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
		super(new DirectionParticleData());
	}

    public DirectionParticle(Particle particle) {
    	super(new DirectionParticleData());
    	this.data.particle = particle;
	}

	public DirectionParticle(DirectionParticleData inputData) {
		super(inputData);
	}

	public DirectionParticle(Consumer<DirectionParticleData> data) {
		this(new DirectionParticleData(), data);
	}

	public DirectionParticle(DirectionParticleData inputData, Consumer<DirectionParticleData> data) {
		super(inputData);
		data.accept(this.data);
	}

	@Override
	public void sendParticles(DynamicLocation location, Player... player) {
		if (ParticleProperty.DIRECTIONAL.hasProperty(this.data.getParticle())) {
			if (player == null || player.length == 0) {
				//location.getWorld().spawnParticle(this.data.particle, ParticleUtils.getOffsetLocation(this.data, location), 0, this.data.direction.getX(), this.data.direction.getY(), this.data.direction.getZ(), this.data.speed);
				for (int i = 0; i < data.amount; i++) {
					location.getWorld().spawnParticle(this.data.particle, ParticleUtils.getOffsetLocation(this.data, location), 0, this.data.direction.getX(), this.data.direction.getY(), this.data.direction.getZ(), this.data.speed, null);
				}
			} else {
				for (int j = 0; j < player.length; j++) {
					for (int i = 0; i < this.data.amount; i++) {
						player[j].spawnParticle(this.data.particle, ParticleUtils.getOffsetLocation(this.data, location), 0, this.data.direction.getX(), this.data.direction.getY(), this.data.direction.getZ(), this.data.speed);
					}
				}
			}
		} else {
			directionals.add(new DirectionAnimation(location, this.data.direction, this.data.speed));

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
				location.getWorld().spawnParticle(data.particle, location, data.amount, data.offset.getX(), data.offset.getY(), data.offset.getZ(), 0.02f);
			} else {
				for (int i = 0; i < player.length; i++) {
					player[i].spawnParticle(data.particle, location, data.amount, data.offset.getX(), data.offset.getY(), data.offset.getZ(), 0.02f);
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
			this.data.direction = ((DirectionParticleData) data).direction;
			this.data.speed = ((DirectionParticleData) data).speed;
		}
	}
}
