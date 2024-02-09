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

package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.properties.IAxis;
import me.sashie.skdragon.skript.expressions.CustomEffectPropertyExpression;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Sashie on 12/12/2016.
 */
@Name("Particles - Rotation XYZ values")
@Description({"Rotates specific effects using a specific x, y, z values",
		"This allows you to set the x, y or z value individually without changing the other values"})
@Examples({"set the x rotation value of the particle effect \"uniqueID\" to 1.7"})
public class ExprEffectRotationXYZ extends CustomEffectPropertyExpression<Number> {

	static {
		register(ExprEffectRotationXYZ.class, Number.class, "(1¦x|2¦y|3¦z) rotation value");
	}

	@Override
	public Number getPropertyValue(EffectData effect) {
		if (effect instanceof IAxis) {
			switch (mark) {
				case 1:
					return ((IAxis) effect).getAxisProperty().getAxis().getX();
				case 2:
					return ((IAxis) effect).getAxisProperty().getAxis().getY();
				case 3:
					return ((IAxis) effect).getAxisProperty().getAxis().getZ();
			}
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		if (effect instanceof IAxis) {
			double d = ((Number) (delta[0])).doubleValue();
			switch (mark) {
				case 1:
					((IAxis) effect).getAxisProperty().getAxis().setX(d);
					break;
				case 2:
					((IAxis) effect).getAxisProperty().getAxis().setY(d);
					break;
				case 3:
					((IAxis) effect).getAxisProperty().getAxis().setZ(d);
					break;
			}
		}
	}

	@Override
	public @NotNull Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public String getPropertyName() {
		return "(x, y, z) rotation value";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.AXIS;
	}

}
