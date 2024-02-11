package me.sashie.skdragon.skript.expressions.particle;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.particles.NormalParticle;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.skript.expressions.CustomParticlePropertyExpression;
import org.jetbrains.annotations.NotNull;


@Name("Particles - Particle speed")
@Description({"Some particle types have a speed option that changes how the particle moves along its preset path"})
@Examples({"set particle speed of effect \"uniqueID\" to .05"})
public class ExprParticleSpeed extends CustomParticlePropertyExpression<Number> {

	static {
		register(ExprParticleSpeed.class, Number.class, "speed");
	}

	@Override
	public Number getParticle(ParticleBuilder<?> p) {
		if (p instanceof NormalParticle) {
			return ((NormalParticle) p).getParticleData().speed;
		}

		return null;
	}

	@Override
	public void setParticle(ParticleBuilder<?> p, Object[] delta) {
		if (p instanceof NormalParticle) {
			Number n = (Number) (delta[0]);
			((NormalParticle) p).getParticleData().speed = n.floatValue();
		}
	}

	@Override
	public @NotNull Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	protected String getPropertyName() {
		return "speed";
	}
}
