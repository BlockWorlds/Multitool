package com.theblockworlds.multitool.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.theblockworlds.multitool.base.MultiToolEvent;

public class MultiToolDataModifyEvent extends MultiToolEvent {
	
	public MultiToolDataModifyEvent(Block block, Player player) {
		super(block, player);
	}
}
