package me.sashie.skdragon.particles.data;

import me.sashie.skdragon.particles.NormalParticle;

public class NormalParticleData extends ParticleData<NormalParticle> {

	private double speed = 0;

	public NormalParticleData(NormalParticle particle) {
		super(particle);
	}

	public double getSpeed() {
		return speed;
	}

	public NormalParticle setSpeed(double speed) {
		this.speed = speed;
		return returnType;
	}
}
