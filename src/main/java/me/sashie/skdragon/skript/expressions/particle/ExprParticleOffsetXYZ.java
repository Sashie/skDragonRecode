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

/**
 * Created by Sashie on 12/12/2016.
 */
@Name("Particles - Offset XYZ values")
@Description({"Similar to the other particle offset expression this one lets you change the individual x, y or z values separately"})
@Examples({	"set the 1st particle x offset value of the particle effect \"uniqueID\" to 1.7"})
public class ExprParticleOffsetXYZ extends CustomParticlePropertyExpression<Number> {

	static {
		//Skript.registerExpression(ExprEffectParticleColorRGB.class, Number.class, ExpressionType.PROPERTY, "[the] [%-number%(st|nd|rd|th)] particle (1Â¦r[ed]|2Â¦g[reen]|3Â¦b[lue]) value of [the] [particle] effect %string%", "[particle] effect %string%'[s] [%-number%(st|nd|rd|th)] particle (1Â¦rgb|2Â¦r[ed]|3Â¦g[reen]|4Â¦b[lue]) value");
		register(ExprParticleOffsetXYZ.class, Number.class, "(1¦x|2¦y|3¦z) offset value");
	}
	
	@Override
	public Number getParticle(ParticleBuilder<?> p) {
		switch (mark) {
			case 1:
				return p.getParticleData().getOffset().getX();
			case 2:
				return p.getParticleData().getOffset().getY();
			case 3:
				return p.getParticleData().getOffset().getZ();
		}
		
		return null;
	}

	@Override
	public void setParticle(ParticleBuilder<?> p, Object[] delta) {
		float f = ((Number) (delta[0])).floatValue();
				
		switch (mark) {
			case 1:
				p.getParticleData().getOffset().setX(f > 0 ? f : 0);
				break;
			case 2:
				p.getParticleData().getOffset().setY(f > 0 ? f : 0);
				break;
			case 3:
				p.getParticleData().getOffset().setZ(f > 0 ? f : 0);
				break;
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
				return "x offset value";
			case 2:
				return "y offset value";
			case 3:
				return "z offset value";
		}
		return "";
	}
}
