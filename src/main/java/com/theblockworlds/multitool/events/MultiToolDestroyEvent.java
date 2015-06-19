package com.theblockworlds.multitool.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

public final class MultiToolDestroyEvent extends BlockEvent implements Cancellable {
	private static final HandlerList HANDLERS = new HandlerList();
	private Player player;
    private boolean noPhysics;
    private boolean cancelled;
    
    public MultiToolDestroyEvent(Block theBlock, Player player, boolean noPhysics) {
		super(theBlock);
		this.noPhysics = noPhysics;
		this.player = player;
	}

    public boolean isNoPhysics() {
        return noPhysics;
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
