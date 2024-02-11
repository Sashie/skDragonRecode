package me.sashie.skdragon.skript.expressions.particle;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.particles.DirectionParticle;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.skript.expressions.CustomParticlePropertyExpression;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;


@Name("Particles - Particle Direction")
@Description({"Some particle types have a direction option that changes how the particle moves along its preset path"})
@Examples({"set particle direction of \"uniqueID\" to vector from player"})
public class ExprParticleDirection extends CustomParticlePropertyExpression<Vector> {

	static {
		register(ExprParticleDirection.class, Vector.class, "direction");
	}

	@Override
	public Vector getParticle(ParticleBuilder<?> p) {
		if (p instanceof DirectionParticle) {
			return ((DirectionParticle) p).getParticleData().direction;
		}

		return null;
	}

	@Override
	public void setParticle(ParticleBuilder<?> p, Object[] delta) {
		if (p instanceof DirectionParticle) {
			Vector v = (Vector) (delta[0]);
			((DirectionParticle) p).getParticleData().direction = v;
		}
	}

	@Override
	public @NotNull Class<? extends Vector> getReturnType() {
		return Vector.class;
	}

	@Override
	protected String getPropertyName() {
		return "direction";
	}
}
