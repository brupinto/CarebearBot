package br.com.cabaret.CarebearBot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot extends ListenerAdapter{
	
	public void create() {
		try {
			JDABuilder builder = JDABuilder.createLight("OTU4ODY0NTI1NzE1NzI2NDQ2.YkTiLQ.1lqDcVqBRePTZqEZClFIRYezuLA", GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES);
			builder.addEventListeners(new Bot());
		    builder.setActivity(Activity.playing("Mining in progress"));
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
        
        if (msg.getContentRaw().equals("!help")) {
        	channel.sendMessage("Carebear command List!\n"
        					+ "!group-list\n"
        					+ "!group-add\n"
        					+ "!group-delete\n"
        					+ "!search\n"
        					+ "!register\n"
        					+ "!report-mining\n"
        					+ "!report-ratting").queue();
        }
        else if (msg.getContentRaw().equals("!group-list")) {
        	channel.sendMessage("Will be implemented soon!").queue();
        }
        else if (msg.getContentRaw().equals("!group-add")) {
        	channel.sendMessage("Will be implemented soon!").queue();
        }
        else if (msg.getContentRaw().equals("!group-delete")) {
        	channel.sendMessage("Will be implemented soon!").queue();
        }
        else if (msg.getContentRaw().equals("!search")) {
        	channel.sendMessage("Will be implemented soon!").queue();
        }
        else if (msg.getContentRaw().equals("!register")) {
        	channel.sendMessage("We need grant permission first. Use the ESI link to do that .: https://tinyurl.com/2p86mt5x").queue();
        }
        else if (msg.getContentRaw().equals("!report-mining")) {
        	channel.sendMessage("Will be implemented soon!").queue();
        }
        else if (msg.getContentRaw().equals("!report-ratting")) {
        	channel.sendMessage("Will be implemented soon!").queue();
        }
    }
}
