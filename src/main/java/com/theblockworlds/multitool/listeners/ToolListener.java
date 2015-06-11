package com.theblockworlds.multitool.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.theblockworlds.multitool.Multitool;

public class ToolListener implements Listener{
	
	private final Multitool plugin;
	
	public ToolListener(Multitool pl){
		this.plugin = pl;
	}
	
	@EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (event.hasItem())
        {
            if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
            {
                ItemStack hand = event.getPlayer().getItemInHand();
                if(hand.getAmount() <= 32 && hand.getAmount() >= 2) hand.setAmount(64);
            }
            if (event.getClickedBlock() == null)
            {
                this.plugin.getToolHandler().onRangedUse(event.getPlayer(), event.getItem(), event.getAction());
            }
            else
            {
                if (this.plugin.getToolHandler().onUse(event.getPlayer(), event.getItem(), event.getAction(), event.getClickedBlock(), event.getBlockFace()))
                {
                    event.setCancelled(true);
                }
            }
        }
    }
}
