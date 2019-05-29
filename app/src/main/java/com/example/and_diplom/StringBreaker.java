package com.example.and_diplom;

public class StringBreaker {
    public String stringAssotiation(String line){

        return line.replaceAll("\n", String.valueOf(R.string.string_code));
    }
    public String stringBreak(String line){
        return line.replaceAll(String.valueOf(R.string.string_code), "\n");
    }
}
