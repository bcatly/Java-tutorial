package com.sudo.v2.interfaces;

import java.util.function.Consumer;

/**
 * SudoTask Interface
 */
public interface ISudoTask {
    void Start();
    void Loop();
    void Pause();
    void Stop();
    void OnTaskCompleted(Consumer<ISudoTask> callback);
}
