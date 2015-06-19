package com.theblockworlds.multitool.handlers;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
import com.theblockworlds.multitool.util.BlockRangeHelper;

public class ToolHandler {
	private static final String RANGED_PERM_PREFIX = "multitool.ranged.";
	private static final String PERM_PREFIX = "multitool.use.";
	
	private static Set<UUID> rangedPlayers = new HashSet<UUID>();
	private Map<Material, Tool> registeredTools = new HashMap<Material, Tool>();
	private final Multitool plugin;
	public ToolHandler(Multitool pl) {
		this.plugin = pl;
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
	
	public void onRangedUse(final Player player, final ItemStack itemUsed, Action action) {
		if (this.registeredTools.containsKey(itemUsed.getType())) {
			if (!this.hasPermission(player) || !rangedPlayers.contains(player.getUniqueId())) {
				return;
			}
			final BlockRangeHelper rangeHelper = new BlockRangeHelper(player, player.getWorld());
			final Block tarBlock = rangeHelper.getTargetBlock();
			final Block previousBlock = rangeHelper.getLastBlock();
			if (tarBlock != null && previousBlock != null) {
				final BlockFace tarFace = tarBlock.getFace(previousBlock);
				final Tool tool = this.registeredTools.get(itemUsed.getType());
				if (player.hasPermission(ToolHandler.RANGED_PERM_PREFIX + tool.getName().replaceAll(" ", "").toLowerCase())) {
					try {
						tool.onRangedUse(tarBlock, tarFace, itemUsed, player, action);
						itemUsed.setDurability((short) 0);
						player.updateInventory();
					}
					catch (final Exception e) {
						this.plugin.getLogger().severe("Tool Error: Could not pass ranged tool use to " + tool.getName());
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public boolean onUse(final Player player, final ItemStack itemUsed, Action action, Block tarBlock, BlockFace tarFace) {
		if (this.registeredTools.containsKey(itemUsed.getType())) {
			if (!this.hasPermission(player)){
				return false;
			}
			final Tool tool = this.registeredTools.get(itemUsed.getType());
			if (player.hasPermission(ToolHandler.PERM_PREFIX + tool.getName().replaceAll(" ", "").toLowerCase())) {
				try {
					Validate.notNull(tarFace);
					this.registeredTools.get(itemUsed.getType()).onUse(tarBlock, tarFace, itemUsed, player, action);
					itemUsed.setDurability((short) 0);
					player.updateInventory();
				}
				catch (final Exception e) {
					this.plugin.getLogger().severe("Tool Error: Could not pass tool use to " + tool.getName());
					e.printStackTrace();
				}
				return true;
			}
		}
		return false;
	}
	
	private boolean hasPermission(final Player player) {
		if ((player.isOp() || player.hasPermission("multitool.world."+player.getWorld().getName()))) {
			return true;
		} else {
	   		player.sendMessage(ChatColor.RED + "You are not allowed to do that here!");
	   		return false;
		}
	}
}
