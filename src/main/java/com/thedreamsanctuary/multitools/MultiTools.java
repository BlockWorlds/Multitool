package com.thedreamsanctuary.multitools;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.thedreamsanctuary.multitools.commands.MultiToolCommand;
import com.thedreamsanctuary.multitools.handlers.ToolHandler;
import com.thedreamsanctuary.multitools.listeners.ToolListener;
import com.thedreamsanctuary.multitools.tasks.PaintBrushRemover;
import com.thedreamsanctuary.multitools.tools.DataWrench;
import com.thedreamsanctuary.multitools.tools.Duplicator;
import com.thedreamsanctuary.multitools.tools.Jackhammer;
import com.thedreamsanctuary.multitools.tools.PaintBrush;
import com.thedreamsanctuary.multitools.tools.Sledgehammer;

public class MultiTools extends JavaPlugin {

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
