package br.com.cabaret.CarebearBot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.cabaret.CarebearBot.dto.ResultReportMiningDetailDto;
import br.com.cabaret.CarebearBot.dto.ResultReportMiningDto;
import br.com.cabaret.CarebearBot.service.ResolveCommandService;
import br.com.cabaret.CarebearBot.service.dto.GroupDetailDto;
import br.com.cabaret.CarebearBot.service.dto.GroupDto;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

@Service
public class Bot extends ListenerAdapter{
	
	@Value("${botid}")
	private String botID;
	
	@Autowired
	ResolveCommandService cmdService;
	
	public void create() {
		try {
			JDABuilder builder = JDABuilder.createLight(botID, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES);
		    builder.setActivity(Activity.playing("Working and almost done"));
		    builder.addEventListeners(this);
		    builder.build();	
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
    	Message msg = event.getMessage();
        MessageChannel channel = event.getChannel();
        
        if (!channel.getName().equalsIgnoreCase("🍪𝐌𝐈𝐍𝐈𝐍𝐆🍪"))
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
        		String msgRtn = "**Group.: "+ pos.getGrupoName() +"**\n";
				for( GroupDetailDto detailPos : pos.getDetails()) {
					msgRtn += detailPos.getCharacterName()+"\n";
				}

				channel.sendMessage(msgRtn).queue();
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
        	
        	for(ResultReportMiningDto p : results) {
        		String row = "**"+ p.getCharacterName()+"**\n";
        				
    			for (ResultReportMiningDetailDto o : p.getOres()) {
    				row += "Ore.:"+o.getTypeName()+"\n";
    				row += "Qtde minerado.:"+o.getQtMining().toString()+"\n";
    				row += "Qtde Taxa Corp.:"+o.getTaxaCorp().toString()+"\n";
    			}
     			channel.sendMessage(row).queue();			
        	}
        }
        else if (msg.getContentRaw().equals("!report-ratting")) {
        	channel.sendMessage("Will be implemented soon!").queue();
        }
        else if (msg.getContentRaw().equals("!clear-chat")) {
        	event.getTextChannel().createCopy().queue( (v) ->  event.getTextChannel().delete().queue());
        }
        else if (msg.getContentRaw().equals("!update-member")) {
        	cmdService.UpdateMemberList();
        }
    }
}
