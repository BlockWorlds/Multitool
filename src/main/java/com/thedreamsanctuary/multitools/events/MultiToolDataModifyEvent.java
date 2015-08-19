package com.thedreamsanctuary.multitools.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.thedreamsanctuary.multitools.base.MultiToolEvent;

public class MultiToolDataModifyEvent extends MultiToolEvent {
	
	public MultiToolDataModifyEvent(Block block, Player player) {
		super(block, player);
	}
}
