package com.theblockworlds.multitool.events;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockFromToEvent;

public class MultiToolMoveEvent extends BlockFromToEvent {
	private Player player;

	public MultiToolMoveEvent(Block block, BlockFace face, Player player) {
		super(block, face);
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
}
