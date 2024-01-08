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
import java.util.List;

import javax.annotation.Nullable;

import me.sashie.skdragon.util.EffectUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.sashie.skdragon.EffectAPI;
import me.sashie.skdragon.SkDragonRecode;
import me.sashie.skdragon.debug.SkriptNode;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.skript.sections.EffectSection;
import me.sashie.skdragon.skript.sections.ParticleEffectSection;

/**
 * Edited by Sashie on 6/20/2017
 */

@Name("Particles - Clientside/visible players of effect")
@Description({
		"Gets, sets, adds to and removes from the list of players able to see an effect, if the list is deleted all players can see the effect" })
@Examples({ "set {_players::*} to players of effect \"uniqueid\"",
		"set clientside players of effect \"uniqueid\" to {_players::*}",
		"add player to clientside players of effect \"uniqueid\"",
		"remove player from clientside players of effect \"uniqueid\"",
		"delete clientside players of effect \"uniqueid\"" })
public class ExprEffectAllPlayers extends SimpleExpression<Player> {

	static {
		Skript.registerExpression(ExprEffectAllPlayers.class, Player.class, ExpressionType.PROPERTY,
				"[the] [(clientside|visible)] player[s] of [the] [particle] effect %strings%",
				"[particle] effect %strings%'[s] [(clientside|visible)] player[s]",
				"[(clientside|visible)] player[s] of [the] [particle] effect");
	}

	protected boolean scope = false;
	private Expression<String> name;
	private SkriptNode skriptNode;

	@Override
	@Nullable
	protected Player[] get(Event e) {
		if (scope) {
			SkDragonRecode.warn("Incorrect use of syntax, can't get values from scope", skriptNode);
			return null;
		}
		
		String[] effectIDs = (String[]) this.name.getAll(e);
		if (effectIDs.length > 1)
			SkDragonRecode.warn("Only a single ID input can be used for setting players to a list variable. Subsequent IDs will be ignored.", skriptNode);

		final EffectData effect = EffectAPI.get(effectIDs[0], skriptNode);
		synchronized(effect) {
			return effect.getPlayers();
		}
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		final Player[] players = (Player[]) delta;

		if (scope) {
			EffectData effect = EffectAPI.get(ParticleEffectSection.getID(), skriptNode);
			synchronized(effect) {
				effect.setPlayers(players);
			}
		} else {
			List<String> failedEffects = new ArrayList<String>();
			String[] effectIDs = (String[]) this.name.getAll(e);

			if (effectIDs == null)
				return;

			for (String id : effectIDs) {
				if (!EffectAPI.ALL_EFFECTS.containsKey(id)) {
					failedEffects.add(id);
					continue;
				}
				EffectData effect = EffectAPI.get(id, skriptNode);
				synchronized (effect) {
					switch (mode) {
						case ADD:
							if (effect.getPlayers() == null)
								effect.setPlayers(players);
							else
								for (Player p : players)
									if (!EffectUtils.arrayContains(effect.getPlayers(), p))
										EffectUtils.addToArray(effect.getPlayers(), p);

							break;
						case REMOVE:
							if (effect.getPlayers() != null) {
								for (Player p : players)
									if (EffectUtils.arrayContains(effect.getPlayers(), p))
										EffectUtils.removeFromArray(effect.getPlayers(), p);
							}
							break;
						case REMOVE_ALL:
						case RESET:
						case DELETE:
							if (effect.getPlayers() != null) {
								//effect.getPlayers().clear();
								effect.setPlayers(null);
							}
							break;
						case SET:
							effect.setPlayers(players);
							break;
					}
				}
			}

			if (!failedEffects.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				for (String s : failedEffects) {
					sb.append(s);
					sb.append(", ");
				}
				SkDragonRecode.warn("One or more particle effects didn't exist! (" + sb.toString() + ")", skriptNode);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean k, SkriptParser.ParseResult p) {
		if (matchedPattern == 2) {
			if (EffectSection.isCurrentSection(ParticleEffectSection.class)) {
				this.scope = true;
			} else {
				return false;
			}
		} else {
			name = (Expression<String>) exprs[0];
		}
		skriptNode = new SkriptNode(SkriptLogger.getNode());

		return true;
	}

	@Override
	public Class<? extends Player> getReturnType() {
		return Player.class;
	}

	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		return CollectionUtils.array(Player[].class);
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "visible players";
	}
}
