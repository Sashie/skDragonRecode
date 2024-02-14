package me.sashie.skdragon.api.particles.data;

import org.bukkit.Particle;
import org.bukkit.util.Vector;

public class ParticleData {

	public Particle particle;
	public int amount = 1;
	public Vector offset = new Vector(0, 0, 0);

	public Particle getParticle() {
		return particle;
	}

	public void setParticle(Particle particle) {
		this.particle = particle;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Vector getOffset() {
		return offset;
	}

	public void setOffset(Vector offset) {
		this.offset = offset;
	}

}
