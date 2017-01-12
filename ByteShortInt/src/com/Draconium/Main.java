package com.Draconium;

public class Main {

    public static void main(String[] args) {

        /* int has a width of 32 */
        int myValue = 10000;
        int myMinValue=-2_147_483_648;
        int myMaxValue =2_147_483_647;
        int myTotal = (myMinValue/2);
        System.out.println(myTotal);

        //byte has a width of 8
        byte myMinByteValue = -128;
        byte myMaxByteValue = 127;
        /* By placing a (datatype) it forces the program to take the value as a byte e.g.
        (byte). THis is called Casting.
         */
        byte myByteTotal = (byte) (myMinByteValue/2);
        System.out.println(myByteTotal);

        //short has a width of 16
        short myMinShortValue = -32768;
        short myMaxshortValue = 32767;

        //long has a width of 64
        long myLongValue = 100L;
        /* Max long is = -2^63 to (2^63) - 1
        Always place an L in the end of a number for long
         */
        byte bytec = 34;
        short shortc = 4568;
        int intc = 343045;
        long longc = 50000 +10*(bytec+shortc+intc);
        System.out.println(longc);

    }
}