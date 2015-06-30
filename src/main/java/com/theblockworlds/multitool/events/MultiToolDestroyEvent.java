package com.theblockworlds.multitool.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.theblockworlds.multitool.base.MultiToolEvent;

public final class MultiToolDestroyEvent extends MultiToolEvent {
	private boolean physics;
	
	public MultiToolDestroyEvent(Block block, Player player, boolean physics) {
		super(block, player);
		this.physics = physics;
	}
	
	public boolean isPhysicsOn() {
		return physics;
	}
	
	public void setPhyscis(boolean physics) {
		this.physics = physics;
	}
}
