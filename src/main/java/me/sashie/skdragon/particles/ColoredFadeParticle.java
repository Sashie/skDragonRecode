package me.sashie.skdragon.particles;

import java.util.function.Consumer;

import me.sashie.skdragon.util.DynamicLocation;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import me.sashie.skdragon.particles.data.FadeParticleData;
import me.sashie.skdragon.util.ParticleUtils;

public class ColoredFadeParticle extends ParticleBuilder<FadeParticleData> {

	public ColoredFadeParticle() {
		super(new FadeParticleData());
	}

    public ColoredFadeParticle(Particle particle) {
    	super(new FadeParticleData());
    	this.data.particle = particle;
	}

	public ColoredFadeParticle(FadeParticleData inputData) {
		super(inputData);
	}

	public ColoredFadeParticle(Consumer<FadeParticleData> data) {
		this(new FadeParticleData(), data);
	}

	public ColoredFadeParticle(FadeParticleData inputData, Consumer<FadeParticleData> data) {
		super(inputData);
		data.accept(this.data);
	}

	@Override
	public void sendParticles(DynamicLocation location, Player... player) {
		java.awt.Color c = data.colors.get();
		Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(c.getRed(), c.getGreen(), c.getBlue()), 1);

		if (player == null || player.length == 0) {
			location.getWorld().spawnParticle(Particle.REDSTONE, ParticleUtils.getOffsetLocation(this.data, location), 0, dustOptions);
		} else {
			for (int j = 0; j < player.length; j++) {
				for (int i = 0; i < this.data.amount; i++) {
					player[j].spawnParticle(Particle.REDSTONE, ParticleUtils.getOffsetLocation(this.data, location), 0, dustOptions);
				}
			}
		}
	}
}
