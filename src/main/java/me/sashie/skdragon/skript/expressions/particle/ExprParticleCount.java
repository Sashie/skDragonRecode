package me.sashie.skdragon.skript.expressions.particle;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.api.particles.ParticleBuilder;
import me.sashie.skdragon.skript.expressions.CustomParticlePropertyExpression;
import org.jetbrains.annotations.NotNull;



@Name("Particles - Particle amount/count")
@Description({"The amount of a single particle type to be displayed"})
@Examples({"set particle amount of effect \"uniqueID\" to 20"})
public class ExprParticleCount extends CustomParticlePropertyExpression<Number> {

	static {
		register(ExprParticleCount.class, Number.class, "(amount|count)");
	}

	@Override
	public Number getParticle(ParticleBuilder<?> p) {
		return p.getParticleData().getAmount();
	}

	@Override
	public void setParticle(ParticleBuilder<?> p, Object[] delta) {
		int i = ((Number) (delta[0])).intValue();
		p.getParticleData().setAmount(i > 0 ? i : 1);
	}

	@Override
	public @NotNull Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	protected String getPropertyName() {
		return "amount";
	}
}
