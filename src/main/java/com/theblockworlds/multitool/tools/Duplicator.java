package com.theblockworlds.multitool.tools;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.theblockworlds.multitool.Multitool;
import com.theblockworlds.multitool.base.Tool;

public class Duplicator extends Tool {

	public Duplicator(Multitool pl) {
		super(pl);
	}

	@Override
	protected void setParameters() {
		setName("Duplicator");
		setMaterial(cfgLoadMaterial(Material.STONE_AXE));
	}
	
	@Override
	public ItemStack getItemStack() {
		ItemStack items = new ItemStack(getMaterial(), 1, (short) -1);
		ItemMeta meta = items.getItemMeta();
		meta.setDisplayName(getName());
		meta.setLore(Arrays.asList("Left click to dupilcate one item", "Right click to duplicate a stack of items"));
		items.setItemMeta(meta);
		return items;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onUse(Block targetBlock, BlockFace face, ItemStack itemUsed, Player player, Action action) {
		boolean fullStack = action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
		ItemStack stack = new ItemStack(targetBlock.getType(), fullStack ? 64 : 1, (short) targetBlock.getData());
		player.getInventory().addItem(stack);
		return true;
	}
}
