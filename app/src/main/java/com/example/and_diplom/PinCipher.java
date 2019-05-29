package com.example.and_diplom;

import java.util.Random;

public class PinCipher {
    public String crypt(int pin) {
        Random random = new Random();
        char[] pinNum = String.valueOf(pin).toCharArray();
        char[] cryptPin = new char[9];
        for (int i = 0; i < cryptPin.length; i++) {
            cryptPin[i] = String.valueOf(random.nextInt(10)).toCharArray()[0];
        }
        cryptPin[2] = pinNum[0];
        cryptPin[5] = pinNum[2];
        cryptPin[0] = pinNum[3];
        cryptPin[7] = pinNum[1];

        return (new String(cryptPin));

    }

    public int decrypt(String crypt) {
        char[] cryptPin = crypt.toCharArray();
        String pin = String.valueOf(cryptPin[2]) + String.valueOf(cryptPin[7]) + String.valueOf(cryptPin[5]) + String.valueOf(cryptPin[0]);
        return Integer.parseInt(pin);
    }
}
