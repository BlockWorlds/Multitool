package com.thedreamsanctuary.multitools.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.thedreamsanctuary.multitools.MultiTools;

public class ToolListener implements Listener{
	
	private final MultiTools plugin;
	
	public ToolListener(MultiTools pl) {
		this.plugin = pl;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.hasItem()) {
			if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				ItemStack hand = event.getPlayer().getItemInHand();
				if(hand.getAmount() <= 32 && hand.getAmount() >= 2) {
					hand.setAmount(64);
				}
			}
			if (this.plugin.getToolHandler().onUse(event.getPlayer(), event.getItem(), event.getAction(), event.getClickedBlock(), event.getBlockFace())) {
				event.setCancelled(true);
			}
		}
	}
}