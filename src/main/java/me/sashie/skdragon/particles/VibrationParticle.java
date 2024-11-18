package me.sashie.skdragon.particles;

import java.util.function.Consumer;

import me.sashie.skdragon.particles.data.ParticleData;
import org.bukkit.Particle;
import org.bukkit.Vibration;
import org.bukkit.entity.Player;

import me.sashie.skdragon.particles.data.VibrationParticleData;
import me.sashie.skdragon.util.DynamicLocation;

public class VibrationParticle extends ParticleBuilder<VibrationParticleData> {

	public VibrationParticle() {
		super.initData(new VibrationParticleData(this));
	}

	public VibrationParticle(Particle particle) {
		this();
		this.data.setParticle(particle);
	}

	public VibrationParticle(VibrationParticleData inputData) {
		super.initData(inputData);
	}

	public VibrationParticle(VibrationParticleData inputData, Consumer<VibrationParticleData> data) {
		super(inputData);
		data.accept(this.data);
	}

	@Override
	public void sendParticles(DynamicLocation location, Player... player) {
		Vibration vibration = null;

		if (location.isDynamic()) {
			vibration = new Vibration(location, new Vibration.Destination.EntityDestination(location.getEntity()), this.data.getArrivalTime());
		} else {
			vibration = new Vibration(location, new Vibration.Destination.BlockDestination(this.data.getBlock()), this.data.getArrivalTime());
		}

		// https://www.spigotmc.org/threads/comprehensive-particle-spawning-guide-1-13-1-17.343001/
		if (player == null || player.length == 0) {
			for (int i = 0; i < data.getAmount(); i++) {
				location.getWorld().spawnParticle(Particle.VIBRATION, location, 1, vibration);
			}
		} else {
			for (int i = 0; i < player.length; i++) {
				for (int j = 0; j < data.getAmount(); j++) {
					player[i].spawnParticle(Particle.VIBRATION, location, 1, vibration);
				}
			}
		}
	}

	@Override
	public void initParticle(ParticleData data) {
		this.data.setParticle(data.getParticle());
		this.data.setAmount(data.getAmount());
		this.data.setOffset(data.getOffset());
		if (data instanceof VibrationParticleData) {
			this.data.setArrivalTime(((VibrationParticleData) data).getArrivalTime());
			this.data.setDirection(((VibrationParticleData) data).getDirection());
			this.data.setBlock(((VibrationParticleData) data).getBlock());
			this.data.setEntity(((VibrationParticleData) data).getEntity());
			this.data.setTarget(((VibrationParticleData) data).getTarget());
			this.data.setSpeed(((VibrationParticleData) data).getSpeed());
		}
	}
}
