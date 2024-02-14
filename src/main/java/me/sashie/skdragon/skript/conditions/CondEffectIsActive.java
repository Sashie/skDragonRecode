package me.sashie.skdragon.skript.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.api.EffectAPI;
import me.sashie.skdragon.api.util.Utils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Particles - Effect is active")
@Description("Checks whether a particle effect with a given id is active.")
@Examples({"particle effect id \"%player%\" is active"})
public class CondEffectIsActive extends BaseConditions {

	static {
		Skript.registerCondition(
				CondEffectIsActive.class,
				"particle effect id %string% is active",
				"particle effect id %string% is not active"
		);
	}

	private Expression<String> exprId;

	@Override
	public boolean initCondition(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean kleenean, @NotNull ParseResult parseResult) {
		exprId = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	public boolean checkCondition(@NotNull Event e) {
		String id = Utils.verifyVar(e, exprId, null);
		return EffectAPI.isRunning(id);
	}

	@Override
	public String toStringCondition(Event e, boolean debug) {
		return "effect with id " + exprId.toString(e, debug) + " is " + (isNegated() ? "not active" : "active");
	}

}
