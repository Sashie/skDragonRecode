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
import me.sashie.skdragon.effects.properties.IRotation;
import me.sashie.skdragon.skript.expressions.CustomEffectPropertyExpression;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Sashie on 12/12/2016.
 */

@Name("Particles - Auto rotation")
@Description({"If an effect uses this it makes the effect rotate using the rotation vector expression"})
@Examples({"set auto rotation of effect \"uniqueID\" to true"})
public class ExprEffectRotate extends CustomEffectPropertyExpression<Boolean> {

	static {
		register(ExprEffectRotate.class, Boolean.class, "auto rotation");
	}

	@Override
	public Boolean getPropertyValue(EffectData effect) {
		if (effect instanceof IRotation) {
			return ((IRotation) effect).getRotateProperty().isRotating();
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		if (effect instanceof IRotation) {
			Boolean b = (Boolean) delta[0];
			((IRotation) effect).getRotateProperty().setRotating(b);
		}
	}

	@Override
	public @NotNull Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}

	@Override
	public String getPropertyName() {
		return "auto rotation";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.AUTO_ROTATE;
	}
}
