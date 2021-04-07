package com.mthree.flooringmastery.view;

/**
 *
 * UserIO.java - UserIO interface
 *
 * @author Dhalham Mohamed-Ajaz
 * @version 1.0
 *
 */

public interface UserIO {

    void print(String message);

    String readString(String prompt);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max);

    float readFloat(String prompt);

    float readFloat(String prompt, float min, float max);

    long readLong(String prompt);

    long readLong(String prompt, long min, long max);

    String readDate(String prompt);
}
