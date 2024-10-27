package com.example.madtlab3;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private double valueOne = Double.NaN;
    private double valueTwo;
    private char currentOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        display = findViewById(R.id.txtDisplay);
        setListeners();
    }

    private void setListeners() {
        // Number buttons
        findViewById(R.id.btn0).setOnClickListener(v -> appendNumber("0"));
        findViewById(R.id.btn1).setOnClickListener(v -> appendNumber("1"));
        findViewById(R.id.btn2).setOnClickListener(v -> appendNumber("2"));
        findViewById(R.id.btn3).setOnClickListener(v -> appendNumber("3"));
        findViewById(R.id.btn4).setOnClickListener(v -> appendNumber("4"));
        findViewById(R.id.btn5).setOnClickListener(v -> appendNumber("5"));
        findViewById(R.id.btn6).setOnClickListener(v -> appendNumber("6"));
        findViewById(R.id.btn7).setOnClickListener(v -> appendNumber("7"));
        findViewById(R.id.btn8).setOnClickListener(v -> appendNumber("8"));
        findViewById(R.id.btn9).setOnClickListener(v -> appendNumber("9"));

        // Operation buttons
        findViewById(R.id.btnAdd).setOnClickListener(v -> setOperation('+'));
        findViewById(R.id.btnSubtract).setOnClickListener(v -> setOperation('-'));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> setOperation('*'));
        findViewById(R.id.btnDivide).setOnClickListener(v -> setOperation('/'));
        findViewById(R.id.btnSqrt).setOnClickListener(v -> calculateSquareRoot());
        findViewById(R.id.btnSignChange).setOnClickListener(v -> changeSign());
        findViewById(R.id.btnClear).setOnClickListener(v -> clear());
        findViewById(R.id.btnBack).setOnClickListener(v -> backspace());
        findViewById(R.id.btnEquals).setOnClickListener(v -> calculateResult());
    }

    private void appendNumber(String number) {
        if (display.getText().toString().equals("0")) {
            display.setText(number);
        } else {
            display.append(number);
        }
    }

    private void setOperation(char operation) {
        if (!Double.isNaN(valueOne)) {
            valueTwo = Double.parseDouble(display.getText().toString());
            calculate();  // calculate based on the previous operation
        } else {
            valueOne = Double.parseDouble(display.getText().toString());
        }
        currentOperation = operation;
        display.setText(null);  // Clear the display for the next input
    }

    private void calculate() {
        switch (currentOperation) {
            case '+':
                valueOne += valueTwo;
                break;
            case '-':
                valueOne -= valueTwo;
                break;
            case '*':
                valueOne *= valueTwo;
                break;
            case '/':
                if (valueTwo != 0) {
                    valueOne /= valueTwo;
                } else {
                    display.setText("Error");
                    return;
                }
                break;
        }
        display.setText(String.valueOf(valueOne));
    }

    private void calculateResult() {
        if (!Double.isNaN(valueOne)) {
            valueTwo = Double.parseDouble(display.getText().toString());
            calculate();
            currentOperation = ' ';  // Clear operation after result
        }
    }

    private void calculateSquareRoot() {
        double number = Double.parseDouble(display.getText().toString());
        if (number >= 0) {
            valueOne = Math.sqrt(number);
            display.setText(String.valueOf(valueOne));
        } else {
            display.setText("Error");
        }
    }

    private void backspace() {
        String currentText = display.getText().toString();
        if (currentText.length() > 0) {
            display.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    private void clear() {
        display.setText("0");
        valueOne = Double.NaN;
        valueTwo = Double.NaN;
        currentOperation = ' ';
    }

    private void changeSign() {
        String currentText = display.getText().toString();
        if (currentText.equals("0")) return;

        if (currentText.startsWith("-")) {
            display.setText(currentText.substring(1));
        } else {
            display.setText("-" + currentText);
        }
    }
}