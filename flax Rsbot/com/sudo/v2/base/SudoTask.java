package com.sudo.v2.base;

import com.sudo.v2.interfaces.ISudoTask;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Abstract Class for SudoTask
 */
@Deprecated
public abstract class SudoTask implements ISudoTask {
    protected Consumer<ISudoTask> _taskCompletedCallback;
    protected ArrayList<ISudoTask> _subTasks;
    protected ISudoTask _currentSubTask;

    public SudoTask() {
        _subTasks = new ArrayList<>();
    }

    protected final void Complete() {
        if (_taskCompletedCallback != null)
            _taskCompletedCallback.accept(this);
    }

    @Override
    public void Start() {

    }
    @Override
    public void Stop() {

    }
    @Override
    public void Pause() {

    }

    @Override
    public void Loop() {
        if (_currentSubTask != null)
            _currentSubTask.Loop();
    }

    @Override
    public void OnTaskCompleted(Consumer<ISudoTask> callback) {
        _taskCompletedCallback = callback;
    }

    // start next subtask in list
    protected void OnSubTaskComplete(ISudoTask subTask) {
        if (_subTasks.contains(subTask)) {
            int taskIndex = _subTasks.indexOf(subTask);
            if (taskIndex > -1) {
                if (taskIndex++ >= _subTasks.size()) {
                    Complete();
                }
                else {
                    _currentSubTask = _subTasks.get(taskIndex);
                    _currentSubTask.Start();
                }
            }
        }
    }
}
