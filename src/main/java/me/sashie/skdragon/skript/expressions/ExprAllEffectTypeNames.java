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

import java.util.Arrays;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.effects.ParticleEffect;

/**
 * Edited by Sashie on 1/20/2017
 */

@Name("All particle effect type names")
@Description({ "Gets a list of all particle effect type names used in skDragon ie. circle, dot, sphere" })
@Examples({ "all particle effect names" })
public class ExprAllEffectTypeNames extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprAllEffectTypeNames.class, String.class, ExpressionType.SIMPLE,
				"[all] particle effect names");
	}

	@Override
	public boolean init(Expression<?>[] e, int i, Kleenean k, SkriptParser.ParseResult p) {
		return true;
	}

	@Override
	@Nullable
	protected String[] get(Event e) {
		String[] result = Arrays.stream(ParticleEffect.values())
				.map(effect -> effect.toString().toLowerCase().replace("_", " "))
				.toArray(String[]::new);

		return result;
	}

	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "all particle effect names";
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}
}
