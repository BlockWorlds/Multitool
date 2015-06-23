package com.theblockworlds.multitool.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

public final class MultiToolDestroyEvent extends BlockEvent implements Cancellable {
	private static final HandlerList HANDLERS = new HandlerList();
	private Player player;
	private boolean physics;
	private boolean cancelled;
	
	/** Instantiate a MultiToolDestroyEvent where no-physics is false by default
	 * 
	 * @param block		Affected block
	 * @param player	Player executing event
	 */
	public MultiToolDestroyEvent(Block block, Player player) {
		super(block);
		this.player = player;
	}
	
	public MultiToolDestroyEvent(Block block, Player player, boolean physics) {
		this(block, player);
		this.physics = physics;
	}
	
	public boolean isPhysicsOn() {
		return physics;
	}
	
	public void setPhyscis(boolean physics) {
		this.physics = physics;
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
