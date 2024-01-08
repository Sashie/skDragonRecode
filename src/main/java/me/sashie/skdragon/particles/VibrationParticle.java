package me.sashie.skdragon.particles;

import java.util.function.Consumer;

import me.sashie.skdragon.particles.data.ParticleData;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Vibration;
import org.bukkit.entity.Player;

import me.sashie.skdragon.particles.data.VibrationParticleData;
import me.sashie.skdragon.util.DynamicLocation;

public class VibrationParticle extends ParticleBuilder<VibrationParticleData> {

	public VibrationParticle() {
		super(new VibrationParticleData());
	}

    public VibrationParticle(Particle particle) {
    	super(new VibrationParticleData());
    	this.data.particle = particle;
	}

	public VibrationParticle(VibrationParticleData inputData) {
		super(inputData);
	}

	public VibrationParticle(Consumer<VibrationParticleData> data) {
		this(new VibrationParticleData(), data);
	}

	public VibrationParticle(VibrationParticleData inputData, Consumer<VibrationParticleData> data) {
		super(inputData);
		data.accept(this.data);
	}

	@Override
	public void sendParticles(DynamicLocation location, Player... player) {
		Vibration vibration = null;

		if (location.isDynamic()) {
			vibration = new Vibration(location, new Vibration.Destination.EntityDestination(location.getEntity()), this.data.arrivalTime);
		} else {
			vibration = new Vibration(location, new Vibration.Destination.BlockDestination(this.data.block), this.data.arrivalTime);
		}

		// https://www.spigotmc.org/threads/comprehensive-particle-spawning-guide-1-13-1-17.343001/
		if (player == null || player.length == 0) {
			location.getWorld().spawnParticle(Particle.VIBRATION, location, 1, vibration);
		} else {
			for (int i = 0; i < player.length; i++) {
				player[i].spawnParticle(Particle.VIBRATION, location, 1, vibration);
			}
		}
	}
}
