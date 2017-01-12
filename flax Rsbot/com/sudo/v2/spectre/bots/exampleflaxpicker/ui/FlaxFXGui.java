package com.sudo.v2.spectre.bots.exampleflaxpicker.ui;

import com.sudo.v2.spectre.bots.exampleflaxpicker.ExampleFlaxPicker;
import com.runemate.game.api.hybrid.util.Resources;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Java FX Gui for configuring exampleflaxpicker bot settings
 */
public class FlaxFXGui extends GridPane implements Initializable {

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setVisible(true);
    }

    public FlaxFXGui(ExampleFlaxPicker bot) {
        // Load the fxml file using RuneMate's resources class.
        FXMLLoader loader = new FXMLLoader();

        // Input your Settings GUI FXML file location here.
        // NOTE: DO NOT FORGET TO ADD IT TO MANIFEST AS A RESOURCE
        Future<InputStream> stream = bot.getPlatform().invokeLater(() -> Resources.getAsStream("com/sudo/v2/spectre/bots/exampleflaxpicker/ui/FlaxGUI.fxml"));

        // Set FlaxFXController as the class that will be handling our events
        loader.setController(new FlaxFXController(bot));

        // Set the FXML load's root to this class
        // NOTE: By setting the root to (this) you must change your .fxml to reflect fx:root
        loader.setRoot(this);

        try {
            loader.load(stream.get());
        } catch (IOException | InterruptedException | ExecutionException e) {
            System.err.println("Error loading GUI");
            e.printStackTrace();
        }

    }
}