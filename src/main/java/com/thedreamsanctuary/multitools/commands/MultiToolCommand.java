package com.thedreamsanctuary.multitools.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.thedreamsanctuary.multitools.MultiTools;
import com.thedreamsanctuary.multitools.base.CommandHandler;
import com.thedreamsanctuary.multitools.base.Tool;
import com.thedreamsanctuary.multitools.handlers.ToolHandler;
import com.thedreamsanctuary.multitools.util.Utils;

public class MultiToolCommand extends CommandHandler {
	private static final String PERMISSIONS_PREFIX = "multitools.command.";
	
	public MultiToolCommand(MultiTools pl) {
		super(pl);
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You have to be ingame to use this command.");
			return true;
		}
		Player p = (Player) sender;
		if (args.length == 2 && Utils.equalsIgnoreCase(args[0], "get", "give")) {
			if (args[1].equals("all")) {
				return giveAllAllowedTools(p);
			}
			Tool tool = pl.getToolHandler().getTool(args[1]);
			if (tool != null && tool.getItemStack() != null && hasPermission(p, PERMISSIONS_PREFIX + "get." + args[1].replace(" ", "").toLowerCase())) {
				p.getInventory().addItem(tool.getItemStack());
				return true;
			}
		}
		if (args.length != 1) {
			return false;
		}
		if (Utils.equalsIgnoreCase(args[0], "longrange", "range") && hasPermission(p, PERMISSIONS_PREFIX + "togglerange")) {
			ToolHandler.toggleRange(p);
			return true;
		}
		if (Utils.equalsIgnoreCase(args[0], "alledit", "all") && hasPermission(p, PERMISSIONS_PREFIX + "togglealledit")) {
			ToolHandler.toggleAllBlocks(p);
			return true;
		}
		if (args[0].equals("list") && hasPermission(p, PERMISSIONS_PREFIX + "list")) {
			printList(sender);
			return true;
		}
		return false;
	}
	
	private boolean giveAllAllowedTools(Player player) {
		boolean giveMessage = true;
		for (Tool t : pl.getToolHandler().getTools()) {
			String name = t.getName().replace(" ", "").toLowerCase();
			if (player.hasPermission(PERMISSIONS_PREFIX + "get." + name)) {
				player.getInventory().addItem(t.getItemStack());
				giveMessage = false;
			}
		}
		if (giveMessage) {
			player.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
		}
		return !giveMessage;
	}
	
	private static boolean hasPermission(Player player, String permission) {
		if (player.hasPermission(permission)) {
			return true;
		}
		else {
			player.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
			return false;
		}
	}
	
	private void printList(CommandSender sender){
		sender.sendMessage(ChatColor.AQUA + "Tools:");
		for (Tool t : pl.getToolHandler().getTools()) {
			sender.sendMessage(ChatColor.GRAY + "- " + ChatColor.AQUA + t.getName().replaceAll(" ", ""));
		}
	}
}
