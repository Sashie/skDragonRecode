package me.sashie.skdragon.effects.special;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.SpecialEffect;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.EffectUtils;
import me.sashie.skdragon.util.ParticleUtils;
import me.sashie.skdragon.util.pool.ObjectPoolManager;
import org.bukkit.Color;
import org.bukkit.Particle;

public class NyanCat extends SpecialEffect {

	DynamicLocation loc;

	public NyanCat() {
		loc = ObjectPoolManager.getDynamicLocationPool().acquire();
	}

	@Override
	public void update(DynamicLocation location) {
		loc.init(location);
		ParticleBuilder particle = this.getParticleBuilder(1);
		if (particle instanceof ColoredParticle) {
			ColoredParticle colorParticle = (ColoredParticle) particle;
			for (int i = 0; i < 15; i++) {
				int argb = java.awt.Color.HSBtoRGB(i / 20.0F, 1.0F, 1.0F);

				colorParticle.getParticleData().setColor(Color.fromARGB(argb));
				colorParticle.sendParticles(loc, this.getPlayers());

				loc.setY(loc.getY() + 0.1D);
			}
		} else {
			ParticleBuilder<?> p = ParticleUtils.createParticle(Particle.REDSTONE, particle.getParticleData());
			this.builders[0] = p;
		}
	}

	@Override
	public void onUnregister() {
		ObjectPoolManager.getDynamicLocationPool().release(loc);
	}

	@Override
	public EffectProperty[] acceptProperties() {
		return EffectUtils.array(EffectProperty.DENSITY);
	}

	@Override
	public ParticleBuilder<?>[] defaultParticles() {
		return new ParticleBuilder[] {new ColoredParticle(Particle.REDSTONE)};
	}
}