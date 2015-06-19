package com.theblockworlds.multitool.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.theblockworlds.multitool.Multitool;
import com.theblockworlds.multitool.base.CommandHandler;
import com.theblockworlds.multitool.base.Tool;
import com.theblockworlds.multitool.handlers.ToolHandler;
import com.theblockworlds.multitool.util.Utils;

public class MultiToolCommand extends CommandHandler {
	public MultiToolCommand(Multitool pl) {
		super(pl);
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You have to be ingame to use this command.");
			return true;
		}
		Player p = (Player) sender;
		if (Utils.equalsIgnoreCase(args[0], "get", "give") && args.length == 2 && p.hasPermission("multitool.get."+args[1])) {
			Tool tool = pl.getToolHandler().getTool(args[1]);
			if (tool != null && tool.getItemStack() != null) {
				p.getInventory().addItem(tool.getItemStack());
				return true;
			}
		}
		if (args.length != 1) {
			return false;
		}
		if (Utils.equalsIgnoreCase(args[0], "longrange", "range") && p.hasPermission("multitool.togglerange")) {
			ToolHandler.toggleRange(p);
			return true;
		}
		if (args[0].equals("list") && p.hasPermission("multitool.list")) {
			printList(sender);
			return true;
		}
		return false;
	}
	
	public void printList(CommandSender sender){
		sender.sendMessage(ChatColor.AQUA + "Tools:");
		for(Tool t : pl.getToolHandler().getTools()){
			sender.sendMessage(ChatColor.GRAY + "- " + ChatColor.AQUA + t.getName());
		}
	}
}
