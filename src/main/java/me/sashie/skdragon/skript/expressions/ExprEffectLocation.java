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

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.util.coll.CollectionUtils;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.util.EffectUtils;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Sashie on 12/12/2016.
 */

@Name("Particles - Location")
@Description({"Similar to the location vector expression this one instead sets the location from either another location or an entity. Adding and removing locations instead of to the location"})
@Examples({"set location of effect \"uniqueID\" to location of player"})
public class ExprEffectLocation extends CustomEffectPropertyExpression<Object> {

	static {
		register(
				ExprEffectLocation.class,
				Object.class,
				"location[s]"
		);
	}

	@Override
	public Object getPropertyValue(EffectData effect) {
		return effect.getLocations();
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		switch (getMode()) {
			case ADD:
				effect.setLocations(EffectUtils.addArray(effect.getLocations(), EffectUtils.toDynamicLocations(delta)));
				break;
			case REMOVE:
				effect.setLocations(EffectUtils.removeLocations(effect.getLocations(), EffectUtils.toDynamicLocations(delta)));
				break;
			case RESET:
				effect.resetLocations();
				break;
			case SET:
				effect.setLocations(EffectUtils.toDynamicLocations(delta));
				break;
			default:
				break;
		}
	}

	@Override
	public Class<?> @NotNull [] acceptChange(final Changer.@NotNull ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.RESET)
			return CollectionUtils.array(Object[].class);
		return null;
	}

	@Override
	public @NotNull Class<?> getReturnType() {
		return Location.class;
	}

	@Override
	public String getPropertyName() {
		return "location(s)";
	}
}
