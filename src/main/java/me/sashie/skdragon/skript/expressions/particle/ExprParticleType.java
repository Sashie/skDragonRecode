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
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.skript.expressions.CustomParticlePropertyExpression;
import me.sashie.skdragon.skript.sections.ParticleSection;
import me.sashie.skdragon.util.ParticleUtils;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Sashie on 12/12/2016.
 */
@Name("Particles - Particle type")
@Description({"Gets or sets the particle type used. Some data may be lost, for example if you change the particle type from Colorable to Normal it will lose its color data"})
@Examples({"set particle type of effect \"uniqueID\" to dust",
		"set the 1st particle type of the particle effect \"uniqueID\" to dust"})
public class ExprParticleType extends CustomParticlePropertyExpression<Particle> {

	static {
		register(ExprParticleType.class, Particle.class, "type");
	}

	@Override
	public Particle getParticle(ParticleBuilder<?> p) {
		return p.getParticleData().getParticle();
	}

	@Override
	public void setParticle(ParticleBuilder<?> particle, Object[] delta) {
		Particle type = (Particle) (delta[0]);

		ParticleBuilder<?> p = ParticleUtils.createParticle(type);
		p.initParticle(particle.getParticleData());
		p.getParticleData().setParticle(type);
		if (this.isParticleSection) {
			ParticleSection.particle = p;
		} else {
			this.effect.setParticle(this.particleNumber, p, this.skriptNode);
		}
	}

	@Override
	public @NotNull Class<? extends Particle> getReturnType() {
		return Particle.class;
	}

	@Override
	protected String getPropertyName() {
		return "type";
	}
}
