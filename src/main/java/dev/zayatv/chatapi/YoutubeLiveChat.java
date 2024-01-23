package dev.zayatv.chatapi;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.LiveBroadcast;
import com.google.api.services.youtube.model.LiveBroadcastListResponse;
import com.google.api.services.youtube.model.LiveChatMessage;
import com.google.api.services.youtube.model.LiveChatMessageListResponse;
import dev.zayatv.YoutubeAPI;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class YoutubeLiveChat {
    private LiveBroadcast getBroadcastId() throws GeneralSecurityException, IOException {
        YouTube.LiveBroadcasts.List request = YoutubeAPI.api.liveBroadcasts()
                .list(Collections.singletonList("id,snippet,contentDetails,status"));
        LiveBroadcastListResponse response = request.setBroadcastType("all")
                .setMine(true)
                .execute();
        return response.getItems().getFirst();
    }

    public void getChatMessages() throws IOException, GeneralSecurityException {
        LiveBroadcast broadcastId = getBroadcastId();

        YouTube.LiveChatMessages.List request = YoutubeAPI.api.liveChatMessages()
                .list(broadcastId.getSnippet().getLiveChatId(), Collections.singletonList("snippet"));
        LiveChatMessageListResponse response = request.execute();

        //List<LiveChatMessage> messages = response.getItems();
        /*for (LiveChatMessage message : response.getItems()) {
            System.out.println(message.getSnippet().getDisplayMessage());
        }*/

        String nextPageToken;
        while (broadcastId.getStatus().getLifeCycleStatus().equals("live"))
        {
            nextPageToken = response.getNextPageToken();
            request.setPageToken(nextPageToken);
            for (LiveChatMessage message : response.getItems()) {
                System.out.println(message.getSnippet().getDisplayMessage());
            }
        }
    }
}
