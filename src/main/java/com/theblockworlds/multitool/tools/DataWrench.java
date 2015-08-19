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
import com.theblockworlds.multitool.events.MultiToolDataModifyEvent;
import com.theblockworlds.multitool.util.Utils;

public class DataWrench extends Tool {

	public DataWrench(Multitool pl) {
		super(pl);
	}

	@Override
	protected void setParameters() {
		setName("Data Wrench");
		setMaterial(cfgLoadMaterial(Material.BONE));
		setLore("Left click to scroll forwards through data values", "Left click to scroll backwards through data values");
	}

	@Override
	public boolean onUse(Block targetBlock, BlockFace face, ItemStack itemUsed, Player player, Action action) {
		MultiToolDataModifyEvent modifyEvent = new MultiToolDataModifyEvent(targetBlock, player);
		Bukkit.getPluginManager().callEvent(modifyEvent);
		if (!modifyEvent.isCancelled()) {
			int step = (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) ? 1 : -1;
			trySetData(targetBlock, step);
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	private void trySetData(Block targetBlock, int step) {
		try {
			targetBlock.setData(Utils.wrapAround(targetBlock.getData() + step), false);
		}
		catch(Exception e) {
		}
	}
}
