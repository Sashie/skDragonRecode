package me.sashie.skdragon.api.particles;

import me.sashie.skdragon.api.util.DynamicLocation;
import org.bukkit.entity.Player;

import me.sashie.skdragon.api.particles.data.ParticleData;

public abstract class ParticleBuilder<T extends ParticleData> {

	protected T data;

	public ParticleBuilder(T data) {
		this.data = data;
	}

	public T getParticleData() {
		return this.data;
	}

	public abstract void sendParticles(DynamicLocation location, Player... player);

	public abstract void initParticle(ParticleData data);
}
