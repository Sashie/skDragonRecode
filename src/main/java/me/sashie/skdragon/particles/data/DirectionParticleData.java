package me.sashie.skdragon.particles.data;

import me.sashie.skdragon.particles.DirectionParticle;
import org.bukkit.util.Vector;

public class DirectionParticleData extends ParticleData<DirectionParticle> {

	private double speed = 0;
	private Vector direction = new Vector(0, 0, 0);

	public DirectionParticleData(DirectionParticle particle) {
		super(particle);
	}

	public double getSpeed() {
		return speed;
	}

	public DirectionParticle setSpeed(double speed) {
		this.speed = speed;
		return returnType;
	}

	public Vector getDirection() {
		return direction;
	}

	public DirectionParticle setDirection(Vector direction) {
		this.direction = direction;
		return returnType;
	}
}
