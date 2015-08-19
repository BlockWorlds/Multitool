package com.theblockworlds.multitool;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.theblockworlds.multitool.commands.MultiToolCommand;
import com.theblockworlds.multitool.handlers.ToolHandler;
import com.theblockworlds.multitool.listeners.ToolListener;
import com.theblockworlds.multitool.tools.DataWrench;
import com.theblockworlds.multitool.tools.Duplicator;
import com.theblockworlds.multitool.tools.Jackhammer;
import com.theblockworlds.multitool.tools.PaintBrush;
import com.theblockworlds.multitool.tools.Sledgehammer;
import com.theblockworlds.multitool.tasks.PaintBrushRemover;

public class Multitool extends JavaPlugin {

	private ToolHandler toolHandler;
	private ToolListener listener;

	public void onEnable() {
		//Debugger.debug("LOADING MULTI TOOL CONFIG!");
		this.saveDefaultConfig();
		
		//Debugger.debug("Initializing ToolHandler!");
		this.toolHandler = new ToolHandler(this);
		
		//Debugger.debug("Registering tools!");
		toolHandler.registerTool(new Jackhammer(this));
		toolHandler.registerTool(new Sledgehammer(this));
		toolHandler.registerTool(new Duplicator(this));
		toolHandler.registerTool(new DataWrench(this));
		toolHandler.registerTool(new PaintBrush(this));
		
		//Debugger.debug("Registering MultiToolCommand!");
		MultiToolCommand commands = new MultiToolCommand(this);
		this.getCommand("multitool").setExecutor(commands);
		
		//Debugger.debug("Registering listeners!");
		this.listener = new ToolListener(this);
	    Bukkit.getPluginManager().registerEvents(this.listener, this);
	    
	    //Debugger.debug("Registering tasks!");
	    PaintBrushRemover task = new PaintBrushRemover(this);
	    Bukkit.getScheduler().runTaskTimer(this, task, 0, 6000);
	}
	
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
	}
	
	public ToolHandler getToolHandler() {
		return this.toolHandler;
	}
}
