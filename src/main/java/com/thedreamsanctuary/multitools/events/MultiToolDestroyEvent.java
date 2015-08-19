package com.thedreamsanctuary.multitools.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.thedreamsanctuary.multitools.base.MultiToolEvent;

public final class MultiToolDestroyEvent extends MultiToolEvent {
	private final boolean physics;
	
	public MultiToolDestroyEvent(Block block, Player player, boolean physics) {
		super(block, player);
		this.physics = physics;
	}
	
	public boolean isPhysicsOn() {
		return physics;
	}
}
