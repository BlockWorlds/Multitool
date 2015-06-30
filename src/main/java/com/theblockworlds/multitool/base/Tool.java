package com.theblockworlds.multitool.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	private List<String> lore = new ArrayList<String>();
	private final Multitool pl;
	
	public Tool(Multitool pl){
		this.pl = pl;
		this.setParameters();
	}
	
	/** Sets material representing the tool
	 * 
	 * @param material		Material representing the tool
	 */
	protected void setMaterial(Material material) {
		this.material = material;
	}
	
	/** Sets name of the tool
	 * 
	 * @param name		Name of the tool
	 */
	protected void setName(String name) {
		this.name = name;
	}
	
	/** Sets text for when mouse hover over tool in inventory
	 * 
	 * @param lore		Lines of text
	 */
	protected void setLore(String... lore) {
		this.lore.addAll(Arrays.asList(lore));
	}
	
	/** @return 	Material representing the tool
	 */
	public Material getMaterial(){
		return material;
	}
	
	/** @return		Name of tool
	 */
	public String getName(){
		return name;
	}
	
	/** @return		List of string-lines showing when mouse hover over tool in inventory
	 */
	public List<String> getLore() {
		return lore;
	}
	
	/** @return		Item stack representing the tool in inventory
	 */
	public ItemStack getItemStack() {
		ItemStack items = new ItemStack(getMaterial(), 1, (short) -1);
		ItemMeta meta = items.getItemMeta();
		meta.setDisplayName(getName());
		meta.setLore(lore);
		items.setItemMeta(meta);
		return items;
	}
	
	/** Gets material either from config or the default material
	 * 
	 * @param defaultMaterial		Default material
	 * @return						Material from config if material found, returns defaultMaterial otherwise
	 */
	protected Material cfgLoadMaterial(Material defaultMaterial){
		String temp = pl.getConfig().getString("tools." + name.toLowerCase().replaceAll(" ", "") + ".material");
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
