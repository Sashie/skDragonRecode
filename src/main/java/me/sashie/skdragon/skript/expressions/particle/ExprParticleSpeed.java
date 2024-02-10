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

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.particles.NormalParticle;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.skript.expressions.CustomParticlePropertyExpression;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Sashie on 12/12/2016.
 */
@Name("Particles - Particle speed")
@Description({"Some particle types have a speed option that changes how the particle moves along its preset path"})
@Examples({"set particle speed of effect \"uniqueID\" to .05"})
public class ExprParticleSpeed extends CustomParticlePropertyExpression<Number> {

	static {
		register(ExprParticleSpeed.class, Number.class, "speed");
	}

	@Override
	public Number getParticle(ParticleBuilder<?> p) {
		if (p instanceof NormalParticle) {
			return ((NormalParticle) p).getParticleData().speed;
		}

		return null;
	}

	@Override
	public void setParticle(ParticleBuilder<?> p, Object[] delta) {
		if (p instanceof NormalParticle) {
			Number n = (Number) (delta[0]);
			((NormalParticle) p).getParticleData().speed = n.floatValue();
		}
	}

	@Override
	public @NotNull Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	protected String getPropertyName() {
		return "speed";
	}
}
