package com.example.and_diplom;

public interface Keystore {
    boolean hasPin();
    boolean checkPin(int pin);
    void saveNew(int pin);
}
