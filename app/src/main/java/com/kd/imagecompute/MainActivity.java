package com.kd.imagecompute;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final String NAN = "NaN";
    static final String TAG = "MainAct";
    static final char PLUS = '+';
    static final char MINUS = '-';
    static final char MULTIPLY = '*';
    static final char DIVIDE = '/';
    static final String OPS = "-+*/";

    Button btn;
    TextView tv_show_answer;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        tv_show_answer = findViewById(R.id.textView);
        editText = findViewById(R.id.edit_text);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = editText.getText().toString().trim();
                String answer = NAN;

                if (!inputText.isEmpty()) {
                    answer = compute(inputText);
                }

                tv_show_answer.setText(answer);
            }
        });
    }

    private String compute(String inputText) {
        Log.d(TAG, "Input: " + inputText);

        char firstChar = inputText.charAt(0);
        int prentCount = 0;
        int position = 0;
        char operation = 'a';

        if (firstChar == MINUS) {
            inputText = "0" + inputText;
        }

        // tavshi an boloshi operaciis nishani ar sheidzleba
        if (inputText.matches("^[" + OPS + "].*")
                || inputText.matches(".*[" + OPS + "]$")
                || inputText.matches(".*[" + OPS + "][" + OPS + "].*")) {
            return NAN;
        }

        // frchxilebis moshoreba
        if (firstChar == '(' && inputText.endsWith(")")) {
            inputText = inputText.substring(1, inputText.length() - 1);
            firstChar = inputText.charAt(0);
        }
        // tu mxolod ricxvia vabrunebt ukan
        if (inputText.matches("^\\d*(\\.\\d*)?$")) {
            Log.d(TAG, "Int: " + inputText);
            return inputText;
        }

        // uaryofiti ricxvebistvis
        if (firstChar == MINUS) {
            inputText = "0" + inputText;
        }

        char[] charArray = inputText.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '(') {
                prentCount++;
            }
            if (charArray[i] == ')') {
                prentCount--;
                if (prentCount < 0)
                    return NAN;
            }
            if (charArray[i] == PLUS && prentCount == 0) {
                position = i;
                operation = PLUS;
            }
            if (charArray[i] == MINUS && prentCount == 0) {
                position = i;
                operation = MINUS;
            }
        }

        if (prentCount > 0) {
            return NAN;
        }

        if (position == 0) {
            for (int i = 0; i < charArray.length; i++) {
                if (charArray[i] == '(') {
                    prentCount++;
                }
                if (charArray[i] == ')') {
                    prentCount--;
                    if (prentCount < 0)
                        return NAN;
                }
                if (charArray[i] == MULTIPLY && prentCount == 0) {
                    position = i;
                    operation = MULTIPLY;
                }
                if (charArray[i] == DIVIDE && prentCount == 0) {
                    position = i;
                    operation = DIVIDE;
                }
            }
        }

        if (position == 0){
            return NAN;
        }

        String subA = inputText.substring(0, position);
        String subB = inputText.substring(position + 1);
        String answA = compute(subA);
        String answB = compute(subB);
        String answer = NAN;

        if (answA.equals(NAN) || answB.equals(NAN)) {
            return NAN;
        } else {
            switch (operation) {
                case PLUS:
                    answer = String.valueOf(Double.parseDouble(answA) + Double.parseDouble(answB));
                    break;
                case MINUS:
                    answer = String.valueOf(Double.parseDouble(answA) - Double.parseDouble(answB));
                    break;
                case MULTIPLY:
                    answer = String.valueOf(Double.parseDouble(answA) * Double.parseDouble(answB));
                    break;
                case DIVIDE:
                    if (Double.parseDouble(answB) == 0){
                        answer = NAN;
                    }else {
                        answer = String.valueOf(Double.parseDouble(answA) / Double.parseDouble(answB));
                    }
                    break;
            }
        }

        Log.d(TAG, "output: " + subA + operation + subB + " = " + answer);
        return answer;
    }


}
