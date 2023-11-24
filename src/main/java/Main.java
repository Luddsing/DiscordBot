
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;



public class Main{
    public static void main(String[] args) throws LoginException{



        //Discord bot--------------------

        //bot build

        String botToken = "MTEyMTczODc0NjQ4ODk2MzExMg.GotyPQ.OY5ERCn172zXT2b7GCj5SHfo9L_g2AGzzIuB3Y";
        JDABuilder jdaBuilder = JDABuilder.createDefault(botToken).enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT).addEventListeners(new botCommands());
        jdaBuilder.setActivity(Activity.playing("Type !play"));
        jdaBuilder.addEventListeners(new BotListener());

        jdaBuilder.build();


        //audio build
        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);

        AudioPlayer player = playerManager.createPlayer();
        TrackScheduler trackScheduler = new TrackScheduler(player);
        player.addListener(trackScheduler);




    }




}



