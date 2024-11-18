package me.sashie.skdragon.particles.data;

import me.sashie.skdragon.particles.VibrationParticle;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class VibrationParticleData extends ParticleData<VibrationParticle> {

	private double speed = 0;
	private Vector direction = new Vector(0, 0, 0);
	private int arrivalTime = 0;
	private Location target;
	private Block block;
	private Entity entity;

	public VibrationParticleData(VibrationParticle particle) {
		super(particle);
	}

	public double getSpeed() {
		return speed;
	}

	public VibrationParticle setSpeed(double speed) {
		this.speed = speed;
		return returnType;
	}

	public Vector getDirection() {
		return direction;
	}

	public VibrationParticle setDirection(Vector direction) {
		this.direction = direction;
		return returnType;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public VibrationParticle setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
		return returnType;
	}

	public Location getTarget() {
		return target;
	}

	public VibrationParticle setTarget(Location target) {
		this.target = target;
		return returnType;
	}

	public Block getBlock() {
		return block;
	}

	public VibrationParticle setBlock(Block block) {
		this.block = block;
		return returnType;
	}

	public Entity getEntity() {
		return entity;
	}

	public VibrationParticle setEntity(Entity entity) {
		this.entity = entity;
		return returnType;
	}
}
