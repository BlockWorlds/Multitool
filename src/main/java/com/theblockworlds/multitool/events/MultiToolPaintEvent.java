package com.theblockworlds.multitool.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.theblockworlds.multitool.base.MultiToolEvent;

public class MultiToolPaintEvent extends MultiToolEvent {
	private final boolean applyingPaint;

	public MultiToolPaintEvent(Block block, Player player, boolean applyingPaint) {
		super(block, player);
		this.applyingPaint = applyingPaint;
	}

	public boolean isApplyingPaint() {
		return applyingPaint;
	}
}
