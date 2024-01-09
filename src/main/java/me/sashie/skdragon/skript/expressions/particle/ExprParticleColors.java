/*
	This file is part of skDragon - A Skript addon
      
	Copyright (C) 2016 - 2021  Sashie

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

import java.util.ArrayList;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.util.Color;
import ch.njol.skript.util.ColorRGB;
import me.sashie.skdragon.EffectAPI;
import me.sashie.skdragon.SkDragonRecode;
import me.sashie.skdragon.debug.ParticleException;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.skript.expressions.CustomParticlePropertyExpression;
import org.bukkit.event.Event;

/**
 * Created by Sashie on 12/12/2016.
 */
@Name("Particles - Color")
@Description({""})
@Examples({	"set the 1st particle color of the particle effect \"uniqueID\" to {_v}"})
public class ExprParticleColors extends CustomParticlePropertyExpression<Color> {

	static {
		register(ExprParticleColors.class, Color.class, "colo[u]r[s]");
	}
	
	@Override
	public Color getParticle(ParticleBuilder<?> p) {
		return null; // ignored
	}

	@Override
	protected Color[] get(Event e) {
		particleNumber = 1;
		if (particleNumberExpr != null)
			particleNumber = particleNumberExpr.getSingle(e).intValue();

		//String[] effectIDs = (String[]) getExpr().getAll(e);
		String id = getExpr().getSingle(e);

		if (scope) {
			SkDragonRecode.warn("Incorrect use of syntax, can't get values from scope", skriptNode);
			return null;
		}
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
	public void setParticle(ParticleBuilder<?> p, Object[] delta) {
		if (p instanceof ColoredParticle) {
			Color[] c = (Color[]) delta;
			((ColoredParticle) p).getParticleData().colors.clear();
			for (Color color : c) {
				((ColoredParticle) p).getParticleData().colors.add(color.asBukkitColor());
			}
		}
	}

	@Override
	public Class<? extends Color> getReturnType() {
		return Color.class;
	}

	@Override
	protected String getPropertyName() {
		return "color(s)";
	}
	
	@Override
	public boolean isSingle() {
		return false;
	}
}
