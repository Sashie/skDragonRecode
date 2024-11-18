package me.sashie.skdragon.particles;

import java.util.function.Consumer;

import me.sashie.skdragon.particles.data.ParticleData;
import me.sashie.skdragon.util.DynamicLocation;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import me.sashie.skdragon.particles.data.FadeParticleData;
import me.sashie.skdragon.util.ParticleUtils;

public class ColoredFadeParticle extends ParticleBuilder<FadeParticleData> {

	public ColoredFadeParticle() {
		super.initData(new FadeParticleData(this));
	}

	public ColoredFadeParticle(Particle particle) {
		this();
		this.data.setParticle(particle);
	}

	public ColoredFadeParticle(FadeParticleData inputData) {
		super.initData(inputData);
	}

	public ColoredFadeParticle(FadeParticleData inputData, Consumer<FadeParticleData> data) {
		super(inputData);
		data.accept(this.data);
	}

	@Override
	public void sendParticles(DynamicLocation location, Player... player) {
		Color c = data.getColors().get();
		Particle.DustOptions dustOptions = new Particle.DustOptions(c, 1);

		if (player == null || player.length == 0) {
			location.getWorld().spawnParticle(ParticleUtils.REDSTONE, ParticleUtils.getOffsetLocation(this.data, location), 0, dustOptions);
		} else {
			for (int j = 0; j < player.length; j++) {
				for (int i = 0; i < this.data.getAmount(); i++) {
					player[j].spawnParticle(ParticleUtils.REDSTONE, ParticleUtils.getOffsetLocation(this.data, location), 0, dustOptions);
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
			this.data.setColors(((FadeParticleData) data).getColors());
			this.data.setFadeColors(((FadeParticleData) data).getFadeColors());
		}
	}
}
