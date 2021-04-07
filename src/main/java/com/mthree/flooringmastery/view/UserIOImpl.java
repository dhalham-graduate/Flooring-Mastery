package com.mthree.flooringmastery.view;

import java.util.Scanner;

/**
 *
 * UserIOImpl.java - Console IO implementation
 *
 * @author Dhalham Mohamed-Ajaz
 * @version 1.0
 *
 */

public class UserIOImpl implements UserIO {

    // Scanner for console input
    Scanner sc = new Scanner(System.in);

    // Prints string
    @Override
    public void print(String message) {
        System.out.println(message);
    }

    // Returns string entered by user
    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return sc.nextLine();
    }

    public String readDate(String prompt){
        System.out.println(prompt);
        String stringInput = "";
        String convertedString = "";
        boolean isValid = true;

        while (isValid){
            try {
                stringInput = sc.nextLine();
                convertedString = stringInput.substring(0, 2) + stringInput.substring(3, 5) + stringInput.substring(6, 10);
                Integer.parseInt(convertedString);
                isValid = false;
            }
            catch (NumberFormatException | StringIndexOutOfBoundsException e){
                System.out.println("Make sure you enter the date in a valid format! Please try again:");
                isValid = true;
            }
        }

        return convertedString;
    }

    // Returns int entered by user
    @Override
    public int readInt(String prompt) {
        System.out.println(prompt);
        String stringInput = "";
        int input = 0;

        // Boolean for constant while loop, until an integer is entered
        boolean isValid = true;

        // While loop until a valid integer is entered
        while (isValid) {
            try {
                stringInput = sc.nextLine();
                input = Integer.parseInt(stringInput);
                isValid = false;
            } catch (NumberFormatException e) {
                System.out.println("Make sure you enter an integer!");
                isValid = true;
            }
        }
        return input;
    }

    // Returns int entered by user, within a specific range
    @Override
    public int readInt(String prompt, int min, int max) {
        System.out.println(prompt);
        String stringInput = "";
        int input = 0;

        boolean isValid = true;

        while (isValid) {
            try {
                stringInput = sc.nextLine();
                input = Integer.parseInt(stringInput);
                if (input >= min && input <= max) {
                    isValid = false;
                } else {
                    System.out.println("Please make sure the integer entered "
                            + "is between " + min + " and " + max + "!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Make sure you enter an integer!");
                isValid = true;
            }
        }
        return input;
    }

    // Returns double entered by user
    @Override
    public double readDouble(String prompt) {
        System.out.println(prompt);
        String stringInput = "";
        double input = 0;

        boolean isValid = true;

        while (isValid) {
            try {
                stringInput = sc.nextLine();
                input = Double.parseDouble(stringInput);
                isValid = false;
            } catch (NumberFormatException e) {
                System.out.println("Make sure you enter a number!");
                isValid = true;
            }
        }
        return input;
    }

    // Returns double entered by user, within a specific range
    @Override
    public double readDouble(String prompt, double min, double max) {
        System.out.println(prompt);
        String stringInput = "";
        double input = 0;

        boolean isValid = true;

        while (isValid) {
            try {
                stringInput = sc.nextLine();
                input = Double.parseDouble(stringInput);
                if (input >= min && input <= max) {
                    isValid = false;
                } else {
                    System.out.println("Please make sure the number entered "
                            + "is between " + min + " and " + max + "!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Make sure you enter a number!");
                isValid = true;
            }
        }
        return input;
    }

    // Returns float entered by user
    @Override
    public float readFloat(String prompt) {
        System.out.println(prompt);
        String stringInput = "";
        float input = 0;

        boolean isValid = true;

        while (isValid) {
            try {
                stringInput = sc.nextLine();
                input = Float.parseFloat(stringInput);
                isValid = false;
            } catch (NumberFormatException e) {
                System.out.println("Make sure you enter a number!");
                isValid = true;
            }
        }
        return input;
    }

    // Returns float entered by user, within a specific range
    @Override
    public float readFloat(String prompt, float min, float max) {
        System.out.println(prompt);
        String stringInput = "";
        float input = 0;

        boolean isValid = true;

        while (isValid) {
            try {
                stringInput = sc.nextLine();
                input = Float.parseFloat(stringInput);
                if (input >= min && input <= max) {
                    isValid = false;
                } else {
                    System.out.println("Please make sure the number entered "
                            + "is between " + min + " and " + max + "!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Make sure you enter a number!");
                isValid = true;
            }
        }
        return input;
    }

    // Returns long entered by user
    @Override
    public long readLong(String prompt) {
        System.out.println(prompt);
        String stringInput = "";
        long input = 0;

        boolean isValid = true;

        while (isValid) {
            try {
                stringInput = sc.nextLine();
                input = Long.parseLong(stringInput);
                isValid = false;
            } catch (NumberFormatException e) {
                System.out.println("Make sure you enter a number!");
                isValid = true;
            }
        }
        return input;
    }

    // Returns long entered by user, within a specific range
    @Override
    public long readLong(String prompt, long min, long max) {
        System.out.println(prompt);
        String stringInput = "";
        long input = 0;

        boolean isValid = true;

        while (isValid) {
            try {
                stringInput = sc.nextLine();
                input = Long.parseLong(stringInput);
                if (input >= min && input <= max) {
                    isValid = false;
                } else {
                    System.out.println("Please make sure the number entered "
                            + "is between " + min + " and " + max + "!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Make sure you enter a number!");
                isValid = true;
            }
        }
        return input;
    }

}
