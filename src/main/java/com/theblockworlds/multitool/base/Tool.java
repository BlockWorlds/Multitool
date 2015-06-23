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
		return material;
	}
	
	public String getName(){
		return name;
	}
	
	public ItemStack getItemStack() {
		ItemStack items = new ItemStack(material, 1);
		ItemMeta meta = items.getItemMeta();
		meta.setDisplayName(name);
		items.setItemMeta(meta);
		return items;
	}
	
	/** Gets material either from config or the default material
	 * 
	 * @param defaultMaterial		Default material
	 * @return						Material from config if material found, returns defaultMaterial otherwise
	 */
	protected Material cfgLoadMaterial(Material defaultMaterial){
		String temp = pl.getConfig().getString("tools." + name.toLowerCase() + ".material");
		Material material = Material.matchMaterial(temp);
		if(material == null){
			return defaultMaterial;
		} 
		return material;
	}
	
	/** Sets default parameters used when initiating tool
	 */
	protected abstract void setParameters();
	
	/** Does task of tool held by player
	 * 
	 * @param targetBlock		Task will be applied to this block
	 * @param face				The face of targetBlock being clicked
	 * @param itemUsed			Item stack held in hand by player
	 * @param player			Player using the tool
	 * @param action			Action done by player
	 * @return					True if the PlayerInteractEvent is wanted canceled
	 */
	public abstract boolean onUse(final Block targetBlock, final BlockFace face, final ItemStack itemUsed, final Player player, final Action action);
}
