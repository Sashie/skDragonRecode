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

package me.sashie.skdragon.skript.effects;

import me.sashie.skdragon.effects.ParticleEffect;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.EffectAPI;
import me.sashie.skdragon.debug.SkriptNode;

/**
 * Created by Sashie on 12/12/2017.
 */

@Name("Remove particle effects")
@Description({"Removes all particle effects or one of a given ID name"})
@Examples({	"remove particle effect \"%player%\"",
	"remove all particle effects"})
public class EffRemoveParticleEffects extends Effect {

	static {
		Skript.registerEffect(EffRemoveParticleEffects.class, 
				"(unregister|remove) particle effect [(with id|named)] %string%",
				"(unregister|remove) all particle effects");
	}

	private boolean all;
	private Expression<String> name;
	private SkriptNode skriptNode;

	@Override
	protected void execute(Event event) {
		if (all) {
			EffectAPI.unregisterAll();
		} else {
			String id = this.name.getSingle(event);

			if (id == null)
				return;

			EffectAPI.unregister(id, skriptNode);
		}
	}

	@Override
	public String toString(Event event, boolean debug) {
		if (all)
			return "remove all particle effects";
		else
			return "remove particle effect named " + this.name.toString(event, debug);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		this.all = matchedPattern == 1;
		this.name = (Expression<String>) expressions[0];
		skriptNode = new SkriptNode(SkriptLogger.getNode());

		return true;
	}
}
