package com.draconium;

public class Main {

    public static void main(String[] args) {
        boolean gameOver = true;
        int score = 5000;
        int levelCompleted = 5;
        int bonus = 100;

        if (score == 5000) {
            int finalscore = score+(levelCompleted *bonus);
            System.out.println("Your final score was "+finalscore);
        }
        /*
        if (score == 5000)
            System.out.println("Your score was 5000");
        It is possible to make an if statement without any brackets as long as
        there is only one line. However when using more than one line make sure
        to use braces.
         */

        gameOver = true;
        score = 10000;
        levelCompleted = 8;
        bonus = 200;

        if (gameOver) {
            int finalscore = score+(levelCompleted *bonus);
            System.out.println("Your final score was "+finalscore);
        }
    }
}