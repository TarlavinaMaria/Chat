package com.example.chat;

public interface IChatModel {
    void sendMessage(String message, ChatModel.ChatCallback callback);
}
