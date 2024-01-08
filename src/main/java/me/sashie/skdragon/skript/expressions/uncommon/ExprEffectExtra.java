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
import me.sashie.skdragon.effects.properties.IExtra;
import me.sashie.skdragon.skript.expressions.CustomArrayPropertyExpression;

/**
 * Created by Sashie on 12/12/2016.
 */

@Name("Particles - Effect extra value")
@Description({"Get or set the extra value of an effect if it has this property. To learn more please see the individual particle effects!"})
@Examples({	"set extra value of effect \"uniqueID\" to 20"})
public class ExprEffectExtra extends CustomArrayPropertyExpression<Number> {

	static {
		register(ExprEffectExtra.class, Number.class, "extra [value]");
	}

	@Override
	public Object getPropertyArray(EffectData effect) {
		if (effect instanceof IExtra) {
			return ((IExtra) effect).getExtraProperty().getArray();
		}
		return null;
	}

	@Override
	public Number getPropertyValue(int propertyNumber, EffectData effect) {
		if (effect instanceof IExtra) {
			return ((IExtra) effect).getExtraProperty().getValue(propertyNumber);
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, int propertyNumber, Number value) {
		if (effect instanceof IExtra) {
			((IExtra) effect).getExtraProperty().setValue(propertyNumber, value.intValue());
		}
	}

	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public String getPropertyName() {
		return "extra";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.EXTRA;
	}

}
