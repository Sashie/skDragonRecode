package me.sashie.skdragon.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.particles.ParticleProperty;
import me.sashie.skdragon.util.Utils;
import org.bukkit.Particle;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;


@Name("Particles - Property")
@Description({"Lists any properties this particle has if any"})
@Examples({"set {list::*} to particle properties of redstone"})
public class ExprAllParticleProperties extends SimpleExpression<String> {

	static {
		Skript.registerExpression(
				ExprAllParticleProperties.class,
				String.class,
				ExpressionType.SIMPLE,
				"[all] particle propert(y|ies) of %particle%"
		);
	}

	private Expression<Particle> exprParticle;

	public @NotNull Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] args, int arg1, @NotNull Kleenean arg2, @NotNull ParseResult arg3) {
		exprParticle = (Expression<Particle>) args[0];
		return true;
	}

	@Override
	public @NotNull String toString(@Nullable Event event, boolean debug) {
		return "all particle properties of " + exprParticle.toString(event, debug);
	}

	@Override
	protected String @NotNull [] get(@NotNull Event e) {
		Particle particle = Utils.verifyVar(e, exprParticle, null);
		if (particle == null) return new String[0];

		return ParticleProperty.getPropertiesForParticle(particle).stream()
				.map(ParticleProperty::toString)
				.toArray(String[]::new);
	}
}