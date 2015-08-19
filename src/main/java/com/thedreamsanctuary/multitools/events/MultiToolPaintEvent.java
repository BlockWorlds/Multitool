package com.thedreamsanctuary.multitools.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.thedreamsanctuary.multitools.base.MultiToolEvent;

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
