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

package me.sashie.skdragon.skript.conditions;

import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Checker;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.EffectAPI;

/**
 * Created by Sashie on 12/12/2016.
 */
@Name("Particles - Effect is active")
@Description("Checks whether a particle effect with a given id is active.")
@Examples({"particle effect id \"%player%\" is active"})
public class CondEffectIsActive extends Condition {

	static {
		Skript.registerCondition(CondEffectIsActive.class, "particle effect id %string% is active", "particle effect id %string% is not active");
	}

	private Expression<String> name;

	@SuppressWarnings({"unchecked"})
	@Override
	public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final ParseResult parseResult) {
		name = (Expression<String>) exprs[0];
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public boolean check(final Event e) {
		return name.check(e, new Checker<String>() {
			@Override
			public boolean check(final String s) {
				return EffectAPI.isRunning(s);
			}
		}, isNegated());
	}

	@Override
	public String toString(final @Nullable Event e, final boolean debug) {
		return "effect with id " + name.toString(e, debug) + " is " + (isNegated() ? "not active" : "active");
	}
}
