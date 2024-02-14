package me.sashie.skdragon.api.util.versions;

import java.lang.reflect.Field;

import org.bukkit.event.Event;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.classes.Converter;
import ch.njol.skript.classes.Converter.ConverterInfo;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.util.ConvertedExpression;
import ch.njol.skript.log.HandlerList;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.skript.util.SkriptColor;
import ch.njol.util.Kleenean;

public class V2_4 implements SkriptAdapter {

	private Field hasDelayBeforeField, handlersField;

	public V2_4() {
		try {
			hasDelayBeforeField = ScriptLoader.class.getDeclaredField("hasDelayBefore");
			handlersField = SkriptLogger.class.getDeclaredField("handlers");
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		hasDelayBeforeField.setAccessible(true);
		handlersField.setAccessible(true);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Class<T> getColorClass() {
		return (Class<T>) SkriptColor.class;
	}

	@Override
	public SkriptColor colorFromName(String name) {
		return SkriptColor.fromName(name);
	}

	@Override
	public String getColorName(Object color) {
		return ((SkriptColor) color).getName();
	}

	@Override
	public ConvertedExpression getConvertedExpr(Expression expr, Class superType, Converter converter) {
		return new ConvertedExpression<>(expr, superType,
				new ConverterInfo<>(Object.class, superType, converter, 1));
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
