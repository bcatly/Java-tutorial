package com.sudo.v2.spectre.common.helpers;

import com.runemate.game.api.hybrid.util.Timer;

/**
 * SudoTimer
 *
 * Wrapper class for RuneMate's Timer Class
 */
public class SudoTimer {

    private Timer timer;
    private int minTime, maxTime;

    public SudoTimer(int min, int max){
        minTime = min;
        maxTime = max;
        timer = new Timer(min + (int)(Math.random() * (max - min)));
    }

    public long getRemainingTime(){
        return timer.getRemainingTime();
    }

    public void setRemainingTime(int time){
        timer = new Timer(time);
    }

    public void reset(){
        timer.stop();
        timer.reset();
        timer = new Timer(minTime + (int)(Math.random() * (maxTime - minTime)));
        timer.start();
    }

    public void restart(){
        timer.reset();
    }

    public void start(){
        timer.start();
    }

    public void stop(){
        timer.stop();
    }
}
