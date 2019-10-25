package com.example.flowergrass.DataModel;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;

public class ChatModel {
    String message, receiver, sender;
    Timestamp timestamp;
    boolean isSeen;

    public ChatModel() {
    }

    public ChatModel(String message, String receiver, String sender, Timestamp timestamp, boolean isSeen) {
        this.message = message;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean getIsSeen() {
        return isSeen;
    }

    public void setIsSeen(boolean seen) {
        isSeen = seen;
    }
}
