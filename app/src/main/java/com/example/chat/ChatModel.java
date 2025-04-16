package com.example.chat;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatModel {
    final String API_KEY = "sk-cf6742194e954b2dbcaf5cfd7385cc4b";
    final String API_URL = "https://api.deepseek.com/v1/chat/completions";

    final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    public interface ChatCallback {
        void onSuccess(String responce);

        void onFailure(String error);
    }

    public void sendMessage(String message, ChatCallback callback) {
        try {
            JSONObject json = new JSONObject();
            json.put("model", "deepseek-chat");
            //думающая
            // json.put("model","deepseek-reasoner");

            JSONArray messages = new JSONArray();
            JSONObject userMsg = new JSONObject();
            userMsg.put("role", "user");
            userMsg.put("content", message);
            messages.put(userMsg);

            json.put("messages", messages);

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json;charset=utf-8"));
            Request request = new Request.Builder()
                    .url(API_URL)
                    .header("Authorization", "Bearer " + API_KEY)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    callback.onFailure("Ошибка сети " + e.getMessage());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        String errorBody = response.body() != null ? response.body().string() : "нет тела ответа";
                        callback.onFailure("HTTP " + response.code() + ":" + errorBody);
                        return;
                    }
                    //Если ответ успешен
                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonResponce = new JSONObject(responseBody);
                        String reply = jsonResponce.getJSONArray("choices")
                                .getJSONObject(0)
                                .getJSONObject("message")
                                .getString("content");
                        callback.onSuccess(reply.trim());
                    } catch (Exception e) {
                        callback.onFailure("Ошибка разобра JSON " + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            callback.onFailure("Ошибка при формировании запроса " + e.getMessage());
        }
    }
}
