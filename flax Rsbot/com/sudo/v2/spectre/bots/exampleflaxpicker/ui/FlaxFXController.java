package com.sudo.v2.spectre.bots.exampleflaxpicker.ui;

import com.sudo.v2.spectre.bots.exampleflaxpicker.ExampleFlaxPicker;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Java FX Controller for the FlaxFXGui class
 *
 * The controller class is where the logic and implementation of GUI events go.
 *      Ex. If you press on a Start button in a typical program, you'd expect the program to actually start.
 *          That event handling would be found here.
 *          NOTE:   You can assign a single class to be the FXML Loader And Controller.
 *                  To do this, just set your FXML's loader to .setController(this) in appropriate class.
 */
public class FlaxFXController implements Initializable {

    private ExampleFlaxPicker bot;

    @FXML
    private ComboBox Location_ComboBox;

    @FXML
    private Button Start_BT;


    public FlaxFXController(ExampleFlaxPicker bot) {
        this.bot = bot;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Add Locations to the combo box
        Location_ComboBox.getItems().addAll("Taverly", "Seers Village");

        // If the Start Button is pressed, handle that even in the getStart_BTAction method
        Start_BT.setOnAction(getStart_BTAction());

        // Set the event for Location_ComboBox
        Location_ComboBox.setOnAction(getLocation_ComboBoxEvent());
    }

    public EventHandler<ActionEvent> getStart_BTAction() {
        return event -> {
            try {
                // Initialize all variables in your bot
                bot.guiWait = false;

                switch(Location_ComboBox.getSelectionModel().getSelectedItem().toString()){
                    case "Taverly":
                        bot.flaxArea = bot.TAV_FLAX_AREA;
                        bot.bankArea = bot.TAV_BANK_AREA;
                        break;
                    case "Seers Village":
                        bot.flaxArea = bot.SEER_FLAX_AREA;
                        bot.bankArea = bot.SEER_BANK_AREA;
                }

                // Set the EmbeddableUI property to reflect your Info GUI
                Platform.runLater(() -> bot.setToInfoProperty());

            } catch (Exception e) {
                e.printStackTrace();
            }

        };
    }

    public EventHandler<ActionEvent> getLocation_ComboBoxEvent(){
        return event ->{
            // If a value is assigned to the Combo Box, enable tot he Start Button.
            if(Location_ComboBox.getSelectionModel().getSelectedItem() != null)
                Start_BT.setDisable(false);
            else
                Start_BT.setDisable(true);
        };
    }
}
