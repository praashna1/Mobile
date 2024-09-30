package com.example.passingdata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {
    EditText textBox;
    Button passButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);

        textBox=findViewById(R.id.textBox);
        passButton=findViewById(R.id.passButton);

        passButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String str=textBox.getText().toString();

                Intent intent=new Intent(FirstActivity.this,SecondActicity.class);
                intent.putExtra("message",str);

                startActivity(intent);
            }
        });
    }
}
