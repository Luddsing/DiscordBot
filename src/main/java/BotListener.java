import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;




public class BotListener extends ListenerAdapter {
    private final AudioPlayerManager playerManager;
    private final AudioPlayer player;
    private final TrackScheduler scheduler;

    public BotListener() {
        playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        player = playerManager.createPlayer();
        scheduler = new TrackScheduler(player);
        player.addListener(scheduler);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        TextChannel channel = (TextChannel) event.getChannel();
        if (message.startsWith("!play")) {
            String[] command = message.split(" ", 2);
            if (command.length == 2) {
                String url = command[1];
                loadAndPlay(event, (TextChannel) event.getChannel(), url); // Pass event here
            }
            else if (message.startsWith("!pause")){

            }else if (message.startsWith("!resume")){

            }else if (message.startsWith("!clear")){

            } else if (message.startsWith("!skip")) {



            }

            else{
                event.getChannel().sendMessage("Please provide a valid URL.").queue();
            }
        }        switch (message) {
            case "!play":
                String[] command = message.split(" ", 2);
                if (command.length == 2) {
                    String url = command[1];
                    loadAndPlay(event, channel, url);
                } else {
                    channel.sendMessage("Please provide a valid URL.").queue();
                }
                break;
            case "!pause":
                pauseTrack(channel);
                break;
            case "!resume":
                resumeTrack(channel);
                break;
            case "!clear":
                clearQueue(channel);
                break;
            case "!skip":
                skipTrack(channel);

        }



    }
    private void pauseTrack(TextChannel channel) {
        if (player.isPaused()) {
            channel.sendMessage("Playback is already paused.").queue();
        } else {
            player.setPaused(true);
            channel.sendMessage("Playback paused.").queue();
        }
    }
    private void skipTrack(TextChannel channel) {
        scheduler.skipTrack();
        channel.sendMessage("Skipped to the next track.").queue();
    }

    private void resumeTrack(TextChannel channel) {
        if (!player.isPaused()) {
            channel.sendMessage("Playback is not paused.").queue();
        } else {
            player.setPaused(false);
            channel.sendMessage("Playback resumed.").queue();
        }
    }

    private void clearQueue(TextChannel channel) {
        scheduler.clearQueue();
        channel.sendMessage("Queue cleared.").queue();
    }

    private void loadAndPlay(MessageReceivedEvent event, final TextChannel channel, final String trackUrl) {
        joinChannel(event); // Use event here
        channel.sendMessage("Joining a channel!");
        playerManager.loadItemOrdered(player, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                channel.sendMessage("Adding to queue " + track.getInfo().title).queue();
                scheduler.queue(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();
                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }
                channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();
                scheduler.queue(firstTrack);
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Nothing found by " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Could not play: " + exception.getMessage()).queue();
            }
        });
    }

    private void joinChannel(MessageReceivedEvent event) {
        AudioManager audioManager = event.getGuild().getAudioManager();

        if (!audioManager.isConnected() && event.getMember().getVoiceState().inAudioChannel()) {
            AudioChannel voiceChannel = event.getMember().getVoiceState().getChannel();

            if (voiceChannel != null) {
                audioManager.openAudioConnection(voiceChannel);

                // Set the AudioPlayerSendHandler to the AudioManager
                audioManager.setSendingHandler(new AudioPlayerSendHandler(player));

                System.out.println("Bot joining channel: " + voiceChannel.getName());
                event.getChannel().sendMessage("Bot joining channel: " + voiceChannel).queue();
            } else {
                System.out.println("Voice channel is null");
            }
        } else {
            System.out.println("User is not in any audio channel");
        }
    }





}