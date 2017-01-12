package com.sudo.v2.spectre.bots.exampleflaxpicker.ui;

import com.sudo.v2.spectre.bots.exampleflaxpicker.ExampleFlaxPicker;
import com.runemate.game.api.hybrid.util.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Info GUI for the exampleflaxpicker Bot
 *
 * This will show various live stats on the bot
 */
public class FlaxInfoUI extends GridPane implements Initializable {

    private ExampleFlaxPicker bot;

    @FXML
    Label FlaxPH_L, FlaxCount_L, Runtime_L, CurrentTask_L;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setVisible(true);
    }

    // An object property is a container of an object, which can be added
    // listeners to. In this case the property contains our controller class
    // (this)

    public FlaxInfoUI(ExampleFlaxPicker bot) {
        this.bot = bot;

        // Load the fxml file using RuneMate's resources class.
        FXMLLoader loader = new FXMLLoader();

        // Input your InfoUI FXML file location here.
        // NOTE: DO NOT FORGET TO ADD IT TO MANIFEST AS A RESOURCE
        Future<InputStream> stream = bot.getPlatform().invokeLater(() -> Resources.getAsStream("com/sudo/v2/spectre/bots/exampleflaxpicker/ui/InfoUI.fxml"));

        // Set this class as root AND Controller for the Java FX GUI
        loader.setController(this);

        // NOTE: By setting the root to (this) you must change your .fxml to reflect fx:root
        loader.setRoot(this);

        try {
            loader.load(stream.get());
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    // This method will update the text that is presented to the end user
    public void update() {
        try {
            Info i = bot.info;

            FlaxPH_L.textProperty().set("" + i.flaxPh);
            FlaxCount_L.textProperty().set("" + i.flaxCount);
            Runtime_L.textProperty().set("" + i.runTime);
            CurrentTask_L.textProperty().set(i.currentTask);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}