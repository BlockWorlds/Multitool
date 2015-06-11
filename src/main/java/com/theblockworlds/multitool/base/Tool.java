package com.theblockworlds.multitool.base;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public abstract class Tool {
	private Material material;
	private String name;
	
	protected void setMaterial(Material m){
		this.material = m;
	}
	
	protected void setName(String name){
		this.name = name;
	}
	
	public Material getMaterial(){
		return this.material;
	}
	
	public String getName(){
		return this.name;
	}

	abstract void setParameters();
	public abstract void onUse(final Block targetBlock, final BlockFace face, final ItemStack itemUsed, final Player player, final Action action);
	public abstract void onRangedUse(final Block targetBlock, final BlockFace face, final ItemStack itemUsed, final Player player, final Action action);
}
