package org.zeroturnaround.jf.android.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.R.attr.cursorVisible;
import static android.R.attr.id;
import static org.zeroturnaround.jf.android.calculator.R.id.button_zero;

public class CalculatorActivity extends Activity {
    private TextView screen;
    private long currentTotal = 0;
    private boolean removeScreen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set our calculator layout
        setContentView(R.layout.main);
        registerButtons();

        screen = (TextView) findViewById(R.id.text_display);
        screen.setText("");


    }

    private void registerButtons(){
        final Button buttonZero = (Button) findViewById(R.id.button_zero);
        final Button buttonOne = (Button) findViewById(R.id.button_one);
        final Button buttonClear = (Button) findViewById(R.id.button_clear);
        final Button buttonEquals = (Button) findViewById(R.id.button_equals);
        final Button buttonPlus = (Button) findViewById(R.id.button_plus);
        buttonZero.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(removeScreen){
                    screen.setText("");
                    removeScreen = false;
                }
                if(screen.getText().length() >= 16) return;
                if (screen.getText().equals("")) return;
                screen.setText(screen.getText()+"0");
            }
        });
        buttonOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(removeScreen){
                    screen.setText("");
                    removeScreen = false;
                }
                if(screen.getText().length() >= 16) return;
                screen.setText(screen.getText()+"1");
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                screen.setText("");
                currentTotal = 0;
            }
        });
        buttonEquals.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentTotal+=Long.parseLong(screen.getText().toString(),2);
                screen.setText(Long.toBinaryString(currentTotal)+"");
                removeScreen = true;
            }
        });
        buttonPlus.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (removeScreen) return;
                long enteredValue =Long.parseLong(screen.getText().toString(),2);
                if (currentTotal == 0){
                    screen.setText("");
                    currentTotal += enteredValue;
                }else{
                    currentTotal += enteredValue;
                    screen.setText(Long.toBinaryString(currentTotal));
                    removeScreen = true;
                }
            }
        });
    }
}
