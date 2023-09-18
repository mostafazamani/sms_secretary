package com.op.smss.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.op.smss.R;

public class AddItem extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText ke;
    private EditText v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        ke = findViewById(R.id.key);
        v =  findViewById(R.id.value);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(ke.getText()) || TextUtils.isEmpty(v.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String key = ke.getText().toString();
                String value = v.getText().toString();
                replyIntent.putExtra("key", key);
                replyIntent.putExtra("value", value);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }

}