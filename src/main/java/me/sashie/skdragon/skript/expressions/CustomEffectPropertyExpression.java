package me.sashie.skdragon.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.classes.Converter;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.sashie.skdragon.EffectAPI;
import me.sashie.skdragon.SkDragonRecode;
import me.sashie.skdragon.debug.SkriptNode;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.skript.sections.EffectSection;
import me.sashie.skdragon.skript.sections.ParticleEffectSection;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class CustomEffectPropertyExpression<T> extends CustomPropertyExpression<String, T> implements Converter<String, T> {

	public static <T> void register(final Class<? extends Expression<T>> c, final Class<T> type, final String property) {
		Skript.registerExpression(c, type, ExpressionType.PROPERTY,
				"[the] " + property + " of [the] [particle] effect %string%",
				"[particle] effect %string%'[s] " + property,
				property + " of [the] [particle] effect");
	}

	protected boolean scope;
	private ChangeMode mode;
	protected int mark;
	protected SkriptNode skriptNode;

	@SuppressWarnings("unchecked")
	public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull ParseResult parseResult) {
		if (matchedPattern == 2) {
			if (EffectSection.isCurrentSection(ParticleEffectSection.class)) {
				this.scope = true;
			} else {
				return false;
			}
		} else {
			this.setExpr((Expression<? extends String>) exprs[0]);
		}
		this.mark = parseResult.mark;
		skriptNode = new SkriptNode(SkriptLogger.getNode());
		return true;
	}

	public ChangeMode getMode() {
		return mode;
	}

	/**
	 * Property name
	 */
	public abstract String getPropertyName();

	/**
	 * To be overridden
	 *
	 * @return
	 */
	protected EffectProperty getEffectProperty() {
		return null;
	}

	public abstract T getPropertyValue(EffectData effect);

	public abstract void setPropertyValue(EffectData effect, Object[] delta);

	@Override
	@Nullable
	public T convert(String id) {
		if (scope) {
			SkDragonRecode.warn("Incorrect use of syntax, can't get values from scope", skriptNode);
			return null;
		}
		if (id == null)
			return null;
		if (EffectAPI.ALL_EFFECTS.containsKey(id)) {
			final EffectData effect = EffectAPI.get(id, skriptNode);
			if (getEffectProperty() != null) {
				for (EffectProperty property : effect.getEffectProperties()) {
					if (getEffectProperty() != property)
						continue;
					synchronized (effect) {
						return getPropertyValue(effect);
					}
				}
				// If check fails then no properties matched for this effect's properties
				SkDragonRecode.warn("Effect '" + id + "' does not have an editable property for " + getEffectProperty().getName(), skriptNode);
			} else {
				// If getEffectProperty is null that means the property should always work by default and no checks are needed
				// Some properties are used in all effects
				synchronized (effect) {
					return getPropertyValue(effect);
				}
			}
		}
		return null;
	}

	@Override
	public void change(@NotNull Event e, Object @NotNull [] delta, Changer.@NotNull ChangeMode mode) {
		this.mode = mode;
		if (scope) {
			set(ParticleEffectSection.getID(), delta);
		} else {
			List<String> failedEffects = new ArrayList<String>();
			String[] effectIDs = (String[]) getExpr().getArray(e);

			if (effectIDs == null)
				return;

			for (String id : effectIDs) {
				if (!EffectAPI.ALL_EFFECTS.containsKey(id)) {
					failedEffects.add(id);
					continue;
				}
				set(id, delta);
			}

			if (!failedEffects.isEmpty()) {
				//StringBuilder sb = new StringBuilder();
				//for (String s : failedEffects) {
				//	sb.append(s);
				//	sb.append(", ");
				//}
				SkDragonRecode.warn("One or more particle effects didn't exist! (" + failedEffects.toString().replaceAll("\\[|\\]", "") + ")", skriptNode);
			}
		}
	}

	private void set(String id, Object[] delta) {
		EffectData effect = EffectAPI.get(id, skriptNode);
		if (getEffectProperty() != null) {
			for (EffectProperty property : effect.getEffectProperties()) {
				if (getEffectProperty() != property)
					continue;
				synchronized (effect) {
					setPropertyValue(effect, delta);
					return;
				}
			}
			SkDragonRecode.warn("Effect '" + id + "' does not have an editable property for " + getEffectProperty().getName(), skriptNode);
		} else {
			//If getEffectProperty is null that means the property should always work by default
			synchronized (effect) {
				setPropertyValue(effect, delta);
			}
		}
	}

	@Override
	protected T[] get(Event e, String[] source) {
		return super.get(source, this);
	}

	@Override
	public Class<? extends T> @NotNull [] acceptChange(final Changer.@NotNull ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET)
			return CollectionUtils.array(getReturnType());
		return null;
	}

	@Override
	public @NotNull String toString(@Nullable Event e, boolean debug) {
		return "the " + (getEffectProperty() == null ? getPropertyName() : this.getEffectProperty().getName()) + " of effect " + (scope ? ParticleEffectSection.getID() : this.getExpr().toString(e, debug));
	}
}