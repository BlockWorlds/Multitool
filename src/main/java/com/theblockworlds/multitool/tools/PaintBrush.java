package com.theblockworlds.multitool.tools;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PaintBrush {
	
	public boolean onCommand (CommandSender sender, Command cmd, String label,  String [] args) {
	
		if (cmd.getName().equalsIgnoreCase("ignite")) {
			
			if (args.length != 1) {
				
				return false;
			}
			
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players can set other players on fire.");
				sender.sendMessage("This is an arbitary requirement for demonstration purposes only.");
				return true;
			}
			
			Player target = Bukkit.getServer().getPlayer(args[0]);
			
			if (target == null) {
				sender.sendMessage(args[0] + " is not currently online.");
				return true;
			}
			
			target.setFireTicks(1000);
			return true;
		}
		return false;	
	}
}	
	
