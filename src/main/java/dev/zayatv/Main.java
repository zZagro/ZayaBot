package dev.zayatv;

import com.google.api.services.youtube.YouTube;
import dev.zayatv.chatapi.MessageReader;
import dev.zayatv.chatapi.YoutubeLiveChat;
import dev.zayatv.database.DatabaseSecrets;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

public class Main {

    public static JDA jda;

    private static MessageReader messageReader;

    private static final DatabaseSecrets dbSecrets = new DatabaseSecrets();

    public static void main(String[] args)
    {
        connectDatabases();
        startDiscordBot();
        initializeYoutubeAPI();
        initializeMessageReader();
        addDiscordEventListeners();
    }

    private static void startDiscordBot()
    {
        JDABuilder jdaBuilder = JDABuilder.createDefault(dbSecrets.getApiKey("DiscordBotToken"));
        jda = jdaBuilder.build();

        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.getPresence().setActivity(Activity.listening("your thoughts..."));
    }

    private static void connectDatabases()
    {
        try {
            dbSecrets.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeMessageReader()
    {
        messageReader = new MessageReader();
    }

    private static void addDiscordEventListeners()
    {
        messageReader.readMessages();
    }

    private static void initializeYoutubeAPI()
    {
        try {
            YoutubeAPI.getService();

            YoutubeLiveChat liveChat = new YoutubeLiveChat();
            liveChat.getChatMessages();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}
