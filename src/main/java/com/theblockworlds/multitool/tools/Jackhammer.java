package com.theblockworlds.multitool.tools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import com.theblockworlds.multitool.Multitool;
import com.theblockworlds.multitool.base.Tool;
import com.theblockworlds.multitool.events.MultiToolDestroyEvent;

public class Jackhammer extends Tool {

	public Jackhammer(Multitool pl) {
		super(pl);
	}

	@Override
	protected void setParameters() {
		setName("Jackhammer");
		setMaterial(cfgLoadMaterial(Material.DIAMOND_PICKAXE));
	}

	@Override
	public void onUse(Block targetBlock, BlockFace face, ItemStack itemUsed, Player player, Action action) {
		preformAction(targetBlock, face, itemUsed, player, action);
	}

	@Override
	public void onRangedUse(Block targetBlock, BlockFace face, ItemStack itemUsed, Player player, Action action) {
		preformAction(targetBlock, face, itemUsed, player, action);
	}
	
	private void preformAction(Block targetBlock, BlockFace face, ItemStack itemUsed, Player player, Action action) {
		MultiToolDestroyEvent destoryEvent = new MultiToolDestroyEvent(targetBlock, player);
		Bukkit.getPluginManager().callEvent(destoryEvent);
		if (destoryEvent.isCancelled()) {
			return;
		}
		if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
			targetBlock.setType(Material.AIR);
		}
		else {
			destoryEvent.setNoPhyscis(true);
			targetBlock.setType(Material.AIR, false);
		}
	}
}
