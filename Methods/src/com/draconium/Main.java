package com.draconium;

public class Main {

    public static void main(String[] args) {
//        boolean gameOver = true;
//        int score = 800;
//        int levelCompleted = 5;      Code No longer needed and thus far more efficient. However readability suffers.
//        int bonus = 100;
        calculateScore(true,800,5,100);

        calculateScore(true, 10000,8,200);

    }
    // public static void [VarName]([Parameters]) { <---- Layout for setting a method
    public static void calculateScore(boolean gameOver,int score,int levelCompleted,int bonus) {
        if(gameOver) {
            int finalScore = score + (levelCompleted*bonus);
            finalScore+= 1000;
            System.out.println("Your final score is "+finalScore);
        }
    }
}
