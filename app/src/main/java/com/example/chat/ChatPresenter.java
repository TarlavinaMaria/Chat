package com.example.chat;

public class ChatPresenter implements ChatContract.Presenter {

    ChatContract.View view;
    ChatModel model;

    public ChatPresenter(ChatContract.View view) {
        this.view = view;
        this.model = new ChatModel();
    }

    @Override
    public void sendMessage(String message) {
        view.showLoading();
        model.sendMessage(message, new ChatModel.ChatCallback() {
            @Override
            public void onSuccess(String responce) {
                ((MainActivity) view).runOnUiThread(() -> {
                    view.hideLoading();
                    view.showResponse(responce);
                });
            }

            @Override
            public void onFailure(String error) {
                ((MainActivity) view).runOnUiThread(() -> {
                    view.hideLoading();
                    view.showError(error);
                });
            }
        });


    }
}
