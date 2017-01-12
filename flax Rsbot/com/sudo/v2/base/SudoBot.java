package com.sudo.v2.base;

import com.sudo.v2.antiban.*;
import com.sudo.v2.spectre.common.helpers.Methods;
import com.sudo.v2.spectre.common.helpers.SudoTimer;
import com.sudo.v2.spectre.common.tasks.AnagogicOrtTask;
import com.sudo.v2.ui.Info;
import com.sudo.v2.ui.SudoBuddyFXGui;
import com.sudo.v2.ui.model.XPInfo;
import com.sudo.v2.interfaces.IAntiBan;
import com.sudo.v2.interfaces.ISudoBot;
import com.sudo.v2.interfaces.ISudoTask;
import com.runemate.game.api.client.embeddable.EmbeddableUI;
import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.GameEvents;
import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.entities.definitions.ItemDefinition;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.net.GrandExchange;
import com.runemate.game.api.hybrid.region.GroundItems;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.osrs.net.OSBuddyExchange;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.LoopingBot;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.events.ItemEvent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

import java.util.*;

/**
 * SudoBot Abstract Class
 *
 * NOTE: Disable Game Events with
 * // GameEvents.RS3.UNEXPECTED_ITEM_HANDLER.disable();
 */
@Deprecated
public abstract class SudoBot extends LoopingBot implements ISudoBot, EmbeddableUI, InventoryListener {

    public boolean _guiWait = true, _useAntiBan = false, _useMiddleMouseCamera = false, _checkAnagogic = false, _enableRun = true, _isRS3 = true;
    public AntiBanHandler _abHandler = new AntiBanHandler(this);
    public Player _player;
    public int _grossIncome = 0, _runEnergy = 25;

    public ISudoTask _currentTask, _previousTask;

    public final StopWatch _STOPWATCH = new StopWatch();
    public SudoTimer _anagogicOrtTimer = new SudoTimer(10000, 30000);

    public SimpleObjectProperty<Node> _botInterfaceProperty;

    public LinkedHashMap<Skill, XPInfo> _XPInfoMap = new LinkedHashMap<>();
    public LinkedHashMap<String, String> _DisplayInfoMap = new LinkedHashMap<>();

    private HashMap<Integer, Integer> _acquiredIDs = new HashMap<>();

    public String _currentTaskString, _abTaskString;

    public ArrayList<Node> _GUI;

    public SudoBuddyFXGui _sbGUI;

    public Info _info = new Info(null , null, " ", " ", " ");

    public static ArrayList<IAntiBan> _antibans = new ArrayList<>();

    @Override
    public void onStart(String... args) {

        if(Environment.isRS3()) {
            _isRS3 = true;
            GameEvents.RS3.UNEXPECTED_ITEM_HANDLER.disable();
        }
        else
            _isRS3 = false;

        // Sets the length of time in milliseconds to wait before calling onLoop again
        setLoopDelay(100, 300);

        // Slow down the mouse
        Mouse.setSpeedMultiplier(1);

        // Force menu interaction when clicking
        Mouse.setForceMenuInteraction(true);

        // Start the stopwatch
        _STOPWATCH.start();

        // Start anagogic timer
        _anagogicOrtTimer.start();
    }

    @Override
    public void onLoop(){
        if (!Execution.delayUntil(() -> !_guiWait, 60000)) {
            Methods.updateCurrentTask("A minute has passed without initial start. Bot has stopped to prevent infinite loop.", this);
            stop();
            return;
        }

        updateInfo(); // UpdateUI GUI Info

        _player = Players.getLocal();

        // If user enabled antiban
        if (_useAntiBan)
            _abHandler.executeAntiBan(_antibans);

        // Make sure playing is running
        if(_enableRun && !Traversal.isRunEnabled() && Traversal.getRunEnergy() > _runEnergy) {
            Traversal.toggleRun();

            // Change runEnergy threshold to a different number
            _runEnergy = 20 + (int)(Math.random() * 20);
        }

        // Check for Anagogic Ort
        if(_checkAnagogic) {
            // If a Anagogic Ort appears on the ground, grab it.
            GroundItem anagogicOrt = GroundItems.newQuery().names("Anagogic ort").results().nearest();
            if (_anagogicOrtTimer.getRemainingTime() <= 0 && anagogicOrt != null) {
                if(_previousTask != _currentTask) {
                    _previousTask = _currentTask;
                }
                _currentTask = new AnagogicOrtTask(this);
                _currentTask.OnTaskCompleted((ISudoTask task) -> OnTaskComplete(task));
            }
        }
    }

    public void changeProperty(int index){
        if(_GUI != null) {
            if (index < _GUI.size())
                Methods.debug("Setting EmbeddableUI to Index: " + index);
                _botInterfaceProperty.set(_GUI.get(index));
        }
    }

    public void setCurrentTask(String foo){
        _currentTaskString = foo;
        updateInfo();
    }

    public void setAntiBanTask(String foo){
        _abTaskString = foo;
        updateInfo();
    }

    @Override
    public void OnTaskComplete(ISudoTask completedTask) {
        if (completedTask instanceof AnagogicOrtTask)
            _currentTask = _previousTask;
    }

    @Override
    public void onItemAdded(ItemEvent event) {
        ItemDefinition definition = event.getItem().getDefinition();
        if (definition != null) {
            if(!_acquiredIDs.containsKey(definition.getId())){
                try {
                    _acquiredIDs.put(definition.getId(), GrandExchange.lookup(definition.getId()).getPrice());
                }catch(Exception e){
                    _acquiredIDs.put(definition.getId(), 0);
                }
            }
            _grossIncome += _acquiredIDs.get(definition.getId());
        }
    }

    @Override
    public void onItemRemoved(ItemEvent event){
        ItemDefinition definition = event.getItem().getDefinition();
        if (definition != null) {
            if(!_acquiredIDs.containsKey(definition.getId())){
                try {
                    if(Environment.isRS3())
                        _acquiredIDs.put(definition.getId(), GrandExchange.lookup(definition.getId()).getPrice());
                    else
                        _acquiredIDs.put(definition.getId(), OSBuddyExchange.getGuidePrice(definition.getId()).getSelling());
                }catch(Exception e){
                    _acquiredIDs.put(definition.getId(), 0);
                }
            }
            _grossIncome -= _acquiredIDs.get(definition.getId());
        }
    }
}
