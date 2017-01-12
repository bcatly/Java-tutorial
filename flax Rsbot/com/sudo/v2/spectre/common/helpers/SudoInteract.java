package com.sudo.v2.spectre.common.helpers;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.hud.Menu;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;

/**
 * SudoInteract Class
 *
 * Abstract methods for additional interaction features
 */
public class SudoInteract {
    public static boolean interactWith(GameObject obj, String action, boolean useMiddleMouse) {
        if (obj != null) {
            //if (!obj.isVisible()) {
                if (Players.getLocal().distanceTo(obj.getPosition()) > 8) {
                    SudoTravel.bresenhamTowards(new Area.Circular(obj.getPosition(), 4));
                    Execution.delayWhile(Players.getLocal()::isMoving, 1000);
                }

                if (useMiddleMouse)
                    SudoCamera.middleMouseTurnTo(obj);
                else {
                    SudoCamera.turnTo(obj);
                }
            //}
            if (Menu.isOpen()) {
                Menu.close();
                Execution.delayUntil(() -> !Menu.isOpen(), 500);
            }

            try {
                if(obj.interact(action, obj.getDefinition().getName()))
                    return true;
                else
                    return false;
            } catch (Exception e) {
                Methods.debug("(Obj) Exception thrown when interacting.");
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean interactWith(Npc npc, String action, boolean useMiddleMouse) {
        if (npc != null) {
            //if (!npc.isVisible()) {
                if (Players.getLocal().distanceTo(npc.getPosition()) > 8) {
                    SudoTravel.bresenhamTowards(new Area.Circular(npc.getPosition(), 4));
                    Execution.delayWhile(Players.getLocal()::isMoving, 1000);
                }

                if (useMiddleMouse)
                    SudoCamera.middleMouseTurnTo(npc);
                else {
                    SudoCamera.turnTo(npc);
                }
            //}
            if (Menu.isOpen()) {
                Menu.close();
                Execution.delayUntil(() -> !Menu.isOpen(), 500);
            }

            try {
                if(npc.interact(action, npc.getDefinition().getName()))
                    return true;
                else
                    return false;
            } catch (Exception e) {
                Methods.debug("(Npc) Exception thrown when interacting.");
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean interactWith(GroundItem ground, String action, boolean useMiddleMouse) {
        if (ground != null) {
            //if (!ground.isVisible()) {
                if (Players.getLocal().distanceTo(ground.getPosition()) > 8) {
                    SudoTravel.bresenhamTowards(new Area.Circular(ground.getPosition(), 4));
                    Execution.delayWhile(Players.getLocal()::isMoving, 1000);
                }

                if (useMiddleMouse)
                    SudoCamera.middleMouseTurnTo(ground);
                else {
                    SudoCamera.turnTo(ground);
                }
            //}
            if (Menu.isOpen()) {
                Menu.close();
                Execution.delayUntil(() -> !Menu.isOpen(), 500);
            }

            try {
                if(ground.interact(action, ground.getDefinition().getName()))
                    return true;
                else
                    return false;
            } catch (Exception e) {
                Methods.debug("(GrndObj) Exception thrown when interacting.");
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean interactWithClick(GameObject obj, boolean useMiddleMouse, String... action) {
        if (obj != null) {
            if (Players.getLocal().distanceTo(obj.getPosition()) > 3) {
                SudoTravel.bresenhamTowards(new Area.Circular(obj.getArea().getCenter(), 3));
                Execution.delayWhile(Players.getLocal()::isMoving, 1000);
            }// else if (!obj.isVisible()) {
                if (useMiddleMouse)
                    SudoCamera.middleMouseTurnTo(obj);
                else {
                    SudoCamera.turnTo(obj);
                }
            //}
            //SudoCamera.AngleCameraUp(useMiddleMouse);

            try {
                obj.hover();
                Execution.delayWhile(Mouse::isMoving, 250, 750);

                if (!Menu.isOpen()) {
                    Menu.open();
                    Execution.delayUntil(Menu::isOpen, 500, 1000);
                }

                if (Menu.isOpen()) {
                    for (int i = 0; i < action.length; i++) {
                        if (Menu.contains(action[i]))
                            if (Menu.getItem(action[i]).click())
                                return true;
                            else
                                return false;
                    }
                    Menu.close();
                }

            } catch (Exception e) {
                Methods.debug("(Obj) Exception thrown when interacting with mouse.");
                e.printStackTrace();
            }
        }
        return false;
    }


    public static boolean interactWithClick(Npc npc, boolean useMiddleMouse, String... action){
        if (npc != null) {
            if (Players.getLocal().distanceTo(npc.getPosition()) > 3) {
                SudoTravel.bresenhamTowards(new Area.Circular(npc.getArea().getCenter(), 3));
                Execution.delayWhile(Players.getLocal()::isMoving, 1000);
            }// else if (!npc.isVisible()) {
                if (useMiddleMouse)
                    SudoCamera.middleMouseTurnTo(npc);
                else {
                    SudoCamera.turnTo(npc);
                }
            //}
            //SudoCamera.AngleCameraUp(useMiddleMouse);

            if (Menu.isOpen()) {
                Menu.close();
                Execution.delayWhile(Menu::isOpen, 500, 1000);
            }

            try {
                npc.hover();
                Execution.delayWhile(Mouse::isMoving, 250, 750);
                Menu.open();

                if (Menu.isOpen()) {
                    for (int i = 0; i < action.length; i++) {
                        if (Menu.contains(action[i]))
                            if (Menu.getItem(action[i]).click())
                                return true;
                            else
                                return false;
                    }
                }

            } catch (Exception e) {
                Methods.debug("(Obj) Exception thrown when interacting with mouse.");
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean interactWithClick(Coordinate coord, boolean useMiddleMouse, String... action){
        if (coord != null) {
            if (Players.getLocal().distanceTo(coord.getPosition()) > 3) {
                SudoTravel.bresenhamTowards(new Area.Circular(coord.getArea().getCenter(), 3));
                Execution.delayWhile(Players.getLocal()::isMoving, 1000);
            }// else if (!coord.isVisible()) {
                if (useMiddleMouse)
                    SudoCamera.middleMouseTurnTo(coord);
                else {
                    SudoCamera.turnTo(coord);
                }
            //}
            //SudoCamera.AngleCameraUp(useMiddleMouse);

            if (Menu.isOpen()) {
                Menu.close();
                Execution.delayWhile(Menu::isOpen, 500, 1000);
            }

            try {
                coord.hover();
                Execution.delayWhile(Mouse::isMoving, 250, 750);
                Menu.open();

                if (Menu.isOpen()) {
                    for (int i = 0; i < action.length; i++) {
                        if (Menu.contains(action[i]))
                            if (Menu.getItem(action[i]).click())
                                return true;
                            else
                                return false;
                    }
                }

            } catch (Exception e) {
                Methods.debug("(Obj) Exception thrown when interacting with mouse.");
                e.printStackTrace();
            }
        }
        return false;
    }
}
