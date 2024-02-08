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

package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.properties.IVelocity;
import me.sashie.skdragon.effects.properties.VelocityProperty;
import me.sashie.skdragon.skript.expressions.CustomEffectPropertyExpression;
import org.bukkit.util.Vector;

/**
 * Created by Sashie on 12/12/2016.
 */

@Name("Particles - Effect rotation vector")
@Description({"Rotates specific effects using a vector, requires auto rotation expression for the effect be set to true"})
@Examples({	"set rotation vector of effect \"uniqueID\" to vector 20, 50 and 100"})
public class ExprEffectRotationalVelocity extends CustomEffectPropertyExpression<Vector> {

	static {
		register(ExprEffectRotationalVelocity.class, Vector.class, "rotation vector");
	}

	@Override
	public Vector getPropertyValue(EffectData effect) {
		if (effect instanceof IVelocity) {
			VelocityProperty property = ((IVelocity) effect).getVelocityProperty();
			double x =  property.getAngularVelocityX();
			double y =  property.getAngularVelocityX();
			double z =  property.getAngularVelocityX();
			return new Vector(x, y, z);
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		if (effect instanceof IVelocity) {
			Vector v = (Vector) (delta[0]);
			((IVelocity) effect).getVelocityProperty().setAngularVelocity(v.getX(), v.getY(), v.getZ());

		}
	}

	@Override
	public Class<? extends Vector> getReturnType() {
		return Vector.class;
	}

	@Override
	public String getPropertyName() {
		return "rotation vector";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.XYZ_ANGULAR_VELOCITY;
	}
}
