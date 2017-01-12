package com.sudo.v2.spectre.bots.exampleflaxpicker;

import com.sudo.v2.spectre.common.helpers.Methods;
import com.sudo.v2.spectre.bots.exampleflaxpicker.ui.FlaxFXGui;
import com.sudo.v2.spectre.bots.exampleflaxpicker.ui.FlaxInfoUI;
import com.sudo.v2.spectre.bots.exampleflaxpicker.ui.Info;
import com.runemate.game.api.client.embeddable.EmbeddableUI;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.entities.definitions.ItemDefinition;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.hybrid.util.calculations.CommonMath;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.LoopingBot;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.events.ItemEvent;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

import java.util.concurrent.TimeUnit;

/**
 * Created by Proxi on 4/5/2016.
 *
 * This bot is designed to give those of you that are new to spectre development
 * some guidance and direction when using some of the new features within RuneMate spectre.
 *
 * This bot will use the new GUI standard for RuneMate known as EmbeddableUI.
 *  - In short, this literally runs your Java FX GUI embedded into spectre.
 *
 * If any questions come up, feel free to ask. Several developers are always active on our website and Slack.
 *
 * - Proxi
 *
 * --- Bot Description ---
 * This will pick and bank flax in Taverly or Seers Village.
 *
 */
public class ExampleFlaxPicker extends LoopingBot implements InventoryListener, EmbeddableUI {

    public Info info;

    private FlaxFXGui configUI;
    private FlaxInfoUI infoUI;
    private SimpleObjectProperty<Node> botInterfaceProperty;

    private StopWatch stopWatch = new StopWatch();
    private int flaxCount;
    private String currentTaskString;
    public Boolean guiWait;
    private Player player;

    public Area flaxArea, bankArea;
    public final Area TAV_FLAX_AREA = new Area.Rectangular(new Coordinate(2883, 3463, 0), new Coordinate(2889, 3468, 0)),
            TAV_BANK_AREA = new Area.Rectangular(new Coordinate(2877, 3419, 0), new Coordinate(2874, 3415, 0)),
            SEER_FLAX_AREA = new Area.Rectangular(new Coordinate(2745, 3451, 0), new Coordinate(2738, 3438, 0)),
            SEER_BANK_AREA = new Area.Rectangular(new Coordinate(2730, 3491, 0), new Coordinate(2722, 3494, 0));

    // Bot's default constructor
    public ExampleFlaxPicker(){
        // Initialize your variables
        guiWait = true;
        flaxCount = 0;

        // Set this class as the EmbeddableUI
        setEmbeddableUI(this);
    }

    @Override
    public ObjectProperty<? extends Node> botInterfaceProperty() {
        if (botInterfaceProperty == null) {
            // Initializing configUI in this manor is known as Lazy Instantiation
            botInterfaceProperty = new SimpleObjectProperty<>(configUI = new FlaxFXGui(this));
            infoUI = new FlaxInfoUI(this);
        }
        return botInterfaceProperty;
    }

    @Override
    public void onStart(String... args){
        // Set/Run anything that needs to be ran at the initial start of the bot
        stopWatch.start();
        currentTaskString = "Starting bot...";

        // Sets the length of time in milliseconds to wait before calling onLoop again
        // NOTE: IT IS NOT RECOMMENDED TO KEEP DEFAULT LOOP DELAY
        setLoopDelay(300, 600);

        // Set custom mouse multiplier or leave default
        Mouse.setSpeedMultiplier(1);

        // Force menu interaction when clicking (force right-click interaction)
        Mouse.setForceMenuInteraction(true);

        // Add this class as a listener for the Event Dispatcher
        getEventDispatcher().addListener(this);
    }

    @Override
    public void onLoop() {
        // While we are waiting for guiWait to be false, stay in this loop
        // This will prevent the bot from starting before the GUI is complete
        // Note: It will wait for 60 seconds (60,000 milliseconds)
        if (!Execution.delayUntil(() -> !guiWait, 60000)) {
            Methods.debug("Still waiting for GUI after a minute, stopping.");
            stop();
            return;
        }

        // In every Loop get the player
        player = Players.getLocal();

        // In every Loop update the GUI thread
        updateInfo();


        // Bot loop logic
        if(Inventory.isFull()){
            if(bankArea.distanceTo(player) < 10){        // You could use bankArea.contains(player) if you wanted to
                if(Bank.isOpen()) {
                    currentTaskString = "Depositing Inventory";
                    Bank.depositInventory();
                }
                else {
                    currentTaskString = "Opening Bank";
                    Bank.open();
                }
            }
            else{
                currentTaskString = "Running to Bank";
                bresenhamTo(bankArea);
            }
        }
        else{
            if(flaxArea.distanceTo(player) < 10) {       // You could use flaxArea.contains(player) if you wanted to
                currentTaskString = "Picking Flax";
                pickFlax();
            }
            else {
                if(Bank.isOpen()){
                    currentTaskString = "Closing Bank";
                    Bank.close();
                }
                else {
                    currentTaskString = "Running to Flax area";
                    bresenhamTo(flaxArea);
                }
            }
        }
    }

    @Override
    public void onItemAdded(ItemEvent event) {
        ItemDefinition definition = event.getItem().getDefinition();
        if (definition != null) {
            // If an item with the name "Flax" appears in the inventory, increase count by 1
            if (definition.getName().contains("Flax")) {
                flaxCount++;
            }
        }
    }

    // When called, switch the botInterfaceProperty to reflect the InfoUI
    public void setToInfoProperty(){
        botInterfaceProperty.set(infoUI);
    }

    // This method is used to update the GUI thread from the bot thread
    public void updateInfo() {
        try {
            // Assign all values to a new instance of the Info class
            info = new Info(
                    (int) CommonMath.rate(TimeUnit.HOURS, stopWatch.getRuntime(), flaxCount),  //   -   -   -   -   -   -   -   // Flax per hour
                    flaxCount,                //    -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   // Flax Picked
                    stopWatch.getRuntimeAsString(),                 //  -   -   -   -   -   -   -   -   -   -   -   -   -   -   // Total Runtime
                    currentTaskString);       //    -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   // Current Task

        }catch(Exception e){
            e.printStackTrace();
        }

        // Be sure to run infoUI.update() through runLater.
        // This will run infoUI.update() on the dedicated JavaFX thread which is the only thread allowed to update anything related to JavaFX rendering
        Platform.runLater(() -> infoUI.update());

        /*
        *  "The way to think about it
            is that the "some stuff" is a package
            and you're at your house (the bot thread)
            Platform.runLater is the mailman
            you give the mailman your package and then go about your life however you want
            i.e. keep going in the code
            and then the mailman does what he needs with the package to get it delivered
            and it's no longer your or your house's problem"
            - The Wise. The One. The Arbiter.
         */
    }

    // Walk towards a location with bresenham path
    private void bresenhamTo(Area area){
        final BresenhamPath bp = BresenhamPath.buildTo(area.getRandomCoordinate());

        if (bp != null) {
            if (bp.step(true)) {
                System.out.println("Walking with Bresenham Path");
                Execution.delayWhile(Players.getLocal()::isMoving, 500, 2500);
            }
        }
    }

    // Method for picking nearest flax to the player
    private void pickFlax(){
        // Get the nearest game object with the name Flax and action Pick and interact with found object with the Pick action
        GameObject flax = GameObjects.newQuery().names("Flax").actions("Pick").within(flaxArea).results().nearest();

        if(flax.isVisible())
            flax.interact("Pick");
        else
            Camera.turnTo(flax);

        // Delay for a random time between 100 and 500 milliseconds. This prevent spam clicking
        Execution.delay(100, 500);
    }

}
