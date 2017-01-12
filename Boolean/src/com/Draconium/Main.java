package com.Draconium;

public class Main {

    public static void main(String[] args) {
        //only supports one character. has width of 16
        //to write a unicode do backslash(\)uxxxx
        char myChar = '\u00A9';
        System.out.println(myChar);

        //Can only hold true or false.
        boolean myBoolean = false;
        boolean isMale = true;

        //Challenge
        char myrsymbol = '\u00AE';
        System.out.println(myrsymbol);
    }
}
