package me.sashie.skdragon.particles.data;

import org.bukkit.Particle;
import org.bukkit.util.Vector;

public class ParticleData<R> {

	protected R returnType;

	private Particle particle;
	private int amount = 1;
	private Vector offset = new Vector(0, 0, 0);

	public ParticleData(R particle) {
		this.returnType = particle;
	}

	public Particle getParticle() {
		return particle;
	}

	public R setParticle(Particle particle) {
		this.particle = particle;
		return returnType;
	}

	public int getAmount() {
		return amount;
	}

	public R setAmount(int amount) {
		this.amount = amount;
		return returnType;
	}

	public Vector getOffset() {
		return offset;
	}

	public R setOffset(Vector offset) {
		this.offset = offset;
		return returnType;
	}

	public R setOffset(double x, double y, double z) {
		this.offset.setX(x).setY(y).setZ(z);
		return returnType;
	}

}
