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
import me.sashie.skdragon.effects.properties.IDensity;
import me.sashie.skdragon.skript.expressions.CustomArrayPropertyExpression;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Sashie on 12/12/2016.
 */
@Name("Particles - Effect density")
@Description({"Get or set the density of an effect if it has this property"})
@Examples({"set 1st density of effect \"uniqueID\" to 20"})
public class ExprEffectDensity extends CustomArrayPropertyExpression<Number> {

	static {
		register(
				ExprEffectDensity.class,
				Number.class,
				"density"
		);
	}

	@Override
	public Object getPropertyArray(EffectData effect) {
		if (effect instanceof IDensity) {
			return ((IDensity) effect).getDensityProperty().getArray();
		}
		return null;
	}

	@Override
	public Number getPropertyValue(int propertyNumber, EffectData effect) {
		if (effect instanceof IDensity) {
			return ((IDensity) effect).getDensityProperty().getDensity(propertyNumber);
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, int propertyNumber, Number value) {
		if (effect instanceof IDensity) {
			((IDensity) effect).getDensityProperty().setDensity(propertyNumber, value.intValue());
		}
	}

	@Override
	public @NotNull Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public String getPropertyName() {
		return "density";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.DENSITY;
	}
}
