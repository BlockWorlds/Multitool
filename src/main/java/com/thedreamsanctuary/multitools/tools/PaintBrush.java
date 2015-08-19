package com.thedreamsanctuary.multitools.tools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.thedreamsanctuary.multitools.MultiTools;
import com.thedreamsanctuary.multitools.base.Tool;
import com.thedreamsanctuary.multitools.events.MultiToolPaintEvent;
import com.thedreamsanctuary.multitools.util.BlockDataHelper;

public class PaintBrush extends Tool{
	private static HashMap<UUID, BlockState> paintStates = new HashMap<UUID, BlockState>();
	private static HashMap<UUID, BlockState> tossStates = new HashMap<UUID, BlockState>();

	public PaintBrush(MultiTools pl) {
		super(pl);
	}

	@Override
	protected void setParameters() {
		setName("Paint Brush");
		setMaterial(cfgLoadMaterial(Material.SLIME_BALL));
		setLore("Left click to copy block", "Right click to paint block", "Paint Material: None");
	}

	@Override
	public boolean onUse(Block targetBlock, BlockFace face, ItemStack itemUsed, Player player, Action action) {
		boolean applyingPaint = (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK);
		MultiToolPaintEvent paintEvent = new MultiToolPaintEvent(targetBlock, player, applyingPaint);
		Bukkit.getPluginManager().callEvent(paintEvent);
		if (!paintEvent.isCancelled()) {
			if (applyingPaint) {
				BlockState paintState = getState(itemUsed);
				if (paintState != null) {
					BlockDataHelper.setTypeAndData(targetBlock, paintState, false, false);
				}
				else {
					ItemMeta meta = itemUsed.getItemMeta();
					meta.setLore(Arrays.asList("Left click to copy block", "Right click to paint block", "Paint Material: None"));
					itemUsed.setItemMeta(meta);
				}
			}
			else {
				linkStateToItem(targetBlock.getState(), itemUsed);
			}
		}
		return true;
	}
	
	public void updatePaintBrushes() {
		removePaintBrushes(true);
		removePaintBrushes(false);
	}
	
	private void linkStateToItem(BlockState state, ItemStack item) {
		if (state != null) {
			ItemMeta meta = item.getItemMeta();
			UUID id = getId(meta);
			if (id == null) {
				id = UUID.randomUUID();
			}
			meta.setLore(Arrays.asList("Left click to copy block", "Right click to paint block", "Paint Material: " + state.getType(), id.toString()));
			item.setItemMeta(meta);
			paintStates.put(id, state);
		}
	}
	
	private BlockState getState(ItemStack item) {
		UUID id = getId(item.getItemMeta());
		if (id != null) {
			return paintStates.get(id);
		}
		return null;
	}
	
	private UUID getId(ItemMeta meta) {
		if (meta != null && meta.getLore() != null && meta.getLore().size() >= 4) {
			String id = meta.getLore().get(3);
			if (paintStates.containsKey(UUID.fromString(id))) {
				return StringToUUID(id);
			}
		}
		return null;
	}
	
	private static UUID StringToUUID(String id) {
		try {
			return UUID.fromString(id);
		}
		catch(IllegalArgumentException e) {
			return null;
		}
	}
	
	private void removePaintBrushes(boolean finalRemove) {
		Iterator<UUID> iterator = finalRemove ? tossStates.keySet().iterator() : paintStates.keySet().iterator();
		while (iterator.hasNext()) {
			boolean found = false;
			UUID key = iterator.next();
			for (Player player : Bukkit.getOnlinePlayers()) {
				for (ItemStack item : player.getInventory().getContents()) {
					if (item != null && key.equals(getId(item.getItemMeta()))) {
						found = true;
					}
				}
			}
			if (finalRemove) {
				if (!found) {
					paintStates.remove(key);
				}
				iterator.remove();
			}
			else {
				if (!found) {
					tossStates.put(key, paintStates.get(key));
				}
			}
		 }
	}
}

	
	
