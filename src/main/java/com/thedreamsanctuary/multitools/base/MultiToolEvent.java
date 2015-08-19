package com.thedreamsanctuary.multitools.base;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

public abstract class MultiToolEvent extends BlockEvent implements Cancellable {
	private static final HandlerList HANDLERS = new HandlerList();
	private Player player;
	private boolean cancelled;
	
	public MultiToolEvent(Block block, Player player) {
		super(block);
		this.player = player;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
 
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}
	
	public Player getPlayer() {
		return player;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
}
