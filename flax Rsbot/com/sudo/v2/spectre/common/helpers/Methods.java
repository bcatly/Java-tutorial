package com.sudo.v2.spectre.common.helpers;

import com.sudo.v2.base.SudoBot;

/**
 * UpdateUI class for debugging purposes
 */
public class Methods {

    public static void debug(String foo){
        System.out.println(foo);
    }

    public static void updateCurrentTask(String foo, SudoBot bot){
        bot.setCurrentTask(foo);
        System.out.println(foo);
    }

    public static void updateAntiBanTask(String foo, SudoBot bot){
        bot.setAntiBanTask(foo);
        System.out.println(foo);
    }


}
