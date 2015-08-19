package com.thedreamsanctuary.multitools.base;

import org.bukkit.command.CommandExecutor;

import com.thedreamsanctuary.multitools.MultiTools;

public abstract class CommandHandler implements CommandExecutor{
	protected final MultiTools pl;
	public CommandHandler(MultiTools pl){
		this.pl = pl;
	}
}
