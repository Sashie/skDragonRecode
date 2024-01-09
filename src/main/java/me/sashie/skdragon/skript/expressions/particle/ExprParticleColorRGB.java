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

import java.awt.Color;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.skript.expressions.CustomParticlePropertyExpression;

/**
 * Created by Sashie on 12/12/2016.
 */
@Name("Particles - Color RGB values")
@Description({"Gets the current color of a particle, setting a color with this expression will make it a solid color"})
@Examples({	"set the 1st particle red value of the particle effect \"uniqueID\" to 255"})
public class ExprParticleColorRGB extends CustomParticlePropertyExpression<Number> {

	static {
		register(ExprParticleColorRGB.class, Number.class, "(1¦r[ed]|2¦g[reen]|3¦b[lue]) value");
	}
	
	@Override
	public Number getParticle(ParticleBuilder<?> p) {
		if (p instanceof ColoredParticle) {
			switch (mark) {
			case 1:
				return ((ColoredParticle) p).getParticleData().colors.get().getRed();
			case 2:
				return ((ColoredParticle) p).getParticleData().colors.get().getGreen();
			case 3:
				return ((ColoredParticle) p).getParticleData().colors.get().getBlue();
			}
		}

		return null;
	}

	@Override
	public void setParticle(ParticleBuilder<?> p, Object[] delta) {
		if (p instanceof ColoredParticle) {
			int value = ((Number) (delta[0])).intValue();
			if (value < 0)
				value = 0;
			else if (value > 255)
				value = 255;
			
			switch (mark) {
				case 1:
					((ColoredParticle) p).getParticleData().setColor(org.bukkit.Color.fromRGB(value, ((ColoredParticle) p).getParticleData().colors.get().getGreen(), ((ColoredParticle) p).getParticleData().colors.get().getBlue()));
					break;
				case 2:
					((ColoredParticle) p).getParticleData().setColor(org.bukkit.Color.fromRGB(((ColoredParticle) p).getParticleData().colors.get().getRed(), value, ((ColoredParticle) p).getParticleData().colors.get().getBlue()));
					break;
				case 3:
					((ColoredParticle) p).getParticleData().setColor(org.bukkit.Color.fromRGB(((ColoredParticle) p).getParticleData().colors.get().getRed(), ((ColoredParticle) p).getParticleData().colors.get().getGreen(), value));
					break;
			}
		}
	}

	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	protected String getPropertyName() {
		switch (mark) {
			case 1:
				return "red value";
			case 2:
				return "green value";
			case 3:
				return "blue value";
		}
		return "";
	}
}
