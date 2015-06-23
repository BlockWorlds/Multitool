package com.theblockworlds.multitool;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.theblockworlds.multitool.util.Debugger;
import com.theblockworlds.multitool.commands.MultiToolCommand;
import com.theblockworlds.multitool.handlers.ToolHandler;
import com.theblockworlds.multitool.listeners.ToolListener;
import com.theblockworlds.multitool.tools.Jackhammer;
import com.theblockworlds.multitool.tools.Sledgehammer;

public class Multitool extends JavaPlugin{

	private ToolHandler toolHandler;
	private ToolListener listener;

	public void onEnable(){
		Debugger.debug("LOADING MULTI TOOL CONFIG!");
		this.saveDefaultConfig();
		
		Debugger.debug("Initializing ToolHandler!");
		this.toolHandler = new ToolHandler(this);
		
		Debugger.debug("Registering tools!");
		toolHandler.registerTool(new Jackhammer(this));
		toolHandler.registerTool(new Sledgehammer(this));
		
		Debugger.debug("Registering MultiToolCommand!");
		MultiToolCommand multiToolHandler = new MultiToolCommand(this);
		this.getCommand("multitool").setExecutor(multiToolHandler);
		
		Debugger.debug("Registering listeners!");
		this.listener = new ToolListener(this);
	    Bukkit.getPluginManager().registerEvents(this.listener, this);
	}
	
	public void onDisable(){
		
	}
	
	public ToolHandler getToolHandler(){
		return this.toolHandler;
	}
}
