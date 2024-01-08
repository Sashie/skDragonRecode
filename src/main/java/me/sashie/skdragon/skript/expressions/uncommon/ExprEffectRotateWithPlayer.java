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

/**
 * Created by Sashie on 12/12/2016.
 */

@Name("Particles - Auto yaw")
@Description({"Make certain particle effects turn with the player or turn to face the player(depending on the effect type)"})
@Examples({	"set auto face of effect \"uniqueID\" to true"})
public class ExprEffectRotateWithPlayer extends CustomEffectPropertyExpression<Boolean> {

	static {
		register(ExprEffectRotateWithPlayer.class, Boolean.class, "auto face");
	}

	@Override
	public Boolean getPropertyValue(EffectData effect) {
		if (effect instanceof IRotation) {
			return ((IRotation) effect).getRotateProperty().isRotatingWithPlayer();
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		if (effect instanceof IRotation) {
			Boolean b = (Boolean) delta[0];
			((IRotation) effect).getRotateProperty().setRotatingWithPlayer(b);
		}
	}

	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;

	}

	@Override
	public String getPropertyName() {
		return "auto face";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.AUTO_FACE;
	}
}
