package br.com.cabaret.CarebearBot;

import java.awt.Color;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Builder;
import java.net.http.WebSocket.Listener;
import java.nio.ByteBuffer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.cabaret.CarebearBot.client.CharacterClient;
import br.com.cabaret.CarebearBot.dto.ResultReportMiningDetailDto;
import br.com.cabaret.CarebearBot.dto.ResultReportMiningDto;
import br.com.cabaret.CarebearBot.dto.ZKillDto;
import br.com.cabaret.CarebearBot.service.ResolveCommandService;
import br.com.cabaret.CarebearBot.service.dto.GroupDetailDto;
import br.com.cabaret.CarebearBot.service.dto.GroupDto;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

@Service
public class Bot extends ListenerAdapter{
	
	@Value("${botid}")
	private String botID;
	
	@Autowired
	private ResolveCommandService cmdService;
	private WebSocket webSocket;
	
	@Autowired
	private CharacterClient charClient;
	private JDA jda;
	
	public void connectWS(JDA jda) {
			 HttpClient httpClient = HttpClient.newBuilder().build();
		     Builder webSocketBuilder = httpClient.newWebSocketBuilder();
		     webSocket = webSocketBuilder.buildAsync(URI.create("wss://zkillboard.com/websocket/"), new WebSocketListener(jda)).join();
		     webSocket.sendText("{\"action\":\"sub\",\"channel\":\"killstream\"}", true);
	}
	
	public void create() {
		try {
			JDABuilder builder = JDABuilder.createLight(botID, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES);
		    builder.setActivity(Activity.playing("Working and almost done"));
		    builder.addEventListeners(this);
		    jda = builder.build();	
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
    @Override
    public void onReady(ReadyEvent event)
    {
		connectWS(event.getJDA());
    }
	
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
    	Message msg = event.getMessage();
        MessageChannel channel = event.getChannel();
        
        if(webSocket.isInputClosed())
        	connectWS(event.getJDA());
        
        if (!event.getGuild().getName().equalsIgnoreCase("whelps")) {
	        if (!channel.getName().equalsIgnoreCase("🍪𝐌𝐈𝐍𝐈𝐍𝐆🍪") && !channel.getName().equalsIgnoreCase("👿𝐊𝐈𝐋𝐋𝐁𝐎𝐀𝐑𝐃😈"))
	        	return;
	        List<Role> roles = event.getMember().getRoles();
	        Boolean hasRole = false;
	        
	        for (Role r : roles) {
	        	if (r.getName().equalsIgnoreCase("diretor")) {
	        		hasRole = true;
	        	}
	        }
	        
        	if(!hasRole) {
	        	return;
	        }
        }
        
        if (msg.getContentRaw().equals("!help")) {
        	channel.sendMessage("**Carebear command List!**\n"
        					+ "!group-list\n"
        					+ "!group-add\n"
        					+ "!group-delete\n"
        					+ "!search\n"
        					+ "!register\n"
        					+ "!report-mining\n"
        					+ "!report-ratting").queue();
        }
        else if (msg.getContentRaw().equals("!group-list")) {
        	List<GroupDto> groupList = cmdService.groupList();
        	
        	for(GroupDto pos: groupList) {
        		List<MessageEmbed> pilotsList = new ArrayList<>();
        		
				for( GroupDetailDto detailPos : pos.getDetails()) {
					var embed = new EmbedBuilder()
	            			.setDescription(detailPos.getCharacterName().replaceAll(" ", "\n"))
	                        .setThumbnail(charClient.portrait(detailPos.getCharacterId()).getPx64x64())
	                        .addBlankField(false)
	            			.setColor(Color.blue)
	            			.build();
					pilotsList.add(embed);
				}
				
				var msgb = new MessageBuilder()
	        			.setContent("**Group.:"+pos.getGrupoName()+"**")
	    				.setEmbeds(pilotsList)
	    				.build();

				channel.sendMessage(msgb).queue();
        	}
        }
        else if (msg.getContentRaw().startsWith("!group-add")) {
        	channel.sendMessage(cmdService.groupAdd(msg.getContentRaw())).queue();
        }
        else if (msg.getContentRaw().startsWith("!group-delete")) {
        	channel.sendMessage(cmdService.groupDelete(msg.getContentRaw())).queue();
        }
        else if (msg.getContentRaw().startsWith("!search")) {
        	channel.sendMessage(cmdService.search(msg.getContentRaw())).queue();
        }
        else if (msg.getContentRaw().equals("!register")) {
        	channel.sendMessage("We need grant permission first. Use the ESI link to do that .: https://tinyurl.com/2p86mt5x").queue();
        }
        else if (msg.getContentRaw().startsWith("!report-mining")) {
        	List<ResultReportMiningDto> results = new ArrayList<>();
        	channel.sendMessage(cmdService.reportMining(msg.getContentRaw(), results)).queue();
        	
        	if (results.size() > 0) {
        		var embedGrupo = new EmbedBuilder()
            			.setTitle("**Grupo**")
                        .setColor(Color.BLUE)
                        .build();
        		
        		var embedChar = new EmbedBuilder()
            			.setTitle("**Character**")
                        .setColor(Color.ORANGE)
                        .build();
        		
        		channel.sendMessageEmbeds(embedGrupo).queue();
        		channel.sendMessageEmbeds(embedChar).queue();
        	}
        	for(ResultReportMiningDto p : results) {
        		Color color = Color.LIGHT_GRAY;
        		String row = "";
        		
    			for (ResultReportMiningDetailDto o : p.getOres()) {
    				if (o.getFgChar().compareTo(1L) == 0) {
    					color = Color.ORANGE;
    				}
    				else {
    					color = Color.BLUE;
    				}
    				row += "**"+o.getTypeName()+"** Total.:"+NumberFormat.getNumberInstance().format(o.getQtMining())+" **Taxa da Corp.:"+NumberFormat.getNumberInstance().format(o.getTaxaCorp())+"**\n";
    			}

        		var embed = new EmbedBuilder()
            			.setTitle("**"+ p.getCharacterName().toUpperCase()+"**")
                        .setDescription(row) 
                        .setColor(color)
                        .build();
        				
        		channel.sendMessageEmbeds(embed).queue();		
        	}
        	
        	channel.sendMessage("Done Report!").queue();
        }
        else if (msg.getContentRaw().equals("!report-ratting")) {
        	channel.sendMessage("Will be implemented soon!").queue();
        }
        else if (msg.getContentRaw().equals("!clear-chat")) {
        	event.getTextChannel().createCopy().queue( (v) ->  event.getTextChannel().delete().queue());
        }
        else if (msg.getContentRaw().equals("!update-all")) {
        	cmdService.updateAll();
        	channel.sendMessage("done.").queue();
        }
        else if (msg.getContentRaw().equals("!teste")) {
        	
        	var embed = new EmbedBuilder()
        			.setTitle("**Texto 01**")
                    .setColor(Color.blue)
                    .setThumbnail(charClient.portrait(90157237L).getPx64x64())
                    .build();
        	
        	var embed2 = new EmbedBuilder()
        			.setTitle("**Texto 02**")
                    .setColor(Color.cyan)
                    .setThumbnail(charClient.portrait(91437812L).getPx64x64())
                    .build();

        }
		
    }
    
    private static class WebSocketListener implements Listener {
    	private JDA jda;
    	
    	public WebSocketListener(JDA jdaVal ) {
    		jda = jdaVal;
    	}
        @Override
        public void onOpen(WebSocket webSocket) {
            System.out.println("CONNECTED");
            Listener.super.onOpen(webSocket);      
        }
 
        @Override
        public void onError(WebSocket webSocket, Throwable error) {
            System.out.println("Error occurred" + error.getMessage());
            Listener.super.onError(webSocket, error);
        }
 
        @Override
        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
            Gson gson = new Gson();
           
            try 
            {
                ZKillDto rtn = gson.fromJson(data.toString(), ZKillDto.class);
	            
                if (rtn.ValidCorp(98517775L)) {
	            	jda.getTextChannelsByName("👿𝐊𝐈𝐋𝐋𝐁𝐎𝐀𝐑𝐃😈", true).get(0).sendMessage(rtn.getZkb().getUrl()).queue();
	            }
            }
            catch(Exception ex) {
            	ex.printStackTrace();
            }
            return Listener.super.onText(webSocket, data, last);
        }
        
        @Override
        public CompletionStage<?> onPong(WebSocket webSocket, ByteBuffer message) {
            System.out.println("onPong: " + new String(message.array()));
            return Listener.super.onPong(webSocket, message);
        }
    }
}
