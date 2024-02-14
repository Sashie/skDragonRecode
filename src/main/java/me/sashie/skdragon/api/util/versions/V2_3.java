package me.sashie.skdragon.api.util.versions;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.bukkit.event.Event;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Converter;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.util.ConvertedExpression;
import ch.njol.skript.log.HandlerList;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.skript.util.Color;
import ch.njol.util.Kleenean;

public class V2_3 implements SkriptAdapter {

	private Field byNameField, hasDelayBeforeField, handlersField;
	
	public V2_3() {
		try {
			if (Skript.fieldExists(getColorClass(), "BY_NAME")) 
				byNameField = getColorClass().getDeclaredField("BY_NAME");
			else 
				byNameField = getColorClass().getDeclaredField("byName");
			hasDelayBeforeField = ScriptLoader.class.getDeclaredField("hasDelayBefore");
			handlersField = SkriptLogger.class.getDeclaredField("handlers");
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		byNameField.setAccessible(true);
		hasDelayBeforeField.setAccessible(true);
		handlersField.setAccessible(true);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Class<T> getColorClass() {
		return (Class<T>) Color.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Color colorFromName(String name) {
		try {
			return (Color) ((HashMap<String, Object>) byNameField.get(null)).get(name);
		} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getColorName(Object color) {
		return ((Color) color).toString();
	}

	@Override
	public ConvertedExpression getConvertedExpr(Expression expr, Class superType, Converter converter) {
		try {
			Constructor<ConvertedExpression> conExCon = ConvertedExpression.class.getConstructor(Expression.class, superType.getClass(), Converter.class);
			return conExCon.newInstance(expr, superType, converter);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Kleenean hasDelayBefore() {
		try {
			return (Kleenean) hasDelayBeforeField.get(null);
		} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setHasDelayBefore(Kleenean hasDelayBefore) {
		try {
			hasDelayBeforeField.set(null, hasDelayBefore);
		} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isCurrentEvent(Class<? extends Event> event) {
		return ScriptLoader.isCurrentEvent(event);
	}

	@Override
	public HandlerList getHandlers() {
		try {
			return (HandlerList) handlersField.get(null);
		} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

}
