

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class botCommands extends ListenerAdapter{


    public void onMessageReceived(MessageReceivedEvent event) {
        String messageContent = event.getMessage().getContentRaw();




        if (messageContent.equalsIgnoreCase("!Finland")) {
            event.getChannel().sendMessage("https://www.youtube.com/watch?v=xGaucDp9Rls&ab_channel=Yoohn").queue();

        }
        if (messageContent.equalsIgnoreCase("!Benne")) {
            event.getChannel().sendMessage("https://www.youtube.com/watch?v=xwpMlf2fNl0").queue();
        }


    }







}
