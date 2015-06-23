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
	public boolean onUse(Block targetBlock, BlockFace face, ItemStack itemUsed, Player player, Action action) {
		boolean physics = action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK;
		MultiToolDestroyEvent destroyEvent = new MultiToolDestroyEvent(targetBlock, player, physics);
		Bukkit.getPluginManager().callEvent(destroyEvent);
		if (!destroyEvent.isCancelled()) {
			targetBlock.setType(Material.AIR, physics);
		}
		return true;
	}
	
	@Override
	public ItemStack getItemStack(){
		ItemStack items = new ItemStack(getMaterial(), 1, (short) -1);
		ItemMeta meta = items.getItemMeta();
		meta.setDisplayName(getName());
		meta.setLore(Arrays.asList("Left click to remove", "Right click to no-physics remove"));
		items.setItemMeta(meta);
		return items;
	}
}
