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
import me.sashie.skdragon.util.Utils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Sashie on 12/12/2017.
 */

@Name("Remove particle effects")
@Description({"Removes all particle effects or one of a given ID name"})
@Examples({"unregister particle effect \"%player%\"",
		"unregister all particle effects"})
public class EffRemoveParticleEffects extends Effect {

	static {
		Skript.registerEffect(
				EffRemoveParticleEffects.class,
				"(unregister|remove) particle effect [(with id|named)] %string%",
				"(unregister|remove) all particle effects"
		);
	}

	private Expression<String> exprId = null;
	private SkriptNode skriptNode;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?> @NotNull [] expressions, int matchedPattern, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
		if (matchedPattern == 0) exprId = (Expression<String>) expressions[0];
		skriptNode = new SkriptNode(SkriptLogger.getNode());
		return true;
	}

	@Override
	protected void execute(@NotNull Event e) {
		if (exprId == null) EffectAPI.stopAll();

		String id = Utils.verifyVar(e, exprId, null);
		EffectAPI.unregister(id, skriptNode); //even warn when the entry (e.g. a var) is null
	}

	@Override
	public @NotNull String toString(Event event, boolean debug) {
		return exprId == null ? "remove all particle effects" : "remove particle effect named " + exprId.toString(event, debug);
	}

}
