package dev.zayatv.chatapi;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Base64;

public class DiscordLiveChat extends ListenerAdapter {

    private MessageReader messageReader;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getMessage().getChannel().getName().equals("stream-chat")) return;

        messageReader = new MessageReader();

        byte[] imageBytes = event.getAuthor().getAvatarUrl() != null ? IOUtils.toByteArray(event.getAuthor().getAvatarUrl()) : IOUtils.toByteArray(event.getAuthor().getDefaultAvatarUrl());
        String userImage = Base64.getEncoder().encodeToString(imageBytes);

        String username = event.getAuthor().getName();
        String messageContent = event.getMessage().getContentRaw();
        Platform platform = Platform.DISCORD;

        Message sentMessage = new Message(userImage, username, messageContent, platform);
        messageReader.allMessages.add(sentMessage);
        messageReader.discordMessages.add(sentMessage);
    }
}
