package me.sashie.skdragon.skript.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.particles.ParticleProperty;
import me.sashie.skdragon.util.Utils;
import org.bukkit.Particle;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Particles - Is Colorable")
@Description("Checks whether a particle is colorable.")
@Examples({"particle redstone is colorable",
		"particle flame is colorable"})
public class CondParticleIsColorable extends BaseConditions {

	static {
		Skript.registerCondition(
				CondParticleIsDirectional.class,
				"particle %particle% is colo[u]rable",
				"particle %particle% is not colo[u]rable")
		;
	}

	private Expression<Particle> exprParticle;

	@Override
	public boolean initCondition(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
		exprParticle = (Expression<Particle>) exprs[0];
		return true;
	}

	@Override
	public boolean checkCondition(@NotNull Event e) {
		Particle particle = Utils.verifyVar(e, exprParticle, null);
		return ParticleProperty.COLORABLE.hasProperty(particle);
	}

	@Override
	public String toString(Event e, boolean debug) {
		return exprParticle.toString(e, debug) + " particle " + (isNegated() ? "does not require" : "requires") + " material";
	}
}
