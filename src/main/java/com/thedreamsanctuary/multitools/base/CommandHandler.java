package com.thedreamsanctuary.multitools.base;

import org.bukkit.command.CommandExecutor;

import com.thedreamsanctuary.multitools.Multitool;

public abstract class CommandHandler implements CommandExecutor{
	protected final Multitool pl;
	public CommandHandler(Multitool pl){
		this.pl = pl;
	}
}
