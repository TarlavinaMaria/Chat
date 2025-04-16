package com.example.chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChatViewModel extends ViewModel {

    private final ChatModel model;
    private final MutableLiveData<String> responseLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    public ChatViewModel() {
        this.model = new ChatModel();
    }

    public LiveData<String> getResponseLiveData() {
        return responseLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public void sendMessage(String message) {
        loadingLiveData.setValue(true);
        model.sendMessage(message, new ChatModel.ChatCallback() {
            @Override
            public void onSuccess(String response) {
                loadingLiveData.postValue(false);
                responseLiveData.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                loadingLiveData.postValue(false);
                errorLiveData.postValue(error);
            }
        });
    }
}
