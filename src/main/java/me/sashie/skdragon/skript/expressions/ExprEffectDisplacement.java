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

package me.sashie.skdragon.skript.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Sashie on 12/12/2016.
 */

@Name("Particles - Vector displacement")
@Description({"Sets or gets the displacement of a given particle effect from its location"})
@Examples({"set displacement vector of effect \"uniqueID\" to vector 0, 1 and 0"})
public class ExprEffectDisplacement extends CustomEffectPropertyExpression<Vector> {

	static {
		register(
				ExprEffectDisplacement.class,
				Vector.class,
				"displacement [vector]"
		);
	}

	@Override
	public Vector getPropertyValue(EffectData effect) {
		return new Vector(effect.getDisplacement().getX(), effect.getDisplacement().getY(), effect.getDisplacement().getZ());
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		Vector v = (Vector) (delta[0]);
		effect.setDisplacement(v);
	}

	@Override
	public @NotNull Class<? extends Vector> getReturnType() {
		return Vector.class;
	}

	@Override
	public String getPropertyName() {
		return "displacement vector";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.DISPLACEMENT;
	}
}
