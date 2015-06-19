package com.theblockworlds.multitool;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.theblockworlds.multitool.commands.MultiToolCommand;
import com.theblockworlds.multitool.handlers.ToolHandler;
import com.theblockworlds.multitool.listeners.ToolListener;
import com.theblockworlds.multitool.tools.Jackhammer;
import com.theblockworlds.multitool.util.Debugger;

public class Multitool extends JavaPlugin{

	private ToolHandler toolHandler;
	private ToolListener listener;
	
	public void onEnable(){
		Debugger.debug("LOADING MULTI TOOL CONFIG");
		this.saveDefaultConfig();
		
		Debugger.debug("INITIALIZING TOOLHANDLER");
		this.toolHandler = new ToolHandler(this);
		
		Debugger.debug("REGISTERING TOOLS");
		toolHandler.registerTool(new Jackhammer(this));
		
		Debugger.debug("REGISTERING COMMANDS");
		MultiToolCommand multiToolHandler = new MultiToolCommand(this);
		this.getCommand("multitool").setExecutor(multiToolHandler);
		
		Debugger.debug("REGISTERING LISTENERS");
		this.listener = new ToolListener(this);
	    Bukkit.getPluginManager().registerEvents(this.listener, this);
		
	}
	
	public void onDisable(){
		
	}
	
	public ToolHandler getToolHandler(){
		return this.toolHandler;
	}
}
