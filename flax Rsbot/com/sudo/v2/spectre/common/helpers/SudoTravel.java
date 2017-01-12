package com.sudo.v2.spectre.common.helpers;

import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;

/**
 * Smart Traversal methods for moving the bot player around
 */
public class SudoTravel {

    public static void goTowards(Area area){
        // Get random coordinate in the Area
        Coordinate coordinate = area.getRandomCoordinate();

        if(coordinate.isVisible() && coordinate.isReachable() && Players.getLocal().distanceTo(coordinate) < 12){
            SudoCamera.turnTo(coordinate);
            coordinate.interact("Walk here");
        } else {
            final RegionPath rp = RegionPath.buildTo(coordinate);
            if (rp != null && coordinate.isReachable()) {
                SudoCamera.AngleCameraUp(false);
                if (rp.step(true)) {
                    System.out.println("Walking with Region Path");
                    Execution.delayWhile(() -> Players.getLocal().distanceTo(coordinate) > 6, 250, 500);

                }
            } else {
                final Path p = Traversal.getDefaultWeb().getPathBuilder().buildTo(coordinate);
                if (p != null) {
                    if (p.step(true)) {
                        System.out.println("Walking with Web Path");
                        Execution.delayWhile(() -> Players.getLocal().distanceTo(coordinate) > 6, 250, 500);
                        SudoCamera.turnTo(coordinate);
                    }
                } else {
                    final BresenhamPath bp = BresenhamPath.buildTo(coordinate);

                    if (bp != null) {
                        if (bp.step(true)) {
                            System.out.println("Walking with Bresenham Path");
                            Execution.delayWhile(() -> Players.getLocal().distanceTo(coordinate) > 6, 250, 500);
                        }
                    }
                }
            }
        }
    }

    public static void bresenhamTowards(Area area){
        Player player = Players.getLocal();
        if(area != null) {
            try {
                if(!area.contains(player)) {
                    if(player.distanceTo(area) > 6) {
                        final BresenhamPath bp = BresenhamPath.buildTo(area.getRandomCoordinate());

                        if (bp != null) {
                            if (bp.step(true)) {
                                System.out.println("Walking with Bresenham Path");
                                Execution.delayWhile(Players.getLocal()::isMoving, 250, 500);
                            }
                        }
                    }
                    else{
                        SudoInteract.interactWithClick(area.getRandomCoordinate(), false, "Walk here");
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
