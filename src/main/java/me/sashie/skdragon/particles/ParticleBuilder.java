package me.sashie.skdragon.particles;

import me.sashie.skdragon.util.DynamicLocation;
import org.bukkit.entity.Player;

import me.sashie.skdragon.particles.data.ParticleData;

public abstract class ParticleBuilder<T extends ParticleData> {

	protected T data;

	public ParticleBuilder() {}

	public ParticleBuilder(T data) {
		this.data = data;
	}

	protected void initData(T data) {
		this.data = data;
	}

	public T getParticleData() {
		return this.data;
	}

	public abstract void sendParticles(DynamicLocation location, Player... player);

	public abstract void initParticle(ParticleData data);
}
