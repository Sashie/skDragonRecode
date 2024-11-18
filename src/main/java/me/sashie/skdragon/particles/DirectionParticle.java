package me.sashie.skdragon.particles;

import java.util.Iterator;
import java.util.function.Consumer;

import me.sashie.skdragon.particles.data.ParticleData;
import me.sashie.skdragon.util.*;
import me.sashie.skdragon.util.pool.ObjectPoolManager;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.sashie.skdragon.particles.data.DirectionParticleData;

public class DirectionParticle extends ParticleBuilder<DirectionParticleData> {

	private DynamicList<DirectionAnimation> directionals = new DynamicList<DirectionAnimation>();

	public DirectionParticle() {
		super.initData(new DirectionParticleData(this));
	}

	public DirectionParticle(Particle particle) {
		this();
		this.data.setParticle(particle);
	}

	public DirectionParticle(DirectionParticleData inputData) {
		super.initData(inputData);
	}

	public DirectionParticle(DirectionParticleData inputData, Consumer<DirectionParticleData> data) {
		super(inputData);
		data.accept(this.data);
	}

	@Override
	public void sendParticles(DynamicLocation location, Player... player) {
		if (ParticleProperty.DIRECTIONAL.hasProperty(this.data.getParticle())) {
			if (player == null || player.length == 0) {
				for (int i = 0; i < data.getAmount(); i++) {
					location.getWorld().spawnParticle(this.data.getParticle(), ParticleUtils.getOffsetLocation(this.data, location), 0, this.data.getDirection().getX(), this.data.getDirection().getY(), this.data.getDirection().getZ(), this.data.getSpeed(), null);
				}
			} else {
				for (int j = 0; j < player.length; j++) {
					for (int i = 0; i < this.data.getAmount(); i++) {
						player[j].spawnParticle(this.data.getParticle(), ParticleUtils.getOffsetLocation(this.data, location), 0, this.data.getDirection().getX(), this.data.getDirection().getY(), this.data.getDirection().getZ(), this.data.getSpeed());
					}
				}
			}
		} else {
			directionals.add(new DirectionAnimation(location, this.data.getDirection(), this.data.getSpeed()));

			Iterator<DirectionAnimation> iterator = null;
			for (iterator = directionals.iterator(); iterator.hasNext();) {
				DirectionAnimation animation = iterator.next();

				if (animation.needsUpdate()) {
					animation.update(player);
				} else {
					animation.onStop();
					iterator.remove();
					return;
				}
			}
		}
	}

	private class DirectionAnimation {
		DynamicLocation location;
		private long startTime;
		Vector v;

		public DirectionAnimation(final DynamicLocation loc, Vector direction, double speed) {
			location = ObjectPoolManager.getDynamicLocationPool().acquire(loc);
			v = VectorUtils.subtract(ObjectPoolManager.getVectorPool().acquire(direction), location).normalize().multiply(speed);
			startTime = System.currentTimeMillis();
		}

		public boolean needsUpdate() {
			return (System.currentTimeMillis() - startTime) < 5000;
		}

		public void update(Player... player) {
			if (location == null) {
				return;
			}
			location.add(v);

			// Add a random offset to the Y-axis for a more natural and random upward movement
			double yOffset = (RandomUtils.getRandomDouble() - 0.5) * 0.1; // Adjust the 0.1 for the desired randomness
			location.add(0, yOffset, 0);

			if (player == null || player.length == 0) {
				location.getWorld().spawnParticle(data.getParticle(), location, data.getAmount(), data.getOffset().getX(), data.getOffset().getY(), data.getOffset().getZ(), 0.02f);
			} else {
				for (int i = 0; i < player.length; i++) {
					player[i].spawnParticle(data.getParticle(), location, data.getAmount(), data.getOffset().getX(), data.getOffset().getY(), data.getOffset().getZ(), 0.02f);
				}
			}
		}

		public void onStop() {
			ObjectPoolManager.getDynamicLocationPool().release(location);
			ObjectPoolManager.getVectorPool().release(v);
		}
	}

	@Override
	public void initParticle(ParticleData data) {
		this.data.setParticle(data.getParticle());
		this.data.setAmount(data.getAmount());
		this.data.setOffset(data.getOffset());
		if (data instanceof DirectionParticleData) {
			this.data.setDirection(((DirectionParticleData) data).getDirection());
			this.data.setSpeed(((DirectionParticleData) data).getSpeed());
		}
	}
}
