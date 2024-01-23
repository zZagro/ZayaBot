package dev.zayatv.chatapi;

import dev.zayatv.Main;

import java.util.ArrayList;
import java.util.List;

public class MessageReader {

    public List<Message> allMessages = new ArrayList<>();

    public List<Message> discordMessages = new ArrayList<>();
    public List<Message> youtubeMessages = new ArrayList<>();
    public List<Message> twitchMessages = new ArrayList<>();

    public void readMessages()
    {
        Main.jda.addEventListener(new DiscordLiveChat());
    }
}
