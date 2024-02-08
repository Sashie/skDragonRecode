/*
	This file is part of skDragon - A Skript addon

	Copyright (C) 2016 - 2024  Sashie

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

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
import me.sashie.skdragon.EffectAPI;
import me.sashie.skdragon.SkDragonRecode;
import me.sashie.skdragon.debug.ParticleException;
import me.sashie.skdragon.debug.SkriptNode;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.skript.sections.EffectSection;
import me.sashie.skdragon.skript.sections.ParticleEffectSection;
import me.sashie.skdragon.skript.sections.ParticleSection;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sashie on 1/13/2024
 */

@Name("Particles - Colors")
@Description({"Gets and sets a single color or list of colors from one of the particles of a particle effect" })
@Examples({ "set the 1st particle color of the particle effect \"uniqueID\" to custom color using rgb 255, 255, 0",
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
	private Expression<String> name;
	protected Expression<Number> particleNumberExpr;
	protected int particleNumber;
	private int matchedPattern;
	private SkriptNode skriptNode;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		if (matchedPattern == 2) {
			if (EffectSection.isCurrentSection(ParticleEffectSection.class)) {
				this.scope = true;
				this.isParticleEffectSection = true;
				this.particleNumberExpr = (Expression<Number>) exprs[0];
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
			this.particleNumberExpr = (Expression<Number>) exprs[0];
			this.name = (Expression<String>) exprs[1];
		} else if (matchedPattern == 1) {
			this.name = (Expression<String>) exprs[0];
			this.particleNumberExpr = (Expression<Number>) exprs[1];
		}
		this.matchedPattern = matchedPattern;
		skriptNode = new SkriptNode(SkriptLogger.getNode());
		return true;
	}

	@Override
	protected Color[] get(Event e) {
		particleNumber = 1;
		if (particleNumberExpr != null)
			particleNumber = particleNumberExpr.getSingle(e).intValue();

		if (scope) {
			SkDragonRecode.warn("Incorrect use of syntax, can't get values from scope", skriptNode);
			return null;
		}

		if (name == null)
			return null;

		String id = name.getSingle(e);

		if (id == null)
			return null;
		if (EffectAPI.ALL_EFFECTS.containsKey(id)) {
			EffectData effect = EffectAPI.get(id, skriptNode);

			if (particleNumber > effect.getParticleBuilders().length)
				throw new ParticleException("The " + /*'" + effect.getName() + "'*/"effect with id " + id + " does not support more than " + effect.getParticleBuilders().length + " particle" + (effect.getParticleBuilders().length > 1 ? "s" : ""), skriptNode);

			synchronized(effect) {
				ParticleBuilder<?> p = effect.getParticleBuilders()[particleNumber - 1];

				if (p instanceof ColoredParticle) {
					ArrayList<Color> cl = new ArrayList<>();
					for (org.bukkit.Color c : ((ColoredParticle) p).getParticleData().colors) {
						cl.add(new ColorRGB(c.getRed(), c.getGreen(),c.getBlue() ));
					}
					return cl.toArray(new Color[cl.size()]);
				}
			}
		}

		return null;
	}

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
					ParticleBuilder p = ParticleSection.getParticle();
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
			List<String> failedEffects = new ArrayList<String>();
			String[] effectIDs = name.getArray(e);

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
		EffectData effect = EffectAPI.get(id, skriptNode);

		if (particleNumberExpr != null && particleNumber > effect.getParticleBuilders().length)
			throw new ParticleException("The " + /*'" + effect.getName() + "'*/"effect with id " + id + " does not support more than " + effect.getParticleBuilders().length + " particle" + (effect.getParticleBuilders().length > 1 ? "s" : ""), skriptNode);

		synchronized(effect) {
			ParticleBuilder p = effect.getParticleBuilders()[particleNumber - 1];
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
	public Class<? extends Color> getReturnType() {
		return Color.class;
	}

	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		return CollectionUtils.array(Color[].class);
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "the " + (particleNumberExpr == null ? "" : particleNumberExpr.toString(e, debug) + "(st|nd|rd|th) ") + "particle color(s)" + (this.name == null ? "" : " of effect " + this.name.toString(e, debug));
	}
}