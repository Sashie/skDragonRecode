package me.sashie.skdragon.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
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
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.skript.sections.EffectSection;
import me.sashie.skdragon.skript.sections.ParticleEffectSection;
import me.sashie.skdragon.skript.sections.ParticleSection;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class CustomParticlePropertyExpression<T> extends CustomPropertyExpression<String, T> implements Converter<String, T> {

	public static <T> void register(final Class<? extends Expression<T>> c, final Class<T> type, final String property) {
		Skript.registerExpression(c, type, ExpressionType.PROPERTY,
				"[the] [%-number%(st|nd|rd|th)] particle " + property + " of [the] [particle] effect %string%",
				"[particle] effect %string%'[s] [%-number%(st|nd|rd|th)] particle " + property,
				"[%-number%(st|nd|rd|th)] particle " + property + " of [the] [particle] effect",
				property + " of [the] particle",
				"particle " + property);
	}

	protected boolean scope, isParticleEffectSection, isParticleSection;
	protected Expression<Number> particleNumberExpr;
	protected int particleNumber;
	private int matchedPattern;
	protected int mark;
	protected SkriptNode skriptNode;
	protected EffectData effect;

	@SuppressWarnings("unchecked")
	public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull ParseResult parseResult) {
		if (matchedPattern == 2) {
			if (EffectSection.isCurrentSection(ParticleEffectSection.class)) {
				this.scope = true;
				this.isParticleEffectSection = true;
				this.setParticleExpr((Expression<Number>) exprs[0]);
			} else {
				return false;
			}
		} else if (matchedPattern == 3 || matchedPattern == 4) {
			if (EffectSection.isCurrentSection(ParticleSection.class)) {
				this.scope = true;
				this.isParticleSection = true;
			} else {
				return false;
			}
		} else if (matchedPattern == 0) {
			this.setParticleExpr((Expression<Number>) exprs[0]);
			this.setExpr((Expression<? extends String>) exprs[1]);
		} else if (matchedPattern == 1) {
			this.setExpr((Expression<? extends String>) exprs[0]);
			this.setParticleExpr((Expression<Number>) exprs[1]);
		}
		this.mark = parseResult.mark;
		this.matchedPattern = matchedPattern;
		skriptNode = new SkriptNode(SkriptLogger.getNode());
		return true;
	}

	/*
		public int getMark() {
			return mark;
		}
	*/
	protected final void setParticleExpr(Expression<Number> expr) {
		this.particleNumberExpr = expr;
	}

	public final Expression<Number> getParticleExpr() {
		return this.particleNumberExpr;
	}

	protected final int getParticleNumber() {
		return this.particleNumber;
	}

	protected abstract String getPropertyName();

	/**
	 * Place code to get a particle property
	 */
	public abstract T getParticle(ParticleBuilder<?> p);

	/**
	 * Place code to set a particle property
	 *
	 * @param p	 The particle of which the property is changing
	 * @param delta Skript input value for property expressions
	 */
	public abstract void setParticle(ParticleBuilder<?> p, Object[] delta);

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
			effect = EffectAPI.get(id, skriptNode);

			if (particleNumber > effect.getParticleBuilders().length) {
				SkDragonRecode.warn("The " + /*'" + effect.getName() + "'*/"effect with id " + id + " does not support more than " + effect.getParticleBuilders().length + " particle" + (effect.getParticleBuilders().length > 1 ? "s" : ""), skriptNode);
				return null;
			}

			synchronized (effect) {
				return getParticle(effect.getParticleBuilders()[particleNumber - 1]);
			}
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		particleNumber = 1;
		if (particleNumberExpr != null && particleNumberExpr.getSingle(e) != null)
			particleNumber = particleNumberExpr.getSingle(e).intValue();

		if (scope) {
			if (isParticleEffectSection) {
				set(ParticleEffectSection.getID(), delta);
			} else if (isParticleSection) {
				if (matchedPattern == 3 || matchedPattern == 4) {
					setParticle(ParticleSection.getParticle(), delta);
				} else {
					SkDragonRecode.warn("A 'particle' section only allows one particle at a time not more, for that use a 'particle effect' section", skriptNode);
				}
			}
		} else {
			List<String> failedEffects = new ArrayList<String>();
			String[] effectIDs = getExpr().getArray(e);

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
				StringBuilder sb = new StringBuilder();
				for (String s : failedEffects) {
					sb.append(s);
					sb.append(", ");
				}
				SkDragonRecode.warn("One or more particle effects didn't exist! (" + sb.toString() + ")", skriptNode);
			}
		}
	}

	private void set(String id, Object[] delta) {
		effect = EffectAPI.get(id, skriptNode);

		if (particleNumberExpr != null && particleNumber > effect.getParticleBuilders().length) {
			SkDragonRecode.warn("The " + /*'" + effect.getName() + "'*/"effect with id " + id + " does not support more than " + effect.getParticleBuilders().length + " particle" + (effect.getParticleBuilders().length > 1 ? "s" : ""), skriptNode);
			return;
		}

		synchronized (effect) {
			setParticle(effect.getParticleBuilders()[particleNumber - 1], delta);
		}
	}

	@Override
	protected T[] get(Event e, String[] source) {
		particleNumber = 1;
		if (particleNumberExpr != null && particleNumberExpr.getSingle(e) != null)
			particleNumber = particleNumberExpr.getSingle(e).intValue();

		return super.get(source, this);
	}

	@Override
	public Class<? extends T>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET)
			return CollectionUtils.array(getReturnType());
		return null;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "the " + (particleNumberExpr == null ? "" : particleNumberExpr.toString(e, debug) + "(st|nd|rd|th) ") + "particle " + this.getPropertyName() + (this.getExpr() == null ? "" : " of effect " + (scope ? ParticleEffectSection.getID() : this.getExpr().toString(e, debug)));
	}
}