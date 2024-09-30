package com.example.passingdata;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActicity extends AppCompatActivity {
    TextView text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        text=(TextView)findViewById(R.id.text);

        Intent intent=getIntent();
        String str=intent.getStringExtra("message");
        text.setText(str);
    }

}
