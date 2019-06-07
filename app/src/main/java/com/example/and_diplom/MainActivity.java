package com.example.and_diplom;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int count;
    private int pin;
    private int[] pinNum;
    private Button btn_1;
    private Button btn_2;
    private Button btn_3;
    private Button btn_4;
    private Button btn_5;
    private Button btn_6;
    private Button btn_7;
    private Button btn_8;
    private Button btn_9;
    private Button btn_0;
    private Button btn_del;
    private ImageView[] circles;
    private PinEditor pinEditor = new PinEditor(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        if (!(pinEditor.hasPin())) {
            openApp();
        }

    }

    private void initViews() {
        count = 0;
        pin = 0;
        pinNum = new int[4];
        btn_0 = findViewById(R.id.btn_0);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        btn_del = findViewById(R.id.btn_del);
        circles = new ImageView[4];
        circles[0] = findViewById(R.id.first_circle);
        circles[1] = findViewById(R.id.second_circle);
        circles[2] = findViewById(R.id.third_circle);
        circles[3] = findViewById(R.id.fourth_circle);
        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPin();
            }
        });
    }

    private void enterPin(int num) {
        pinNum[count] = num;
        fillCircle(circles[count]);

        count++;
        if (count == 4) {
            checkPin();

        }
    }

    private void clearPin() {
        if (count > 0) {
            clearCircle(circles[count - 1]);
            pinNum[count - 1] = 0;
            count--;
        }
    }

    private void fillCircle(ImageView circle) {
        circle.setImageResource(R.drawable.circle);
    }

    private void clearCircle(ImageView circle) {
        circle.setImageResource(R.drawable.empty_circle);
    }

    private void checkPin() {
        pin = 0;
        for (int i = 0; i < 4; i++) {
            double nextNum = pinNum[count - 1] * (Math.pow(10, i));
            pin = pin + (int) nextNum;
            clearPin();

        }
        if (pinEditor.checkPin(pin)) {
            openApp();
        } else {
            Toast.makeText(MainActivity.this, R.string.pin_wrong, Toast.LENGTH_LONG).show();
        }

    }

    private void openApp() {
        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Button numBtn = (Button) v;
        enterPin(Integer.parseInt(numBtn.getText().toString()));
    }
}
