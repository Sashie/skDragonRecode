package me.sashie.skdragon.api.particles;

import java.util.function.Consumer;

import me.sashie.skdragon.api.particles.data.ParticleData;
import me.sashie.skdragon.api.util.DynamicLocation;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import me.sashie.skdragon.api.particles.data.FadeParticleData;
import me.sashie.skdragon.api.util.ParticleUtils;

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
		Color c = data.colors.get();
		Particle.DustOptions dustOptions = new Particle.DustOptions(c, 1);

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

	@Override
	public void initParticle(ParticleData data) {
		this.data.setParticle(data.getParticle());
		this.data.setAmount(data.getAmount());
		this.data.setOffset(data.getOffset());
		if (data instanceof FadeParticleData) {
			this.data.colors = ((FadeParticleData) data).colors;
			this.data.fadeColors = ((FadeParticleData) data).fadeColors;
		}
	}
}
