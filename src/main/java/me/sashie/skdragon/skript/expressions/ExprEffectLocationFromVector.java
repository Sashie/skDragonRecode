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

import java.util.ArrayList;

import javax.annotation.Nullable;

import me.sashie.skdragon.SkDragonRecode;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.util.coll.CollectionUtils;
import me.sashie.skdragon.EffectAPI;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.util.DynamicLocation;

/**
 * Created by Sashie on 12/12/2016.
 */

@Name("Particles - Vector location")
@Description({""})
@Examples({	"set vector location of effect \"uniqueID\" to {_v}",
		"add {_v} to vector location of effect \"uniqueID\"",
		"remove {_v} from vector location of effect \"uniqueID\""})
public class ExprEffectLocationFromVector extends CustomEffectPropertyExpression<Vector> {

	static {
		register(ExprEffectLocationFromVector.class, Vector.class, "location (from|as) vector");
	}

	@Override
	public Vector getPropertyValue(EffectData effect) {
		return null;
	}
	
	@Override
	@Nullable
	protected Vector[] get(Event e) {
		String[] s = (String[]) getExpr().getAll(e);
		if (EffectAPI.ALL_EFFECTS.containsKey(s[0])) {
			synchronized(EffectAPI.get(s[0], skriptNode)) {
				ArrayList<Vector> cl = new ArrayList<>();
				for (DynamicLocation location : EffectAPI.get(s[0], skriptNode).getLocations()) {
					cl.add(location.toVector());
				}
				return cl.toArray(new Vector[cl.size()]);
			}
		}
		return null;
	}
	
	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		synchronized(effect) {
			switch (getMode()) {
				case ADD:
					for (int i = 0; i < effect.getLocations().length; i++) {
						effect.getLocations()[i].add((Vector) delta[0]);
					}
					break;
				case REMOVE:
					for (int i = 0; i < effect.getLocations().length; i++) {
						effect.getLocations()[i].subtract((Vector) delta[0]);
					}
					break;
				case SET:
					Vector v = (Vector) delta[0];
					effect.getLocations()[0].set(v.getX(), v.getY(), v.getZ());

					if (delta.length > 1) {
						SkDragonRecode.warn("Only the first location will be set. Subsequent locations will be ignored. To set more consider stopping the effect and starting it at a new location or reconsider how you are using it", skriptNode);
					}
					break;
				default:
					break;
			}
		}
	}

	@Override
	public Class<? extends Vector>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE)
			return CollectionUtils.array(Vector.class);
		return null;
	}

	@Override
	public Class<? extends Vector> getReturnType() {
		return Vector.class;

	}

	@Override
	public String getPropertyName() {
		return "location from/as vector";
	}

}
