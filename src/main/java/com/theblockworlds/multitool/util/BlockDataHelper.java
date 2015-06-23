package com.theblockworlds.multitool.util;

import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.CommandBlock;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Furnace;
import org.bukkit.block.Jukebox;
import org.bukkit.block.NoteBlock;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.material.Directional;

public class BlockDataHelper {
	/** Transfers all the data from value to block
	 * 
	 * @param block				Block you want to apply data to
	 * @param value				Block you want to get data from
	 * @param physics			Whether physics should be applied to block
	 * @param clearInventory	Whether value's inventory should be cleared upon transfer
	 */
	public static void setTypeAndData(Block block, Block value, boolean physics, boolean clearInventory) {
		BlockState valueState = value.getState();
		setTypeAndData(block, valueState, physics, clearInventory);
	}
	
	/** Transfers all the data from value to block
	 * 
	 * @param block				Block you want to apply data to
	 * @param value				Block-state you want to get data from
	 * @param physics			Whether physics should be applied to block
	 * @param clearInventory	Whether value's inventory should be cleared upon transfer
	 */
	@SuppressWarnings("deprecation")
	public static void setTypeAndData(Block block, BlockState value, boolean physics, boolean clearInventory) {
		//Handles new way of storing directions
		if (value.getData() instanceof Directional) {
			Directional dirValue = (Directional) value.getData();
			block.setType(value.getType(), physics);
			BlockState blockState = block.getState();
			Directional dir = (Directional) blockState.getData();
			dir.setFacingDirection(dirValue.getFacing());
			blockState.update(true, physics);
		}
		//Fall back if a block still rely on byte Data
		else {
			block.setType(value.getType(), physics);
			block.setData(value.getRawData());
		}
		//Handles blocks with inventories
		if (value instanceof InventoryHolder) {
			InventoryHolder valueState = (InventoryHolder) value;
			InventoryHolder state = (InventoryHolder) block.getState();
			if (value instanceof Chest) {
				((Chest) state).getBlockInventory().setContents(((Chest) valueState).getBlockInventory().getContents());
				if (clearInventory) {
					((Chest) valueState).getBlockInventory().clear();
				}
			}
			else {
				state.getInventory().setContents(valueState.getInventory().getContents());
				if (clearInventory) {
					valueState.getInventory().clear();
				}
			}
			((BlockState) valueState).update(true, physics);
			((BlockState) state).update(true, physics);
		}
		//Handles special blocks
		if (value instanceof Furnace) {
			Furnace valueState = (Furnace) value;
			Furnace state = (Furnace) block.getState();
			state.setBurnTime(valueState.getBurnTime());
			state.setCookTime(valueState.getBurnTime());
			state.update(true, physics);
		}
		else if (value instanceof Sign) {
			Sign valueState = (Sign) value;
			Sign state = (Sign) block.getState();
			for (int i = 0; i < valueState.getLines().length; i++) {
				state.setLine(i,valueState.getLine(i));
			}
			state.update(true, physics);
		}
		else if (value instanceof Banner) {
			Banner valueState = (Banner) value;
			Banner state = (Banner) block.getState();
			state.setBaseColor(valueState.getBaseColor());
			state.setPatterns(valueState.getPatterns());
			state.update(true, physics);
		}
		else if (value instanceof CommandBlock) {
			CommandBlock valueState = (CommandBlock) value;
			CommandBlock state = (CommandBlock) block.getState();
			state.setCommand(valueState.getCommand());
			state.setName(valueState.getName());
			state.update(true, physics);
		}
		else if (value instanceof CreatureSpawner) {
			CreatureSpawner valueState = (CreatureSpawner) value;
			CreatureSpawner state = (CreatureSpawner) block.getState();
			state.setCreatureTypeByName(valueState.getCreatureTypeName());
			state.setDelay(valueState.getDelay());
			state.update(true, physics);
		}
		else if (value instanceof Jukebox) {
			Jukebox valueState = (Jukebox) value;
			Jukebox state = (Jukebox) block.getState();
			state.setPlaying(valueState.getPlaying());
			if (clearInventory) {
				valueState.setPlaying(null);
				valueState.update(true, physics);
			}
			state.update(true, physics);
		}
		else if (value instanceof NoteBlock) {
			NoteBlock valueState = (NoteBlock) value;
			NoteBlock state = (NoteBlock) block.getState();
			state.setNote(valueState.getNote());
			state.update(true, physics);
		}
		else if (value instanceof Skull) {
			Skull valueState = (Skull) value;
			Skull state = (Skull) block.getState();
			state.setOwner(valueState.getOwner());
			state.setSkullType(valueState.getSkullType());
			state.setRotation(valueState.getRotation());
			state.update(true, physics);
		}
	}
}

