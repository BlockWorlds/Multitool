package com.theblockworlds.multitool.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.theblockworlds.multitool.base.CommandHandler;
import com.theblockworlds.multitool.Multitool;

public class ToolsCommand extends CommandHandler implements TabCompleter {
		List<String> arguments = new ArrayList<String>();
		public ToolsCommand(Multitool pl) {
			super(pl);
			arguments.add("list");
			arguments.add("give");
	}
		public boolean onCommand(CommandSender sender, Command command,
				String label, String[] args) {
			if(args.length < 1){
				return false;
			}
			if(!arguments.contains(args[0])){
				return false;
			}
			if(args[0].equalsIgnoreCase("list")){
				printList(sender);
				return true;
			}
			return false;
		}
		private void printList(CommandSender sender) {
			// TODO Auto-generated method stub
			
		}
		public List<String> onTabComplete(CommandSender arg0, Command arg1,
				String arg2, String[] arg3) {
			// TODO Auto-generated method stub
			return null;
		}

}
