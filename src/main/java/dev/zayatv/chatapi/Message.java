package dev.zayatv.chatapi;

public class Message {
    public String userImage;
    public String username;
    public String messageContent;
    public Platform sentOn;

    public Message(String userImage, String username, String messageContent, Platform sentOn)
    {
        this.userImage = userImage;
        this.username = username;
        this.messageContent = messageContent;
        this.sentOn = sentOn;
    }
}
