package me.sashie.skdragon.effects.special;

import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.util.pool.ObjectPoolManager;
import org.bukkit.Particle;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.SpecialEffect;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.util.DynamicLocation;


/**
 * Created by Sashie on 12/12/2016.
 */

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
		return new ParticleBuilder<?>[] { new ColoredParticle(Particle.REDSTONE) };
	}

}