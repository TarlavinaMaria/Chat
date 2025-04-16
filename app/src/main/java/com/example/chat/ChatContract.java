package com.example.chat;

public interface ChatContract {
    interface View {
        void showLoading();

        void hideLoading();

        void showResponse(String response);

        void showError(String error);
    }

    interface Presenter {
        void sendMessage(String message);
    }
}
