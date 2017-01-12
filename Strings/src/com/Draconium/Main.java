package com.Draconium;

public class Main {

    public static void main(String[] args) {
        // byte
        // short
        // int
        // float
        // double
        // char
        // boolean

        //String is used to hold text
        String myString = "This is a string";
        System.out.println("myString is equal to "+ myString);
        myString = myString + ", and this is more.";
        System.out.println("myString is equal to "+ myString);
        myString = myString+" \u00A9 2015";
        System.out.println("myString is equal to "+ myString);
        //Strings cannot be used for mathematical calculations.

        String lastString = "10";
        int myInt = 50;
        lastString = lastString+myInt;
        System.out.println("LastString is equal to " +lastString);
    }
}-
