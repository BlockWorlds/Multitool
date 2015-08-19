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
import com.thedreamsanctuary.multitools.events.MultiToolDuplicationEvent;

public class Duplicator extends Tool {

	public Duplicator(Multitool pl) {
		super(pl);
	}

	@Override
	protected void setParameters() {
		setName("Duplicator");
		setMaterial(cfgLoadMaterial(Material.STONE_AXE));
		setLore("Left click to duplicate one item", "Right click to duplicate a stack of items");
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onUse(Block targetBlock, BlockFace face, ItemStack itemUsed, Player player, Action action) {
		boolean fullStack = action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
		ItemStack stack = new ItemStack(targetBlock.getType(), fullStack ? 64 : 1, (short) targetBlock.getData());
		MultiToolDuplicationEvent duplicationEvent = new MultiToolDuplicationEvent(targetBlock, player, stack);
		Bukkit.getPluginManager().callEvent(duplicationEvent);
		if (!duplicationEvent.isCancelled()) {
			player.getInventory().addItem(stack);
		}
		return true;
	}
}
