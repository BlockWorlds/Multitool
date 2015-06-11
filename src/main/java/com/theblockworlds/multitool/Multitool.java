package com.theblockworlds.multitool;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.theblockworlds.multitool.handlers.ToolHandler;
import com.theblockworlds.multitool.listeners.ToolListener;

public class Multitool extends JavaPlugin{

	private ToolHandler toolHandler;
	private ToolListener listener;
	
	public void onEnable(){
	
		this.toolHandler = new ToolHandler(this);
		
		/* REGISTER TOOLS HERE */
		
		
		/* REGISTER COMMANDS HERE */
		
		
		/* REGISTER LISTENERS HERE */
		this.listener = new ToolListener(this);
	    Bukkit.getPluginManager().registerEvents(this.listener, this);
		
	}
	
	public void onDisable(){
		
	}
	
	public ToolHandler getToolHandler(){
		return this.toolHandler;
	}
	
}
