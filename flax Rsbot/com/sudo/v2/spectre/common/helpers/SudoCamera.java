package com.sudo.v2.spectre.common.helpers;

import com.runemate.game.api.hybrid.entities.details.Interactable;
import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.InteractablePoint;
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.region.Players;

/**
 * SudoCamera Class
 *
 * Abstract methods for additional Camera features
 */
public class SudoCamera {

    // Angle Camera up
    public static void AngleCameraUp(boolean middleMouse){
        double pitch = .5 + (Math.random() / 7);
        if(middleMouse)
            Camera.concurrentlyTurnTo(pitch);
        else {
            // Area to hover over
            Area hoverArea = new Area.Circular(Players.getLocal().getPosition(), 3);

            // Hover over random coordinate
            hoverArea.getRandomCoordinate().hover();

            // Create failsafe timer
            SudoTimer turnTimer = new SudoTimer(1000, 2500);

            // Move camera with mouse until at appropriate pitch
            Mouse.press(Mouse.Button.WHEEL);
            turnTimer.start();
            while(Camera.getPitch() < pitch && turnTimer.getRemainingTime() > 0) {
                Mouse.move(new InteractablePoint((int) ((Mouse.getPosition().getX() - 5) + (Math.random() * 10)), (int) (Mouse.getPosition().getY() + 5 + (Math.random() * 70))));
            }

            turnTimer.reset();
            Mouse.release(Mouse.Button.WHEEL);

        }


        //System.out.println("Angling SudoCamera Upwards");
    }

    // Rotate camera towards an Npc with the middle mouse button
    public static void middleMouseTurnTo(Interactable interactable){
        // Area to hover over
        Area hoverArea = new Area.Circular(Players.getLocal().getPosition(), 3);
        // Hover over random coordinate
        hoverArea.getRandomCoordinate().hover();

        boolean turnLeft;
        SudoTimer turnTimer = new SudoTimer(1000, 2500);

        if(Math.random() < 0.5)
            turnLeft = true;
        else
            turnLeft = false;

        // Move camera with mouse until object is on the screen
        Mouse.press(Mouse.Button.WHEEL);
        turnTimer.start();
        while(interactable != null && !interactable.isVisible() && turnTimer.getRemainingTime() > 0) {
            if(turnLeft) {
                if(Camera.getPitch() < 0.2)    // If camera is angled too low
                    Mouse.move(new InteractablePoint((int) (Mouse.getPosition().getX() - (Math.random() * 100)), (int) (Mouse.getPosition().getY() + (5 + Math.random() * 30))));
                else if(Camera.getPitch() > 0.5) // If the camera is angled too high
                    Mouse.move(new InteractablePoint((int) (Mouse.getPosition().getX() - (Math.random() * 100)), (int) (Mouse.getPosition().getY() - (5 + Math.random() * 30))));
                else    // Camera is in appropriate pitch
                    Mouse.move(new InteractablePoint((int) (Mouse.getPosition().getX() - (Math.random() * 100)), (int) (Mouse.getPosition().getY() - 10 + (Math.random() * 20))));
            }
            else {
                if(Camera.getPitch() < 0.2)    // If camera is angled too low
                    Mouse.move(new InteractablePoint((int) (Mouse.getPosition().getX() + (Math.random() * 100)), (int) (Mouse.getPosition().getY() + (5 + Math.random() * 30))));
                else if(Camera.getPitch() > 0.5) // If the camera is angled too high
                    Mouse.move(new InteractablePoint((int) (Mouse.getPosition().getX() + (Math.random() * 100)), (int) (Mouse.getPosition().getY() - (5 + Math.random() * 30))));
                else    // Camera is in appropriate pitch
                    Mouse.move(new InteractablePoint((int) (Mouse.getPosition().getX() + (Math.random() * 100)), (int) (Mouse.getPosition().getY() - 10 + (Math.random() * 20))));
            }
        }

        turnTimer.reset();
        Mouse.release(Mouse.Button.WHEEL);
    }

    public static void turnTo(Locatable locatable){
        if(ChatDialog.getContinue() != null) {
            ChatDialog.getContinue().select();
            //Execution.delayWhile(() -> ChatDialog.getContinue() != null);
        }
        if(locatable != null) {
            Camera.turnTo(locatable, .5 + (Math.random() / 7));

            int yaw = Camera.getYaw();
            int yawWiggle = ((int)Math.random() * 20) - 10;

            double pitch = Camera.getPitch();
            double pitchWiggle = (Math.random() * 0.1) - 0.05;

            if(yaw > 319)
                yawWiggle = ((int)Math.random() * 20) - 20;
            else if(yaw < 20)
                yawWiggle = ((int)Math.random() * 20);

            if(pitch > .5)
                pitchWiggle = (Math.random() * 0.1) - 0.1;
            else if(pitch < .15)
                pitchWiggle = (Math.random() * 0.1);

            Camera.concurrentlyTurnTo(yaw + yawWiggle, pitch + pitchWiggle);

        }
    }

}
