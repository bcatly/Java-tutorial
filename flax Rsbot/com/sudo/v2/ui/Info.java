package com.sudo.v2.ui;

import com.sudo.v2.ui.model.XPInfo;
import com.runemate.game.api.hybrid.local.Skill;

import java.util.LinkedHashMap;

public class Info {

	public int gpPH, itemCount;

	public LinkedHashMap<Skill, XPInfo> xpInfoMap = new LinkedHashMap<>();
	public LinkedHashMap<String, String> displayInfoMap = new LinkedHashMap<>();

	public String runTime, currentTask, abTask;

	public Info(){
		xpInfoMap = null;
		gpPH = 0;
		itemCount = 0;
		runTime = "";
		currentTask = "";
		abTask = "";
	}

	public Info(LinkedHashMap<Skill, XPInfo> xpInfoMap, LinkedHashMap<String, String> displayInfoMap, String runTime, String currentTask, String abTask){
		this.xpInfoMap = xpInfoMap;
		this.displayInfoMap = displayInfoMap;
		this.runTime = runTime;
		this.currentTask = currentTask;
		this.abTask = abTask;
	}

}
