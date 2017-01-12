package com.Draconium;

public class Main {

    public static void main(String[] args) {
        //width of 32
        int myIntValue = 5;
        // width of 32
        float myFloatValue = 5f/2f;
        // width of 64
        double myDoubleValue = 5d/2d;
        // When placing a decimal java will assume it is a double.
        // To set as a float value place "f" after number
        System.out.println("myIntValue = " + myIntValue);
        System.out.println("myFloat Value = " + myFloatValue);
        System.out.println("myDoubleValue = "+ myDoubleValue);
        // doubles are more recommended in usage as it has a higher precision as well as faster calculation
        //for modern computers

        //Tutorial Challenge
        int pounds = 200;
        double poundstokg = pounds * 0.45359237;
        System.out.println(pounds+" pounds in kilograms is "+poundstokg);
    }
}
