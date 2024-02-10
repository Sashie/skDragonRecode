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
		if (exprId == null) {
			EffectAPI.unregisterAll();
			return;
		}

		String id = Utils.verifyVar(e, exprId, null);
		EffectAPI.unregister(id, skriptNode); //even warn when the entry (e.g. a var) is null
	}

	@Override
	public @NotNull String toString(Event event, boolean debug) {
		return exprId == null ? "remove all particle effects" : "remove particle effect named " + exprId.toString(event, debug);
	}

}
