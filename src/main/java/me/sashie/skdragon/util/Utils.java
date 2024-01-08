package me.sashie.skdragon.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Utils {

	public static String color(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	public static <E extends Enum<E>> boolean isValidEnum(Class<E> enumClass, String enumName) {
		if (enumName != null) {
			try {
				Enum.valueOf(enumClass, enumName);
				return true;
			} catch (IllegalArgumentException ignore) {
				return false;
			}
		}
		return false;
	}

	public static <E extends Enum<E>> E getEnumValue(Class<E> enumClass, String enumName) {
		if (isValidEnum(enumClass, enumName)) {
			return Enum.valueOf(enumClass, enumName);
		}

		return null;
	}
    
}
