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

package me.sashie.skdragon.skript.expressions;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.particles.ParticleBuilder;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Converter;
import ch.njol.skript.expressions.base.PropertyExpression;
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
import me.sashie.skdragon.debug.ParticleException;
import me.sashie.skdragon.skript.sections.EffectSection;
import me.sashie.skdragon.skript.sections.ParticleEffectSection;

/**
 * Created by Sashie on 10/30/2017.
 */

@Name("Particles - Particle type")
@Description({"Gets or sets the particles in an effect, some effects have more than one particle"})
@Examples({	"set 3rd particle of effect \"uniqueID\" to flame"})
public class ExprEffectParticle extends PropertyExpression<String, ParticleBuilder> implements Converter<String, ParticleBuilder<?>> {

	static {
		Skript.registerExpression(ExprEffectParticle.class, ParticleBuilder.class, ExpressionType.PROPERTY,
				"[the] [%-number%(st|nd|rd|th)] particle of [the] [particle] effect %string%",
				"[particle] effect %string%'[s] [%-number%(st|nd|rd|th)] particle",
				"[%-number%(st|nd|rd|th)] particle of [the] [particle] effect");
	}

	protected boolean scope = false;
	private List<String> failedEffects;
	private Expression<Number> particleNumberExpr;
	private int particleNumber;
	private SkriptNode skriptNode;

	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		if (matchedPattern == 2) {
			if (EffectSection.isCurrentSection(ParticleEffectSection.class)) {
				this.scope = true;
				this.particleNumberExpr = (Expression<Number>) exprs[0];
			} else {
				return false;
			}
		} else if (matchedPattern == 1) {
			this.setExpr((Expression<? extends String>) exprs[0]);
			this.particleNumberExpr = (Expression<Number>) exprs[1];
		} else if (matchedPattern == 0) {
			this.particleNumberExpr = (Expression<Number>) exprs[0];
			this.setExpr((Expression<? extends String>) exprs[1]);
		}
		skriptNode = new SkriptNode(SkriptLogger.getNode());

		return true;
	}
	
	@Override
	@Nullable
	public ParticleBuilder<?> convert(String id) {
		if (id == null)
			return null;
		if (EffectAPI.ALL_EFFECTS.containsKey(id)) {
			EffectData effect = EffectAPI.get(id, skriptNode);
			
			if (particleNumber > effect.getParticleBuilders().length)
				throw new ParticleException("The " + /*'" + effect.getName() + "' */"effect with id " + id + " does not support more than " + effect.getParticleBuilders().length + " particle" + (effect.getParticleBuilders().length > 1 ? "s" : ""), skriptNode);

			synchronized(effect) {
				return effect.getParticleBuilders()[particleNumber - 1];
			}
		}
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		failedEffects = new ArrayList<String>();
		particleNumber = 1;
		if (particleNumberExpr != null && particleNumberExpr.getSingle(e) != null)
			particleNumber = particleNumberExpr.getSingle(e).intValue();
				
		if (scope) {
			set(ParticleEffectSection.getID(), (ParticleBuilder<?>) delta[0]);
		} else {
			String[] effectIDs = (String[]) getExpr().getArray(e);

			if (effectIDs == null)
				return;

			for (String id : effectIDs) {
				if (!EffectAPI.ALL_EFFECTS.containsKey(id)) {
					failedEffects.add(id);
					continue;
				}
				set(id, (ParticleBuilder<?>) delta[0]);
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

	private void set(String id, ParticleBuilder<?> p) {
		EffectData effect = EffectAPI.get(id, skriptNode);

		if (particleNumber > effect.getParticleBuilders().length)
			throw new ParticleException("The " +/*'" + effect.getName() + "' */"effect with id " + id + " does not support more than " + effect.getParticleBuilders().length + " particle" + (effect.getParticleBuilders().length > 1 ? "s" : ""), skriptNode);

		synchronized(effect) {
			effect.getParticleBuilders()[particleNumber - 1] = p;
		}
	}

	@Override
	protected ParticleBuilder<?>[] get(Event e, String[] source) {
		particleNumber = 1;
		if (particleNumberExpr != null && particleNumberExpr.getSingle(e) != null)
			particleNumber = particleNumberExpr.getSingle(e).intValue();
		if (scope) {
			throw new ParticleException("Incorrect use of syntax, can't get values inside a scope", skriptNode);
		}
		return super.get(source, this);
	}

	public String toString(@Nullable Event e, boolean debug) {
		return "the particle of effect " + this.getExpr().toString(e, debug);
	}

	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET)
			return CollectionUtils.array(ParticleBuilder.class);
		return null;
	}

	@Override
	public Class<? extends ParticleBuilder> getReturnType() {
		return ParticleBuilder.class;
	}
}