package me.sashie.skdragon.skript.expressions.particle;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.skript.util.Color;
import ch.njol.skript.util.ColorRGB;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.sashie.skdragon.SkDragonRecode;
import me.sashie.skdragon.api.EffectAPI;
import me.sashie.skdragon.api.debug.ParticleException;
import me.sashie.skdragon.api.debug.SkriptNode;
import me.sashie.skdragon.api.effects.EffectData;
import me.sashie.skdragon.api.particles.ColoredParticle;
import me.sashie.skdragon.api.particles.ParticleBuilder;
import me.sashie.skdragon.api.util.Utils;
import me.sashie.skdragon.skript.sections.EffectSection;
import me.sashie.skdragon.skript.sections.ParticleEffectSection;
import me.sashie.skdragon.skript.sections.ParticleSection;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


@Name("Particles - Colors")
@Description({"Gets and sets a single color or list of colors from one of the particles of a particle effect"})
@Examples({"set the 1st particle color of the particle effect \"uniqueID\" to custom color using rgb 255, 255, 0",
		"set the 2nd particle color of the particle effect \"uniqueID\" to custom color using rgb 255, 255, 0 and custom color using rgb 255, 0, 255 and custom color using rgb 255, 0, 0",
		"set the 3rd particle color of the particle effect \"uniqueID\" to gradient between custom color using rgb 255, 0, 0 and custom color using rgb 255, 255, 0 with 100 steps"})
public class ExprParticleColors extends SimpleExpression<Color> {

	static {
		Skript.registerExpression(ExprParticleColors.class, Color.class, ExpressionType.PROPERTY,
				"[the] [%-number%(st|nd|rd|th)] particle colo[u]r of [the] [particle] effect %string%",
				"[particle] effect %string%'[s] [%-number%(st|nd|rd|th)] particle colo[u]r",

				"[%-number%(st|nd|rd|th)] particle colo[u]r of [the] [particle] effect",

				"colo[u]r of [the] particle",
				"particle colo[u]r");
	}

	protected boolean scope, isParticleEffectSection, isParticleSection;
	private Expression<String> exprName;
	protected Expression<Number> exprParticleNumber;
	protected int particleNumber;
	private int matchedPattern;
	private SkriptNode skriptNode;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
		if (matchedPattern == 2) {
			if (EffectSection.isCurrentSection(ParticleEffectSection.class)) {
				this.scope = true;
				this.isParticleEffectSection = true;
				this.exprParticleNumber = (Expression<Number>) exprs[0];
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
			this.exprParticleNumber = (Expression<Number>) exprs[0];
			this.exprName = (Expression<String>) exprs[1];
		} else if (matchedPattern == 1) {
			this.exprName = (Expression<String>) exprs[0];
			this.exprParticleNumber = (Expression<Number>) exprs[1];
		}
		this.matchedPattern = matchedPattern;
		skriptNode = new SkriptNode(SkriptLogger.getNode());
		return true;
	}

	@Override
	protected Color @NotNull [] get(@NotNull Event e) {
		particleNumber = Utils.verifyVar(e, exprParticleNumber, 1).intValue();

		if (scope) {
			SkDragonRecode.warn("Incorrect use of syntax, can't get values from scope", skriptNode);
			return new Color[0];
		}

		String id = Utils.verifyVar(e, exprName, null);
		if (id == null) return new Color[0];

		EffectData effect = EffectAPI.get(id, skriptNode);
		if (effect == null) return new Color[0];

		if (particleNumber > effect.getParticleBuilders().length)
			throw new ParticleException("The effect with id " + id + " does not support more than " + effect.getParticleBuilders().length + " particle" + (effect.getParticleBuilders().length > 1 ? "s" : ""), skriptNode);

		synchronized (effect) {
			ParticleBuilder<?> p = effect.getParticleBuilders()[particleNumber - 1];

			if (p instanceof ColoredParticle) {
				ArrayList<Color> cl = new ArrayList<>();
				for (org.bukkit.Color c : ((ColoredParticle) p).getParticleData().colors) {
					cl.add(new ColorRGB(c.getRed(), c.getGreen(), c.getBlue()));
				}
				return cl.toArray(new Color[0]);
			}
		}

		return new Color[0];
	}

	@Override
	public void change(@NotNull Event e, Object @NotNull [] delta, Changer.@NotNull ChangeMode mode) {
		particleNumber = Utils.verifyVar(e, exprParticleNumber, 1).intValue();

		if (scope) {
			if (isParticleEffectSection) {
				set(ParticleEffectSection.getID(), delta);
			} else if (isParticleSection) {
				if (matchedPattern == 3 || matchedPattern == 4) {
					ParticleBuilder<?> p = ParticleSection.getParticle();
					if (p instanceof ColoredParticle) {
						((ColoredParticle) p).getParticleData().colors.clear();
						for (Object colorObj : delta) {
							Color color = (Color) colorObj;
							((ColoredParticle) p).getParticleData().colors.add(color.asBukkitColor());
						}
					}
				} else {
					SkDragonRecode.warn("A 'particle' section only allows one particle at a time not more, for that use a 'particle effect' section", skriptNode);
				}
			}
		} else {
			List<String> failedEffects = new ArrayList<>();

			String[] effectIds = Utils.verifyVars(e, exprName, null);
			if (effectIds == null)
				return;

			for (String id : effectIds) {
				if (!EffectAPI.ALL_EFFECTS.containsKey(id)) {
					failedEffects.add(id);
					continue;
				}
				set(id, delta);
			}

			if (!failedEffects.isEmpty()) {
				SkDragonRecode.warn("One or more particle effects didn't exist! (" + String.join(", ", failedEffects) + ")", skriptNode);
			}
		}
	}

	private void set(String id, Object[] delta) {
		EffectData effect = EffectAPI.get(id, skriptNode);
		if (effect == null) return;

		if (exprParticleNumber != null && particleNumber > effect.getParticleBuilders().length)
			throw new ParticleException("The effect with id " + id + " does not support more than " + effect.getParticleBuilders().length + " particle" + (effect.getParticleBuilders().length > 1 ? "s" : ""), skriptNode);

		synchronized (effect) {
			ParticleBuilder<?> p = effect.getParticleBuilders()[particleNumber - 1];
			if (p instanceof ColoredParticle) {
				((ColoredParticle) p).getParticleData().colors.clear();
				for (Object colorObj : delta) {
					Color color = (Color) colorObj;
					((ColoredParticle) p).getParticleData().colors.add(color.asBukkitColor());
				}
			}
		}
	}

	@Override
	public @NotNull Class<? extends Color> getReturnType() {
		return Color.class;
	}

	@Override
	public Class<?> @NotNull [] acceptChange(final Changer.@NotNull ChangeMode mode) {
		return CollectionUtils.array(Color[].class);
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public @NotNull String toString(@Nullable Event e, boolean debug) {
		return "the " + (exprParticleNumber == null ? "" : exprParticleNumber.toString(e, debug) + "(st|nd|rd|th) ") + "particle color(s)" + (this.exprName == null ? "" : " of effect " + this.exprName.toString(e, debug));
	}
}