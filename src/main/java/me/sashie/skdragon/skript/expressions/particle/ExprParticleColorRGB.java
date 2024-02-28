package me.sashie.skdragon.skript.expressions.particle;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.skript.expressions.CustomParticlePropertyExpression;
import org.bukkit.Color;
import org.jetbrains.annotations.NotNull;

@Name("Particles - Color RGB values")
@Description({"Gets the current color of a particle, setting a color with this expression will make it a solid color"})
@Examples({"set the 1st particle red value of the particle effect \"uniqueID\" to 255"})
public class ExprParticleColorRGB extends CustomParticlePropertyExpression<Number> {

	static {
		register(ExprParticleColorRGB.class, Number.class, "(1¦r[ed]|2¦g[reen]|3¦b[lue]) value");
	}

	@Override
	public Number getParticle(ParticleBuilder<?> p) {
		if (p instanceof ColoredParticle) {
			switch (mark) {
				case 1:
					return ((ColoredParticle) p).getParticleData().colors.get().getRed();
				case 2:
					return ((ColoredParticle) p).getParticleData().colors.get().getGreen();
				case 3:
					return ((ColoredParticle) p).getParticleData().colors.get().getBlue();
			}
		}

		return null;
	}

	@Override
	public void setParticle(ParticleBuilder<?> p, Object[] delta) {
		if (p instanceof ColoredParticle) {
			int value = ((Number) (delta[0])).intValue();
			if (value < 0)
				value = 0;
			else if (value > 255)
				value = 255;

			Color c = ((ColoredParticle) p).getParticleData().colors.get();
			switch (mark) {
				case 1:
					((ColoredParticle) p).getParticleData().setColor(org.bukkit.Color.fromRGB(value, c.getGreen(), c.getBlue()));
					break;
				case 2:
					((ColoredParticle) p).getParticleData().setColor(org.bukkit.Color.fromRGB(c.getRed(), value, c.getBlue()));
					break;
				case 3:
					((ColoredParticle) p).getParticleData().setColor(org.bukkit.Color.fromRGB(c.getRed(), c.getGreen(), value));
					break;
			}
		}
	}

	@Override
	public @NotNull Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	protected String getPropertyName() {
		return switch (mark) {
			case 1 -> "red value";
			case 2 -> "green value";
			case 3 -> "blue value";
			default -> "";
		};
	}
}
