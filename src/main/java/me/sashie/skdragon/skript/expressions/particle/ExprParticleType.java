package me.sashie.skdragon.skript.expressions.particle;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.api.particles.ParticleBuilder;
import me.sashie.skdragon.skript.expressions.CustomParticlePropertyExpression;
import me.sashie.skdragon.skript.sections.ParticleSection;
import me.sashie.skdragon.api.util.ParticleUtils;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;


@Name("Particles - Particle type")
@Description({"Gets or sets the particle type used. Some data may be lost, for example if you change the particle type from Colorable to Normal it will lose its color data"})
@Examples({"set particle type of effect \"uniqueID\" to dust",
		"set the 1st particle type of the particle effect \"uniqueID\" to dust"})
public class ExprParticleType extends CustomParticlePropertyExpression<Particle> {

	static {
		register(ExprParticleType.class, Particle.class, "type");
	}

	@Override
	public Particle getParticle(ParticleBuilder<?> p) {
		return p.getParticleData().getParticle();
	}

	@Override
	public void setParticle(ParticleBuilder<?> particle, Object[] delta) {
		Particle type = (Particle) (delta[0]);

		ParticleBuilder<?> p = ParticleUtils.createParticle(type);
		p.initParticle(particle.getParticleData());
		p.getParticleData().setParticle(type);
		if (this.isParticleSection) {
			ParticleSection.particle = p;
		} else {
			this.effect.setParticle(this.particleNumber, p, this.skriptNode);
		}
	}

	@Override
	public @NotNull Class<? extends Particle> getReturnType() {
		return Particle.class;
	}

	@Override
	protected String getPropertyName() {
		return "type";
	}
}
