package com.example.chat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private Button button;
    private ChatViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.responseText);
        editText = findViewById(R.id.inputText);
        button = findViewById(R.id.sendButton);

        IChatModel chatModel = new ChatModel();
        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @Override
            public <T extends ViewModel> T create(Class<T> modelClass) {
                return (T) new ChatViewModel(chatModel);
            }
        };

        viewModel = new ViewModelProvider(this, factory).get(ChatViewModel.class);

        viewModel.getResponseLiveData().observe(this, response -> {
            textView.setText(response);
        });

        viewModel.getErrorLiveData().observe(this, error -> {
            textView.setText("Ошибка: " + error);
        });

        viewModel.getLoadingLiveData().observe(this, isLoading -> {
            if (isLoading) {
                textView.setText("Загрузка...");
            }
        });

        button.setOnClickListener(v -> {
            String message = editText.getText().toString();
            viewModel.sendMessage(message);
            editText.setText("");
        });
    }
}
