package com.example.and_diplom;

import android.app.Activity;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class PinEditor implements Keystore {
    private Activity activity;
    private static String PIN = "PIN";
    private int pincode;
    private PinCipher pinCipher = new PinCipher();

    public PinEditor(Activity activity) {
        this.activity = activity;
    }

    public boolean hasPin() {
        try {
            FileInputStream fileInputStream = activity.openFileInput(PIN);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            pincode = pinCipher.decrypt(reader.readLine());
            return true;

        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean checkPin(int pin) {
        return (pincode == pin);
    }

    @Override
    public void saveNew(int pin) {
        try {
            FileOutputStream fileOutputStream = activity.openFileOutput(PIN, activity.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            BufferedWriter bw = new BufferedWriter(outputStreamWriter);
            bw.write(pinCipher.crypt(pin));
            bw.close();
        } catch (Exception e) {

        }

    }


    public boolean ckeckNewPin(String pin) {
        try {
            int newPin = Integer.parseInt(pin);
        } catch (Exception e) {
            Toast.makeText(activity, R.string.pin_error, Toast.LENGTH_LONG).show();
            return false;
        }


        if (!(pin.length() == 4)) {
            Toast.makeText(activity, R.string.pin_error, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
