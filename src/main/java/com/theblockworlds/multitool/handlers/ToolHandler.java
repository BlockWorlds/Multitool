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
	
	public void registerTool(Tool t) {
		Validate.notNull(t, "You cannot register null tools");
		Validate.notNull(t.getMaterial(), "You cannot register tools without a tool material");
		Validate.notNull(t.getName(), "You cannot register tools with no name");
		Validate.isTrue(!t.getName().isEmpty(), "You cannot register tools with no name");
		Validate.isTrue(!this.registeredTools.containsKey(t.getMaterial()), "You cannot register multiple tools with the same material");
		
		this.registeredTools.put(t.getMaterial(), t);
		this.plugin.getLogger().info("Registered tool: " + t.getName());
	}
	
	public boolean hasToolItem(Material material) {
		return registeredTools.containsKey(material);
	}
	
	public Collection<Tool> getTools() {
		return registeredTools.values();
	}
	
	public Tool getTool(String toolName) {
		for (Tool t : registeredTools.values()) {
			if (t.getName().equalsIgnoreCase(toolName)) {
				return t;
			}
		}
	    return null;
	}
	
	public static void toggleAllBlocks(Player p) {
		if (allBlocksPlayers.contains(p.getUniqueId())) {
			allBlocksPlayers.remove(p.getUniqueId());
			p.sendMessage(ChatColor.GRAY + "All blocks editing:" + ChatColor.AQUA + " OFF");
		}
		else {
			allBlocksPlayers.add(p.getUniqueId());
			p.sendMessage(ChatColor.GRAY + "All blocks editing:" + ChatColor.AQUA + " ON");
		}
	}
	
	public static void toggleRange(Player p) {
		if (rangedPlayers.contains(p.getUniqueId())) {
			rangedPlayers.remove(p.getUniqueId());
			p.sendMessage(ChatColor.GRAY + "Long range:" + ChatColor.AQUA + " OFF");
		}
		else {
			rangedPlayers.add(p.getUniqueId());
			p.sendMessage(ChatColor.GRAY + "Long range:" + ChatColor.AQUA + " ON");
		}
	}
	
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
	
	private static boolean hasPermission(final Player player) {
		if ((player.isOp() || player.hasPermission("multitool.world."+player.getWorld().getName()))) {
			return true;
		} else {
	   		player.sendMessage(ChatColor.RED + "You are not allowed to do that here!");
	   		return false;
		}
	}
	
	enum Cases {
		LONG_RANGE, SHORT_RANGE, ALL_LONG_RANGE, ALL_SHORT_RANGE, NO_PERMISSION;
		
		
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
}
