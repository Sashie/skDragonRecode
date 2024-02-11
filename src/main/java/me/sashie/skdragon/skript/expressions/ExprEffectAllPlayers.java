package me.sashie.skdragon.skript.expressions;

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
import me.sashie.skdragon.util.EffectUtils;
import me.sashie.skdragon.util.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

	

@Name("Particles - Clientside/visible players of effect")
@Description({
		"Gets, sets, adds to and removes from the list of players able to see an effect, if the list is deleted all players can see the effect"})
@Examples({"set {_players::*} to players of effect \"uniqueid\"",
		"set clientside players of effect \"uniqueid\" to {_players::*}",
		"add player to clientside players of effect \"uniqueid\"",
		"remove player from clientside players of effect \"uniqueid\"",
		"delete clientside players of effect \"uniqueid\""})
public class ExprEffectAllPlayers extends SimpleExpression<Player> {

	static {
		Skript.registerExpression(ExprEffectAllPlayers.class, Player.class, ExpressionType.PROPERTY,
				"[the] [(clientside|visible)] player[s] of [the] [particle] effect %strings%",
				"[particle] effect %strings%'[s] [(clientside|visible)] player[s]",
				"[(clientside|visible)] player[s] of [the] [particle] effect");
	}

	protected boolean scope = false;
	private Expression<String> exprNames;
	private SkriptNode skriptNode;

	@Override
	protected Player @NotNull [] get(@NotNull Event e) {
		if (scope) {
			SkDragonRecode.warn("Incorrect use of syntax, can't get values from scope", skriptNode);
			return new Player[0];
		}

		String[] effectIds = Utils.verifyVars(e, exprNames, null);
		if (effectIds == null) return new Player[0];

		if (effectIds.length > 1)
			SkDragonRecode.warn("Only a single ID input can be used for setting players to a list variable. Subsequent IDs will be ignored.", skriptNode);

		EffectData effect = EffectAPI.get(effectIds[0], skriptNode);
		if (effect == null) return new Player[0];
		synchronized (effect) {
			return effect.getPlayers();
		}
	}

	@Override
	public void change(@NotNull Event e, Object @NotNull [] delta, Changer.@NotNull ChangeMode mode) {
		final Player[] players = new Player[delta.length];
		for (int i = 0; i < delta.length; i++) {
			players[i] = (Player) delta[i];
		}

		if (scope) {
			EffectData effect = EffectAPI.get(ParticleEffectSection.getID(), skriptNode);
			if (effect == null) return;
			synchronized (effect) {
				effect.setPlayers(players);
			}
		} else {
			List<String> failedEffects = new ArrayList<>();

			String[] effectIds = Utils.verifyVars(e, exprNames, null);
			if (effectIds == null) return;

			for (String id : effectIds) {
				if (!EffectAPI.ALL_EFFECTS.containsKey(id)) {
					failedEffects.add(id);
					continue;
				}
				EffectData effect = EffectAPI.get(id, skriptNode);
				if (effect == null) return;
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
				SkDragonRecode.warn("One or more particle effects didn't exist! (" + String.join(", ", failedEffects) + ")", skriptNode);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean k, SkriptParser.@NotNull ParseResult p) {
		if (matchedPattern == 2) {
			if (EffectSection.isCurrentSection(ParticleEffectSection.class)) {
				this.scope = true;
			} else {
				return false;
			}
		} else {
			exprNames = (Expression<String>) exprs[0];
		}
		skriptNode = new SkriptNode(SkriptLogger.getNode());

		return true;
	}

	@Override
	public @NotNull Class<? extends Player> getReturnType() {
		return Player.class;
	}

	@Override
	public Class<?> @NotNull [] acceptChange(final Changer.@NotNull ChangeMode mode) {
		return CollectionUtils.array(Player[].class);
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public @NotNull String toString(@Nullable Event e, boolean b) {
		return "visible players";
	}
}
