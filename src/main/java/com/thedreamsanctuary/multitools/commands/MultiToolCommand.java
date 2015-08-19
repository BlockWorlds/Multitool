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
	private static final String PERMISSIONS_PREFIX = "multitool.commands.";
	
	public MultiToolCommand(MultiTools pl) {
		super(pl);
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You have to be ingame to use this command.");
			return true;
		}
		Player p = (Player) sender;
		if (args.length == 2 && Utils.equalsIgnoreCase(args[0], "get", "give") && p.hasPermission(PERMISSIONS_PREFIX + "get." + args[1].replace(" ", "").toLowerCase())) {
			Tool tool = pl.getToolHandler().getTool(args[1]);
			if (tool != null && tool.getItemStack() != null) {
				p.getInventory().addItem(tool.getItemStack());
				return true;
			}
		}
		if (args.length != 1) {
			return false;
		}
		if (Utils.equalsIgnoreCase(args[0], "longrange", "range") && p.hasPermission(PERMISSIONS_PREFIX + "togglerange")) {
			ToolHandler.toggleRange(p);
			return true;
		}
		if (Utils.equalsIgnoreCase(args[0], "alledit", "all") && p.hasPermission(PERMISSIONS_PREFIX + "togglealledit")) {
			ToolHandler.toggleAllBlocks(p);
			return true;
		}
		if (args[0].equals("list") && p.hasPermission(PERMISSIONS_PREFIX + "list")) {
			printList(sender);
			return true;
		}
		return false;
	}
	
	private void printList(CommandSender sender){
		sender.sendMessage(ChatColor.AQUA + "Tools:");
		for(Tool t : pl.getToolHandler().getTools()){
			sender.sendMessage(ChatColor.GRAY + "- " + ChatColor.AQUA + t.getName().replaceAll(" ", ""));
		}
	}
}
