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
		performAction(targetBlock, face, itemUsed, player, action);
	}

	@Override
	public void onRangedUse(Block targetBlock, BlockFace face, ItemStack itemUsed, Player player, Action action) {
		performAction(targetBlock, face, itemUsed, player, action);
	}
	
	@Override
	public ItemStack getItemStack(){
		ItemStack items = new ItemStack(this.getMaterial(), 1, (short) -1);
		ItemMeta meta = items.getItemMeta();
		meta.setDisplayName(this.getName());
		meta.setLore(Arrays.asList("Left Click to remove","Right Click to nophysics remove"));
		items.setItemMeta(meta);
		return items;
	}
	
	private void performAction(Block targetBlock, BlockFace face, ItemStack itemUsed, Player player, Action action) {
		if ((action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) && callEventSuccess(targetBlock, player, false)) {
			targetBlock.setType(Material.AIR);
		}
		else if (callEventSuccess(targetBlock, player, true)) {
			targetBlock.setType(Material.AIR, false);
		}
	}
	
	private boolean callEventSuccess(Block targetBlock, Player player, boolean noPhysics) {
		MultiToolDestroyEvent destroyEvent = new MultiToolDestroyEvent(targetBlock, player, noPhysics);
		Bukkit.getPluginManager().callEvent(destroyEvent);
        return !destroyEvent.isCancelled();
	}
}
