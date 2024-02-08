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

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.EffectAPI;
import me.sashie.skdragon.debug.SkriptNode;
import me.sashie.skdragon.runnable.RunnableType;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.EffectUtils;

/**
 * Created by Sashie on 5/14/2017.
 */

@Name("Start particle effect")
@Description({"Starts a particle effect of a given id name.",
				"   - Using the sync optional makes the effect play in a synchronous runnable instead",
				"   - Adding a delay makes the effect start later",
				"   - The effect will play only once unless you add the repeating option",
				"   - The repeating option will play infinitely unless the number of times option is used",
				"   - When using repeat the interval is used to determine how fast between iterations",
				"   - The targeting option is only used for certain effects"})
@Examples({	"start particle effect \"unique_name\" at location of player",
			"start sync particle effect \"%player%\"",
			"start particle effect \"%player%\" delayed by 10 ticks",
			"start particle effect \"%player%\" repeating with an interval of 1 tick",
			"start particle effect \"%player%\" repeating 5 times with an interval of 0 ticks",
			"start sync particle effect \"%player%\" delayed by 10 ticks repeating 5 times with an interval of 5 ticks"})
public class EffStartParticleEffect extends Effect {

	static {
		Skript.registerEffect(EffStartParticleEffect.class,
				"(start|run) [sync] particle effect %string% at %objects% [targeting %-objects%] [delayed by %-timespan%] [repeat(ed|ing) [%-number% times] with [an] interval of %-timespan%]");
		//  "(start|run) [sync] particle effect %string% at %objects% [delayed by %-timespan%] [repeat(ed|ing) [%-number% times] with [an] interval of %-timespan%]")
	}

	private String parsedSyntax;
	private Expression<String> name;
	private Expression<Object> location, targets;
	private Expression<Timespan> inputDelay;
	private Expression<Number> inputRepeat;
	private Expression<Timespan> inputInterval;
	private SkriptNode skriptNode;

	@Override
	protected void execute(Event event) {
		String id = this.name.getSingle(event);
		if (id == null)
			return;

		long finalDelay = EffectUtils.getSingleWithDefault(event, inputDelay, new Timespan(0)).getTicks_i();
		int finalRepeat = EffectUtils.getSingleWithDefault(event, inputRepeat, -1).intValue();
		long finalInterval = EffectUtils.getSingleWithDefault(event, inputInterval, new Timespan(0)).getTicks_i();

		Object[] locations = this.location.getArray(event);
		if (locations == null)
			return;

		//repeatable tasks also allow a delay so 'type' falls through if effect is repeatable
	    RunnableType type = RunnableType.INSTANT;
	    if (finalDelay > 0)
	    	type = RunnableType.DELAYED;
		if (parsedSyntax.contains("repeat") || inputRepeat != null)
			type = RunnableType.REPEATING;

		DynamicLocation[] locs = EffectUtils.toDynamicLocations(locations);

		if (targets == null) {
			EffectAPI.start(id, type, finalRepeat, finalDelay, finalInterval, parsedSyntax.contains(" sync "), locs, skriptNode);
		} else {
			Object[] inputTargets = this.targets.getArray(event);
			if (inputTargets == null)
				return;
			DynamicLocation[] targets = EffectUtils.toDynamicLocations(inputTargets);
			EffectAPI.start(id, type, finalRepeat, finalDelay, finalInterval, parsedSyntax.contains(" sync "), locs, targets, skriptNode);
		}
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "start" + (parsedSyntax.contains(" sync ") ? " sync " : " ") + "particle effect " + name.toString(event, debug) + (inputDelay != null ? " delayed by " + inputDelay.toString(event, debug) : " ") + (parsedSyntax.contains(" repeat") ? "repeating" + (inputRepeat != null ? inputRepeat.toString(event, debug) + " times" : " ")  + "with an interval of " + inputInterval.toString(event, debug): "");
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		name = (Expression<String>) exprs[0];
		location = (Expression<Object>) exprs[1];

		targets = (Expression<Object>) exprs[2];

		inputDelay = (Expression<Timespan>) exprs[3];
		inputRepeat = (Expression<Number>) exprs[4];
		inputInterval = (Expression<Timespan>) exprs[5];
		parsedSyntax = parseResult.expr;
		skriptNode = new SkriptNode(SkriptLogger.getNode());

		return true;
	}
}
