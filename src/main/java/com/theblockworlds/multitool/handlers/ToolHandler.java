package com.theblockworlds.multitool.handlers;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import com.theblockworlds.multitool.Multitool;
import com.theblockworlds.multitool.base.Tool;

public class ToolHandler {
	private static final String RANGED_PERMISSIONS_PREFIX = "multitool.ranged.";
	private static final String PERMISSIONS_PREFIX = "multitool.use.";
	private static final HashSet<Material> NULL_SET = null;//Avoid deprecation
	
	private static HashSet<Material> transparantBlocks = new HashSet<Material>();
	private static Set<UUID> rangedPlayers = new HashSet<UUID>();
	private static Set<UUID> allBlocksPlayers = new HashSet<UUID>();
	private Map<Material, Tool> registeredTools = new HashMap<Material, Tool>();
	private final Multitool plugin;
	
	public ToolHandler(Multitool pl) {
		this.plugin = pl;
		transparantBlocks.add(Material.AIR);
		transparantBlocks.add(Material.STATIONARY_WATER);
		transparantBlocks.add(Material.WATER);
		transparantBlocks.add(Material.STATIONARY_LAVA);
		transparantBlocks.add(Material.LAVA);
	}
	
	public Collection<Tool> getTools() {
		return registeredTools.values();
	}
	
	/** Gets the tool with the specified name
	 * 
	 * @param name		Name of tool without spaces
	 */
	public Tool getTool(String name) {
		for (Tool tool : registeredTools.values()) {
			if (tool.getName().replaceAll(" ", "").equalsIgnoreCase(name)) {
				return tool;
			}
		}
		return null;
	}
	
	/** Toggles whether a player can edit all blocks or only solids
	 * 
	 * @param player		Toggled player
	 */
	public static void toggleAllBlocks(Player player) {
		if (allBlocksPlayers.contains(player.getUniqueId())) {
			allBlocksPlayers.remove(player.getUniqueId());
			player.sendMessage(ChatColor.GRAY + "All blocks editing:" + ChatColor.AQUA + " OFF");
		}
		else {
			allBlocksPlayers.add(player.getUniqueId());
			player.sendMessage(ChatColor.GRAY + "All blocks editing:" + ChatColor.AQUA + " ON");
		}
	}
	
	
	/** Toggles whether a player has long range or short range
	 * 
	 * @param player		Toggled player
	 */
	public static void toggleRange(Player player) {
		if (rangedPlayers.contains(player.getUniqueId())) {
			rangedPlayers.remove(player.getUniqueId());
			player.sendMessage(ChatColor.GRAY + "Long range:" + ChatColor.AQUA + " OFF");
		}
		else {
			rangedPlayers.add(player.getUniqueId());
			player.sendMessage(ChatColor.GRAY + "Long range:" + ChatColor.AQUA + " ON");
		}
	}
	
	/** Validates tool and register it for usage by MultiTool
	 * 
	 * @param tool		Tool being registered
	 */
	public void registerTool(Tool tool) {
		Validate.notNull(tool, "You cannot register null tools");
		Validate.notNull(tool.getMaterial(), "You cannot register tools without a tool material");
		Validate.notNull(tool.getName(), "You cannot register tools with no name");
		Validate.isTrue(!tool.getName().isEmpty(), "You cannot register tools with no name");
		Validate.isTrue(!this.registeredTools.containsKey(tool.getMaterial()), "You cannot register multiple tools with the same material");
		
		this.registeredTools.put(tool.getMaterial(), tool);
		this.plugin.getLogger().info("Registered tool: " + tool.getName());
	}
	
	/** If a tool is held by player, the task of the tool will be executed
	 * 
	 * @param player			Player using the tool
	 * @param itemUsed			Item stack held in hand by player
	 * @param action			Action done by player
	 * @param targetBlock		Task will be applied to this block
	 * @param face				The face of targetBlock being clicked
	 * @return					True if the PlayerInteractEvent is wanted canceled
	 */
	public boolean onUse(Player player, ItemStack itemUsed, Action action, Block tarBlock, BlockFace tarFace) {
		if (registeredTools.containsKey(itemUsed.getType())) {
			List<Block> lastBlocks = null;
			Tool tool = registeredTools.get(itemUsed.getType());
			switch(Cases.getCase(player, tarBlock, tool)) {
			case ALL_LONG_RANGE:
				lastBlocks = player.getLastTwoTargetBlocks(NULL_SET, 200);
				break;
			case LONG_RANGE:
				lastBlocks = player.getLastTwoTargetBlocks(transparantBlocks, 200);
				break;
			case ALL_SHORT_RANGE:
				lastBlocks = player.getLastTwoTargetBlocks(NULL_SET, 5);
				break;
			case SHORT_RANGE:
				break;
			default:
				return false;
			}
			if (lastBlocks != null) {
				tarBlock = lastBlocks.get(1);
				tarFace = tarBlock.getFace(lastBlocks.get(0));
			}
			if (tarBlock != null && tarFace != null) {
				try {
					itemUsed.setDurability((short) 0);
					return tool.onUse(tarBlock, tarFace, itemUsed, player, action);
				}
				catch (Exception e) {
					this.plugin.getLogger().severe("Tool Error: Could not pass ranged tool use to " + tool.getName());
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	/** Enum containing possible cases MultiTool may encounter
	 */
	enum Cases {
		LONG_RANGE, SHORT_RANGE, ALL_LONG_RANGE, ALL_SHORT_RANGE, NO_PERMISSION;
		
		/** Finds fitting case for the situation and checks permissions
		 * 
		 * @param player		Player using tool
		 * @param tarBlock		Block getting task applied to
		 * @param tool			Tool applying task to tarBlock
		 * @return				The Cases enum fitting the situation
		 */
		public static Cases getCase(Player player, Block tarBlock, Tool tool) {
			if (!hasPermission(player)) {
				return NO_PERMISSION;
			}
			if (tarBlock == null && allBlocksPlayers.contains(player.getUniqueId()) && rangedPlayers.contains(player.getUniqueId()) && player.hasPermission(RANGED_PERMISSIONS_PREFIX + tool.getName().replaceAll(" ", "").toLowerCase())) {
				return ALL_LONG_RANGE;
			}
			if (tarBlock == null && rangedPlayers.contains(player.getUniqueId()) && player.hasPermission(RANGED_PERMISSIONS_PREFIX + tool.getName().replaceAll(" ", "").toLowerCase())) {
				return LONG_RANGE;
			}
			if (allBlocksPlayers.contains(player.getUniqueId()) && player.hasPermission(PERMISSIONS_PREFIX + tool.getName().replaceAll(" ", "").toLowerCase())) {
				return ALL_SHORT_RANGE;
			}
			if (player.hasPermission(PERMISSIONS_PREFIX + tool.getName().replaceAll(" ", "").toLowerCase())) {
				return SHORT_RANGE;
			}
			return NO_PERMISSION;
		}
	}
	
	private static boolean hasPermission(final Player player) {
		if ((player.isOp() || player.hasPermission("multitool.world."+player.getWorld().getName()))) {
			return true;
		} else {
	   		player.sendMessage(ChatColor.RED + "You are not allowed to do that here!");
	   		return false;
		}
	}
}
