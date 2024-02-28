package me.sashie.skdragon.skript.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.particles.ParticleProperty;
import me.sashie.skdragon.util.Utils;
import org.bukkit.Particle;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Particles - Requires material")
@Description("Checks whether a particle requires material.")
@Examples({"particle redstone requires material #returns false"})
public class CondParticleRequiresData extends BaseConditions {

	static {
		Skript.registerCondition(
				CondParticleRequiresData.class,
				"particle %particle% requires material",
				"particle %particle% does not require material"
		);
	}

	private Expression<Particle> exprParticle;

	@Override
	public boolean initCondition(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean kleenean, @NotNull ParseResult parseResult) {
		exprParticle = (Expression<Particle>) exprs[0];
		return true;
	}

	@Override
	public boolean checkCondition(@NotNull Event e) {
		Particle particle = Utils.verifyVar(e, exprParticle, null);
		return ParticleProperty.REQUIRES_DATA.hasProperty(particle);
	}

	@Override
	public String toString(Event e, boolean debug) {
		return exprParticle.toString(e, debug) + " particle " + (isNegated() ? "does not require" : "requires") + " material";
	}

}
