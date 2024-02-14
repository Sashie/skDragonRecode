package me.sashie.skdragon.skript.expressions.particle;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.api.particles.ParticleBuilder;
import me.sashie.skdragon.skript.expressions.CustomParticlePropertyExpression;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;


@Name("Particles - Offset vector")
@Description({"This property works best when the amount of particles is more than 1, it makes the particle offset randomly between the start location and the offset value"})
//[the] %number%(st|nd|rd|th) particle rgb value of [the] [particle] effect[s] %strings%
@Examples({"set the 1st particle offset of the particle effect \"uniqueID\" to {_v}"})
public class ExprParticleOffset extends CustomParticlePropertyExpression<Vector> {

	static {
		register(ExprParticleOffset.class, Vector.class, "offset [vector]");
	}

	@Override
	public Vector getParticle(ParticleBuilder<?> p) {
		return p.getParticleData().getOffset();
	}

	@Override
	public void setParticle(ParticleBuilder<?> p, Object[] delta) {
		Vector v = (Vector) (delta[0]);
		p.getParticleData().setOffset(v);
	}

	@Override
	public @NotNull Class<? extends Vector> getReturnType() {
		return Vector.class;
	}

	@Override
	protected String getPropertyName() {
		return "offset vector";
	}
}
