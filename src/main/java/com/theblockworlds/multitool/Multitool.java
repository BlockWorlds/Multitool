package com.theblockworlds.multitool;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.theblockworlds.multitool.commands.MultiToolCommand;
import com.theblockworlds.multitool.handlers.ToolHandler;
import com.theblockworlds.multitool.listeners.ToolListener;
import com.theblockworlds.multitool.tools.Jackhammer;

public class Multitool extends JavaPlugin{

	private ToolHandler toolHandler;
	private ToolListener listener;
	
	public void onEnable(){
		this.saveDefaultConfig();
		
		this.toolHandler = new ToolHandler(this);
		
		/* REGISTER TOOLS HERE */
		toolHandler.registerTool(new Jackhammer(this));
		
		/* REGISTER COMMANDS HERE */
		MultiToolCommand multiToolHandler = new MultiToolCommand(this);
		this.getCommand("multitool").setExecutor(multiToolHandler);
		
		
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
