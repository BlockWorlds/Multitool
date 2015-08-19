package com.thedreamsanctuary.multitools.tools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import com.thedreamsanctuary.multitools.Multitool;
import com.thedreamsanctuary.multitools.base.Tool;
import com.thedreamsanctuary.multitools.events.MultiToolMoveEvent;
import com.thedreamsanctuary.multitools.util.BlockDataHelper;

public class Sledgehammer extends Tool {

	public Sledgehammer(Multitool pl) {
		super(pl);
	}

	@Override
	protected void setParameters() {
		setName("Sledgehammer");
		setMaterial(cfgLoadMaterial(Material.GOLD_PICKAXE));
		setLore("Sneak to force move", "Left click to push", "Right click to pull");
	}

	@Override
	public boolean onUse(Block targetBlock, BlockFace face, ItemStack itemUsed, Player player, Action action) {
		boolean forceMove = player.isSneaking();
		boolean push = action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK;
		return moveBlock(targetBlock, face, player, forceMove, push);
	}
	
	private boolean moveBlock(Block targetBlock, BlockFace face, Player player, boolean forceMove, boolean push) {
		face = push ? face.getOppositeFace() : face;
		Block destinationBlock = targetBlock.getRelative(face);
		MultiToolMoveEvent moveEvent = new MultiToolMoveEvent(targetBlock, face, player);
		Bukkit.getPluginManager().callEvent(moveEvent);
		if ((forceMove || destinationBlock.isEmpty()) && !moveEvent.isCancelled()) {
			BlockDataHelper.setTypeAndData(destinationBlock, targetBlock, false, true);
			targetBlock.setType(Material.AIR, false);
		}
		return true;
	}

}
