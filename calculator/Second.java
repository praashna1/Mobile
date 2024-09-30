package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Second extends AppCompatActivity {

    EditText etInput;
    Button btnClear, btnDivide, btnMultiply, btnSubtract, btnAdd, btnEquals, btnDot;
    Button[] numberButtons = new Button[10];
    double valueOne = Double.NaN, valueTwo;
    char currentAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculatorlayout);

        etInput = findViewById(R.id.etInput);
        btnClear = findViewById(R.id.btnClear);
        btnDivide = findViewById(R.id.btnDivide);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnSubtract = findViewById(R.id.btnSubtract);
        btnAdd = findViewById(R.id.btnAdd);
        btnEquals = findViewById(R.id.btnEquals);
        btnDot = findViewById(R.id.btnDot);

        for (int i = 0; i < 10; i++) {
            String buttonID = "btn" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            numberButtons[i] = findViewById(resID);
            numberButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;
                    etInput.setText(etInput.getText() + b.getText().toString());
                }
            });
        }

        btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etInput.setText(etInput.getText() + ".");
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etInput.setText("");
                valueOne = Double.NaN;
                valueTwo = Double.NaN;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computeCalculation();
                currentAction = '+';
                etInput.setText(null);
            }
        });

        btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computeCalculation();
                currentAction = '-';
                etInput.setText(null);
            }
        });

        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computeCalculation();
                currentAction = '*';
                etInput.setText(null);
            }
        });

        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computeCalculation();
                currentAction = '/';
                etInput.setText(null);
            }
        });

        btnEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computeCalculation();
                currentAction = '=';
                etInput.setText(String.valueOf(valueOne));
                valueOne = Double.NaN;
                currentAction = '0';
            }
        });
    }

    private void computeCalculation() {
        if (!Double.isNaN(valueOne)) {
            valueTwo = Double.parseDouble(etInput.getText().toString());

            switch (currentAction) {
                case '+':
                    valueOne = valueOne + valueTwo;
                    break;
                case '-':
                    valueOne = valueOne - valueTwo;
                    break;
                case '*':
                    valueOne = valueOne * valueTwo;
                    break;
                case '/':
                    valueOne = valueOne / valueTwo;
                    break;
                case '=':
                    break;
            }
        } else {
            try {
                valueOne = Double.parseDouble(etInput.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}