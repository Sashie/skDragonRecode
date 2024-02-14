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
import me.sashie.skdragon.api.EffectAPI;
import me.sashie.skdragon.api.debug.SkriptNode;
import me.sashie.skdragon.api.util.Utils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;


@Name("Stop particle effects")
@Description({"Stops all particle effects or one of a given ID name"})
@Examples({"stop particle effect \"%player%\"",
		"stop all particle effects"})
public class EffStopParticleEffects extends Effect {

	static {
		Skript.registerEffect(
				EffStopParticleEffects.class,
				"stop particle effect [(with id|named)] %string%",
				"stop all particle effects"
		);
	}

	private Expression<String> exprId = null;
	private SkriptNode skriptNode;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?> @NotNull [] expressions, int matchedPattern, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
		if (matchedPattern == 0) this.exprId = (Expression<String>) expressions[0];
		skriptNode = new SkriptNode(SkriptLogger.getNode());
		return true;
	}

	@Override
	protected void execute(@NotNull Event e) {
		if (exprId == null) {
			EffectAPI.stopAll();
			return;
		}

		String id = Utils.verifyVar(e, exprId, null);
		EffectAPI.stop(id, skriptNode); //even warn when the entry (e.g. a var) is null
	}

	@Override
	public @NotNull String toString(Event e, boolean debug) {
		return exprId == null ? "stop all particle effects" : "stop particle effect " + exprId.toString(e, debug);
	}

}
