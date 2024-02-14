package me.sashie.skdragon.api.util.versions;

import org.bukkit.event.Event;

import ch.njol.skript.classes.Converter;
import ch.njol.skript.classes.Converter.ConverterInfo;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.lang.util.ConvertedExpression;
import ch.njol.skript.log.HandlerList;
import ch.njol.skript.util.SkriptColor;
import ch.njol.util.Kleenean;

public class V2_6 implements SkriptAdapter {

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

	@SuppressWarnings("unchecked")
	@Override
	public ConvertedExpression getConvertedExpr(Expression expr, Class superType, Converter converter) {
		return new ConvertedExpression<>(expr, superType,
				new ConverterInfo<>(Object.class, superType, converter, 1));
	}

	@Override
	public Kleenean hasDelayBefore() {
		return ParserInstance.get().getHasDelayBefore();
	}

	@Override
	public void setHasDelayBefore(Kleenean hasDelayBefore) {
		ParserInstance.get().setHasDelayBefore(hasDelayBefore);
	}

	@Override
	public boolean isCurrentEvent(Class<? extends Event> event) {
		return ParserInstance.get().isCurrentEvent(event);
	}

	@Override
	public HandlerList getHandlers() {
		return ParserInstance.get().getHandlers();
	}
}
