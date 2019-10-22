package com.example.flowergrass.DataModel;

import com.google.firebase.firestore.PropertyName;

public class ChatModel {
    String message, receiver, sender, timestamp;
    boolean isSeen;

    public ChatModel() {
    }

    public ChatModel(String messaege, String receiver, String sender, String timestamp, boolean isSeen) {
        this.message = messaege;
        this.receiver = receiver;
        this.sender = sender;
        this.timestamp = timestamp;
        this.isSeen = isSeen;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String messaege) {
        this.message = messaege;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean getIsSeen() {
        return isSeen;
    }

    public void setIsSeen(boolean seen) {
        isSeen = seen;
    }
}
