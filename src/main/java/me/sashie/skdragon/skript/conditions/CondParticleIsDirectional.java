package me.sashie.skdragon.skript.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.api.particles.ParticleProperty;
import me.sashie.skdragon.api.util.Utils;
import org.bukkit.Particle;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;


@Name("Particles - Is Directional")
@Description("Checks whether a particle is directional.")
@Examples({"particle redstone is directional"})
public class CondParticleIsDirectional extends BaseConditions {

	static {
		Skript.registerCondition(
				CondParticleIsDirectional.class,
				"particle %particle% is directional",
				"particle %particle% is not directional"
		);
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
		return ParticleProperty.DIRECTIONAL.hasProperty(particle);
	}

	@Override
	public String toStringCondition(Event e, boolean debug) {
		return exprParticle.toString(e, debug) + " particle " + (isNegated() ? "does not require" : "requires") + " material";
	}

}
