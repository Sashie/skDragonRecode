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

	public ColoredParticle() {
		super.initData(new ColoredParticleData(this));
	}

	public ColoredParticle(Particle particle) {
		this();
		this.data.setParticle(particle);
	}

	public ColoredParticle(ColoredParticleData inputData) {
		super.initData(inputData);
	}

	public ColoredParticle(ColoredParticleData inputData, Consumer<ColoredParticleData> data) {
		super(inputData);
		data.accept(this.data);
	}

	@Override
	public void sendParticles(DynamicLocation location, Player... player) {
		if (data.getParticle() == ParticleUtils.REDSTONE) {
			Color c = data.getColors().get();
			Particle.DustOptions dustOptions = new Particle.DustOptions(c, 1);

			if (player == null || player.length == 0) {
				for (int i = 0; i < data.getAmount(); i++) {
					location.getWorld().spawnParticle(ParticleUtils.REDSTONE, ParticleUtils.getOffsetLocation(this.data, location), 0, dustOptions);
				}
			} else {
				for (int i = 0; i < player.length; i++) {
					for (int j = 0; j < data.getAmount(); j++) {
						// player[i].spawnParticle(ParticleUtils.REDSTONE, ParticleUtils.getOffsetLocation(this.data, location), 0, dustOptions);
						player[i].spawnParticle(ParticleUtils.REDSTONE, ParticleUtils.getOffsetLocation(this.data, location), 0, 0, 0, 0, 0, dustOptions, true);
					}
				}
			}
		} else if (data.getParticle() == ParticleUtils.SPELL_MOB_AMBIENT || data.getParticle() == ParticleUtils.SPELL_MOB) {
			sendLegacyParticles(location, player);
		}
	}

	private void sendLegacyParticles(Location location, Player... player) {
		Color c = data.getColors().get();
		double red = c.getRed() / 255D;
		double green = c.getGreen() / 255D;
		double blue = c.getBlue() / 255D;
		if (red < Float.MIN_NORMAL) {
			red = Float.MIN_NORMAL;
		}

		if (player == null || player.length == 0) {
			location.getWorld().spawnParticle(data.getParticle(), ParticleUtils.getOffsetLocation(this.data, location), 0, red, green, blue, 1);
		} else {
			for (int j = 0; j < player.length; j++) {
				for (int i = 0; i < this.data.getAmount(); i++) {
					player[j].spawnParticle(data.getParticle(), ParticleUtils.getOffsetLocation(this.data, location), 0, red, green, blue, 1);
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
			this.data.setColors(((ColoredParticleData) data).getColors());
		}
	}
}
