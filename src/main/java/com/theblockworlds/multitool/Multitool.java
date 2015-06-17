package com.theblockworlds.multitool;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.theblockworlds.multitool.util.Debugger;
import com.theblockworlds.multitool.commands.ToolsCommand;
import com.theblockworlds.multitool.handlers.ToolHandler;
import com.theblockworlds.multitool.listeners.ToolListener;

public class Multitool extends JavaPlugin{

	private ToolHandler toolHandler;
	private ToolListener listener;

	public void onEnable(){
		Debugger.debug("LOADING MULTITOOL CONFIG");
		this.saveDefaultConfig();
		
		Debugger.debug("Initializing ToolHandler!");
		this.toolHandler = new ToolHandler(this);
		
		/* REGISTER TOOLS HERE */
		
		
		/* REGISTER COMMANDS HERE */
		Debugger.debug("Registering Tools command!");
		ToolsCommand toolsHandler = new ToolsCommand(this);
		this.getCommand("tools").setExecutor(toolsHandler);
		this.getCommand("tools").setTabCompleter(toolsHandler);
		
		
		/* REGISTER LISTENERS HERE */
		
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
