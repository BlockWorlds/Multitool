package com.theblockworlds.multitool.tools;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.theblockworlds.multitool.Multitool;
import com.theblockworlds.multitool.base.Tool;
import com.theblockworlds.multitool.events.MultiToolMoveEvent;
import com.theblockworlds.multitool.util.BlockDataHelper;

public class Sledgehammer extends Tool {

	public Sledgehammer(Multitool pl) {
		super(pl);
	}

	@Override
	protected void setParameters() {
		setName("Sledgehammer");
		setMaterial(cfgLoadMaterial(Material.GOLD_PICKAXE));
	}
	
	@Override
	public ItemStack getItemStack(){
		ItemStack items = new ItemStack(getMaterial(), 1, (short) -1);
		ItemMeta meta = items.getItemMeta();
		meta.setDisplayName(getName());
		meta.setLore(Arrays.asList("Sneak to force move", "Left Click to push", "Right Click to pull"));
		items.setItemMeta(meta);
		return items;
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
