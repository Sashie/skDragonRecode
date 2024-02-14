package me.sashie.skdragon.api.effects.special;

import me.sashie.skdragon.api.effects.EffectProperty;
import me.sashie.skdragon.api.effects.SpecialEffect;
import me.sashie.skdragon.api.particles.ColoredParticle;
import me.sashie.skdragon.api.particles.ParticleBuilder;
import me.sashie.skdragon.api.util.DynamicLocation;
import me.sashie.skdragon.api.util.pool.ObjectPoolManager;
import org.bukkit.Particle;


public class Band extends SpecialEffect {

	DynamicLocation loc;

	public Band() {
		loc = ObjectPoolManager.getDynamicLocationPool().acquire();
	}

	@Override
	public void update(DynamicLocation location, float step) {
		loc.init(location);
		for (int i = 0; i < 15; i++) {
			loc.setY(loc.getY() + 0.1D);
			this.getParticleBuilder(1).sendParticles(loc, this.getPlayers());
		}
	}

	@Override
	public void onUnregister() {
		ObjectPoolManager.getDynamicLocationPool().release(loc);
	}

	@Override
	public EffectProperty[] acceptProperties() {
		return null;
	}

	@Override
	public ParticleBuilder<?>[] defaultParticles() {
		return new ParticleBuilder<?>[]{new ColoredParticle(Particle.REDSTONE)};
	}

}