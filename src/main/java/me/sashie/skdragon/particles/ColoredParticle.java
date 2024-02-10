package me.sashie.skdragon.particles;

import me.sashie.skdragon.particles.data.ColoredParticleData;
import me.sashie.skdragon.particles.data.ParticleData;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.ParticleUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class ColoredParticle extends ParticleBuilder<ColoredParticleData> {

	private Color c;

	public ColoredParticle() {
		super(new ColoredParticleData());
	}

	public ColoredParticle(Particle particle) {
		super(new ColoredParticleData());
		this.data.particle = particle;
	}

	public ColoredParticle(ColoredParticleData inputData) {
		super(inputData);
	}

	public ColoredParticle(Consumer<ColoredParticleData> data) {
		this(new ColoredParticleData(), data);
	}

	public ColoredParticle(ColoredParticleData inputData, Consumer<ColoredParticleData> data) {
		super(inputData);
		data.accept(this.data);
	}

	@Override
	public void sendParticles(DynamicLocation location, Player... player) {
		if (data.particle == Particle.REDSTONE) {
			c = data.colors.get();
			Particle.DustOptions dustOptions = new Particle.DustOptions(c, 1);

			if (player == null || player.length == 0) {
				for (int i = 0; i < data.amount; i++) {
					location.getWorld().spawnParticle(Particle.REDSTONE, ParticleUtils.getOffsetLocation(this.data, location), 0, dustOptions);
				}
			} else {
				for (int i = 0; i < player.length; i++) {
					for (int j = 0; j < data.amount; j++) {
						player[i].spawnParticle(Particle.REDSTONE, ParticleUtils.getOffsetLocation(this.data, location), 0, dustOptions);
					}
				}
			}
		} else if (data.particle == Particle.SPELL_MOB_AMBIENT || data.particle == Particle.SPELL_MOB) {
			sendLegacyParticles(location, player);
		}
	}

	private void sendLegacyParticles(Location location, Player... player) {
		c = data.colors.get();
		double red = c.getRed() / 255D;
		double green = c.getGreen() / 255D;
		double blue = c.getBlue() / 255D;
		if (red < Float.MIN_NORMAL) {
			red = Float.MIN_NORMAL;
		}

		if (player == null || player.length == 0) {
			location.getWorld().spawnParticle(data.particle, ParticleUtils.getOffsetLocation(this.data, location), 0, red, green, blue, 1);
		} else {
			for (int j = 0; j < player.length; j++) {
				for (int i = 0; i < this.data.amount; i++) {
					player[j].spawnParticle(data.particle, ParticleUtils.getOffsetLocation(this.data, location), 0, red, green, blue, 1);
				}
			}
		}
	}

	@Override
	public void initParticle(ParticleData data) {
		this.data.setParticle(data.getParticle());
		this.data.setAmount(data.getAmount());
		this.data.setOffset(data.getOffset());
		if (data instanceof ColoredParticleData) {
			this.data.colors = ((ColoredParticleData) data).colors;
		}
	}
}
