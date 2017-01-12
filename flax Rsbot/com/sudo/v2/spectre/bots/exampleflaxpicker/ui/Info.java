package com.sudo.v2.spectre.bots.exampleflaxpicker.ui;


/*
*
* --- Info Class ---
* This class is used to transfer information between
* the two threads we have in this bot. The GUI and bot threads.
*
* Inside exampleflaxpicker.java you will see that in updateInfo()
* we create a new Info class (this class) and assign its constructor
* the appropriate values. You do this so you can pass that newly gathered
* information to the GUI thread, in a thread-safe manor by Platform.runLater().
*
 */
public class Info {

	public int flaxPh, flaxCount;
	public String runTime, currentTask;

	public Info(){
		this.flaxPh = 0;
		this.flaxCount = 0;
		this.runTime = "";
		this.currentTask = "";
	}

	public Info(int flaxPh, int flaxCount, String runTime, String currentTask) {
		this.flaxPh = flaxPh;
		this.flaxCount = flaxCount;
		this.runTime = runTime;
		this.currentTask = currentTask;
	}

}
