package me.sashie.skdragon.skript.expressions.particle;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.skript.expressions.CustomParticlePropertyExpression;
import org.jetbrains.annotations.NotNull;


@Name("Particles - Offset XYZ values")
@Description({"Similar to the other particle offset expression this one lets you change the individual x, y or z values separately"})
@Examples({"set the 1st particle x offset value of the particle effect \"uniqueID\" to 1.7"})
public class ExprParticleOffsetXYZ extends CustomParticlePropertyExpression<Number> {

	static {
		register(ExprParticleOffsetXYZ.class, Number.class, "(1¦x|2¦y|3¦z) offset value");
	}

	@Override
	public Number getParticle(ParticleBuilder<?> p) {
		return switch (mark) {
			case 1 -> p.getParticleData().getOffset().getX();
			case 2 -> p.getParticleData().getOffset().getY();
			case 3 -> p.getParticleData().getOffset().getZ();
			default -> null;
		};
	}

	@Override
	public void setParticle(ParticleBuilder<?> p, Object[] delta) {
		float f = ((Number) (delta[0])).floatValue();

		switch (mark) {
			case 1:
				p.getParticleData().getOffset().setX(f > 0 ? f : 0);
				break;
			case 2:
				p.getParticleData().getOffset().setY(f > 0 ? f : 0);
				break;
			case 3:
				p.getParticleData().getOffset().setZ(f > 0 ? f : 0);
				break;
		}

	}

	@Override
	public @NotNull Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	protected String getPropertyName() {
		return switch (mark) {
			case 1 -> "x offset value";
			case 2 -> "y offset value";
			case 3 -> "z offset value";
			default -> "";
		};
	}
}
