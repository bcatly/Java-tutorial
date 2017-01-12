package com.sudo.v2.spectre.common.tasks;

import com.sudo.v2.spectre.common.helpers.Methods;
import com.sudo.v2.spectre.common.helpers.SudoInteract;
import com.sudo.v2.base.SudoBot;
import com.sudo.v2.base.SudoTask;
import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.region.GroundItems;

/**
 * Created by SudoPro on 5/4/2016.
 */
public class AnagogicOrtTask extends SudoTask {
    private SudoBot bot;
    private GroundItem anagogicOrt;

    public AnagogicOrtTask(SudoBot bot){
        this.bot = bot;
    }

    @Override
    public void Loop(){
        anagogicOrt = GroundItems.newQuery().names("Anagogic ort").results().nearest();

        if(anagogicOrt != null){
            Methods.updateCurrentTask("SudoBot: Picking up Anagogic ort", bot);
            SudoInteract.interactWith(anagogicOrt, "Take", bot._useMiddleMouseCamera);
        }
        else{
            Methods.updateCurrentTask("SudoBot: AnagogicOrtTask Complete", bot);
            bot._anagogicOrtTimer.reset();
            Methods.debug("The Anagogic Ort timer has reset, will search for Anagogic Ort again in " + bot._anagogicOrtTimer.getRemainingTime() / 1000 + " seconds");
            Complete();
        }
    }
}
