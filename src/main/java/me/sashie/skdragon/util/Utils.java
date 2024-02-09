package me.sashie.skdragon.util;

import ch.njol.skript.lang.Expression;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

	public static <T> @Nullable T verifyVar(@NotNull Event e, @Nullable Expression<T> expression) {
		return verifyVar(e, expression, null);
	}

	public static <T> T verifyVar(@NotNull Event e, @Nullable Expression<T> expression, T defaultValue) {
		return expression == null ? defaultValue : (expression.getSingle(e) == null ? defaultValue : expression.getSingle(e));
	}

	public static <T> T[] verifyVars(@NotNull Event e, @Nullable Expression<T> expression, T[] defaultValue) {
		if (expression == null) {
			return defaultValue;
		}

		if (expression.getArray(e) == null || expression.getArray(e).length == 0) {
			return defaultValue;
		} else {
			return expression.getArray(e);
		}
	}

}
