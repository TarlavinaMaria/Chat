package com.example.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements ChatContract.View {
    TextView textView;
    EditText editText;
    Button button;
    ChatPresenter presenter;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.responseText);
        editText = findViewById(R.id.inputText);
        button = findViewById(R.id.sendButton);
        presenter = new ChatPresenter(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();
                presenter.sendMessage(message);
            }
        });
    }


    @Override
    public void showLoading() {
        textView.setText("Загрузка....");
    }

    @Override
    public void hideLoading() {
        textView.setText("");
    }

    @Override
    public void showResponse(String response) {
        textView.setText(response);
    }

    @Override
    public void showError(String error) {
        textView.setText("Ошибка " + error);
    }
}
