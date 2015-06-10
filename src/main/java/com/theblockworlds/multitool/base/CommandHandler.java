package com.theblockworlds.multitool.base;

import org.bukkit.command.CommandExecutor;

import com.theblockworlds.multitool.Multitool;

public abstract class CommandHandler implements CommandExecutor{
	protected final Multitool pl;
	public CommandHandler(Multitool pl){
		this.pl = pl;
	}
}
