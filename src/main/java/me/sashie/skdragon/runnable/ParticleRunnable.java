package me.sashie.skdragon.runnable;

import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.properties.IDensity;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.ParticleUtils;
import me.sashie.skdragon.util.pool.ObjectPoolManager;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ParticleRunnable extends BukkitRunnableTask {


	private boolean init = true;

	ParticleBuilder<?> particle;
	DynamicLocation[] locations;
	private Vector displacement;
	Player[] players;
	private int iterations;
	private long startTime, duration;

	public ParticleRunnable(ParticleBuilder<?> particle, DynamicLocation[] locations, Vector displacement, Player[] players, long duration, int iterations) {
		this.particle = particle;
		this.locations = locations;
		this.displacement = displacement;
		this.players = players;
		this.duration = duration;
		this.iterations = iterations;
		this.startTime = System.currentTimeMillis();
	}

	@Override
	public void run() {
		if (this.particle == null) {
			cancel();
			return;
		}

		if (duration != 0L && System.currentTimeMillis() - startTime >= duration) {
			cancel();
			return;
		}

		for (DynamicLocation location : locations) {
			if (location == null) continue;

			if (init) {
				if (!location.isDynamic())
					location.add(this.displacement.getX(), this.displacement.getY(), this.displacement.getZ());
				init = false;
			} else {
				if (location.isDynamic()) {
					location.update();
					location.add(this.displacement.getX(), this.displacement.getY(), this.displacement.getZ());
				}

				this.particle.sendParticles(location, this.players);

				ParticleUtils.updateColoredParticles(this.particle);
			}
		}

		if (duration == 0) {
			iterations--;

			if (iterations < 1) {
				cancel();
			}
		}
	}
}
