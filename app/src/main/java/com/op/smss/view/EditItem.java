package com.op.smss.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.op.smss.background.ItemDataBase;
import com.op.smss.model.Items;
import com.op.smss.R;

public class EditItem extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText ke;
    private EditText v;
    ItemDataBase itemDataBase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        ke = findViewById(R.id.key);
        v =  findViewById(R.id.value);


        Bundle bundle = getIntent().getExtras();
        ke.setText(bundle.getString("k",""));
        v.setText(bundle.getString("v",""));
        itemDataBase = ItemDataBase.getDatabase(this);

        Items items = itemDataBase.smswordDao().getItem(bundle.getString("k","")).get(0);

        final Button button = findViewById(R.id.button_save);
        button.setText("Edit");
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(ke.getText()) || TextUtils.isEmpty(v.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
//                itemDataBase.smswordDao().deleteItem(bundle.getString("k",""));
                String key = ke.getText().toString();
                String value = v.getText().toString();

                Items ui = new Items(key,value);
                ui.setId(items.getId());
//                replyIntent.putExtra("key", key);
//                replyIntent.putExtra("value", value);
//                setResult(RESULT_OK, replyIntent);

                itemDataBase.smswordDao().update(ui);
            }
            finish();
        });
    }

}