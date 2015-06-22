package com.theblockworlds.multitool.util;

import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;

public class Utils {
	
	/** Compares one string to an unknown number of strings
	 * 
	 * @param argument		String you want to compare other strings to
	 * @param otherStrings	Unknown number of strings
	 * @return				True if argument equals any of the other strings
	 */
	public static boolean equalsIgnoreCase(String argument, String... otherStrings) {
		for (String string : otherStrings) {
			if (argument.equalsIgnoreCase(string)) {
				return true;
			}
		}
		return false;
	}
	
	/** Gives data value of a block
	 * 
	 * @param block		Block you want to apply data to
	 * @param value		Block you want to get data from
	 */
	@SuppressWarnings("deprecation")
	public static void setTypeAndData(Block block, Block value, boolean physics) {
		block.setType(value.getType(), physics);
		block.setData(value.getData(), physics);
		if (value.getState() instanceof Sign) {
			Sign valueSign = (Sign) value.getState();
			Sign sign = (Sign) block.getState();
			for (int i = 0; i < valueSign.getLines().length; i++) {
				sign.setLine(i,valueSign.getLine(i));
			}
			sign.update(true, false);
		}
		else if (value.getState() instanceof Banner) {
			Banner valueBanner = (Banner) value.getState();
			Banner banner = (Banner) block.getState();
			banner.setBaseColor(valueBanner.getBaseColor());
			banner.setPatterns(valueBanner.getPatterns());
			banner.update(true, false);
		}
		else if (value.getState() instanceof Banner) {
			Banner valueBanner = (Banner) value.getState();
			Banner banner = (Banner) block.getState();
			banner.setBaseColor(valueBanner.getBaseColor());
			banner.setPatterns(valueBanner.getPatterns());
			banner.update(true, false);
		}
		else if (value.getState() instanceof Chest) {
			org.bukkit.material.Chest valueChestMaterial = (org.bukkit.material.Chest) value.getState().getData();
			org.bukkit.material.Chest chestMaterial = (org.bukkit.material.Chest) block.getState().getData();
			chestMaterial.setFacingDirection(valueChestMaterial.getFacing());
			block.getState().update(true, false);
		}
	}
}
