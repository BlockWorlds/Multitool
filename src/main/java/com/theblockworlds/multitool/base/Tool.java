package com.theblockworlds.multitool.base;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.theblockworlds.multitool.Multitool;

public abstract class Tool {
	private Material material;
	private String name;
	private final Multitool pl;
	
	public Tool(Multitool pl){
		this.pl = pl;
		this.setParameters();
	}
	
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
	
	public ItemStack getItemStack() {
		ItemStack items = new ItemStack(this.material, 1, (short) -1);
		ItemMeta meta = items.getItemMeta();
		meta.setDisplayName(this.name);
		items.setItemMeta(meta);
		return items;
	}
	
	protected int cfgLoadInt(int defaultValue){
		int temp = pl.getConfig().getInt("tools." + this.name);
		if(temp == 0){
			return defaultValue;
		}
		return temp;
	}
	
	protected String cfgLoadString(String defaultValue){
		String temp = pl.getConfig().getString("tools." + this.name);
		if(temp == null){
			return defaultValue;
		}
		return temp;
	}
	
	protected abstract void setParameters();
	public abstract void onUse(final Block targetBlock, final BlockFace face, final ItemStack itemUsed, final Player player, final Action action);
	public abstract void onRangedUse(final Block targetBlock, final BlockFace face, final ItemStack itemUsed, final Player player, final Action action);
}
