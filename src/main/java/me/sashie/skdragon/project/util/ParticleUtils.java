package me.sashie.skdragon.project.util;

import me.sashie.skdragon.project.debug.ParticleException;
import me.sashie.skdragon.project.debug.SkriptNode;
import me.sashie.skdragon.project.effects.EffectData;
import me.sashie.skdragon.project.effects.ParticleEffect;
import me.sashie.skdragon.project.particles.*;
import me.sashie.skdragon.project.particles.data.*;
import me.sashie.skdragon.project.runnable.CounterRunnable;
import me.sashie.skdragon.project.runnable.EffectRunnable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Vibration;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ParticleUtils {

	/**
	 * Updates the colors of ColoredParticleData in the provided EffectData.
	 * This method advances the color index for particles with multiple colors.
	 *
	 * @param data The EffectData to update.
	 */
	public static void updateColoredParticles(EffectData data) {
		for (ParticleBuilder<?> builder : data.getParticleBuilders()) {
			ParticleData particleData = builder.getParticleData();
			if (particleData instanceof ColoredParticleData) {
				ColoredParticleData colorData = (ColoredParticleData) particleData;
				if (colorData.colors != null && colorData.colors.size() > 1) {
					colorData.colors.next();
				}
			}
		}
	}

	public static <T extends ParticleData, R extends ParticleBuilder<T>> R createParticle(T data) {
		if (data instanceof MaterialParticleData) {
			return (R) new MaterialParticle((MaterialParticleData) data);
		} else if (data instanceof VibrationParticleData) {
			return (R) new VibrationParticle((VibrationParticleData) data);
		} else if (data instanceof DirectionParticleData) {
			return (R) new DirectionParticle((DirectionParticleData) data);
		} else if (data instanceof FadeParticleData) {
			return (R) new ColoredFadeParticle((FadeParticleData) data);
		} else if (data instanceof ColoredParticleData) {
			return (R) new ColoredParticle((ColoredParticleData) data);
		} else if (data instanceof NormalParticleData) {
			return (R) new NormalParticle((NormalParticleData) data);
		}

		throw new IllegalArgumentException("No particle type exists for that data object");
	}

	public static ParticleBuilder createParticle(Particle particle) {
		Class<?> dataType = particle.getDataType();
		if (dataType == ItemStack.class) {
			return new MaterialParticle();
		} else if (dataType == Particle.DustOptions.class) {
			return new ColoredParticle();
		} else if (dataType == BlockData.class) {
			return new MaterialParticle();
		} else if (dataType == Particle.DustTransition.class) {
			return new ColoredFadeParticle();
		} else if (dataType == Vibration.class) {
			return new VibrationParticle();
		} else if (ParticleProperty.DIRECTIONAL.hasProperty(particle)) {
			return new DirectionParticle();
		}
		return new NormalParticle();
	}

	public static void updateParticleAmount(EffectData data, int newSize) {
		if (newSize != data.getParticleBuilders().length) {
			ParticleBuilder<?>[] finalParticles = new ParticleBuilder<?>[newSize];

			for (int i = 0; i < newSize; i++) {
				if (i < data.getParticleBuilders().length) {
					finalParticles[i] = data.getParticleBuilders()[i];
				} else {
					finalParticles[i] = new ColoredParticle(Particle.REDSTONE);
				}
			}

			data.setDefaultParticles(finalParticles);
		}
	}

	public static Location getOffsetLocation(ParticleData data, Location location) {
		if (data.offset.getX() != 0 || data.offset.getY() != 0 || data.offset.getZ() != 0) {
			return location.clone().add(
					RandomUtils.randomRangeDouble(-data.offset.getX(), data.offset.getX()),
					RandomUtils.randomRangeDouble(-data.offset.getY(), data.offset.getY()),
					RandomUtils.randomRangeDouble(-data.offset.getZ(), data.offset.getZ()));
		}
		return location;
	}

	// Enum to specify the shape of the particles
	public enum SizeShape {
		ROUND,
		SQUARE
	}

	public static ParticleBuilder<?>[] isSupported(ParticleData[] data, SkriptNode skriptNode) throws ParticleException {
		ParticleBuilder<?>[] out = new ParticleBuilder[data.length];
		for (int i = 0; i < data.length; i++) {
			if (!Utils.isValidEnum(Particle.class, data[i].getParticle().toString()))
				throw new ParticleException("The particle '" + data[i] + "' is not supported by your version of bukkit/spigot", skriptNode);
			out[i] = createParticle(data[i]);
		}
		return out;
	}

	public static boolean isAirBlock(Block block) {
		return block.getType() == Material.AIR;
	}

	public static void sendTimedParticles(ParticleBuilder<?> builder, DynamicLocation location, Player[] player, int iterations, long ticks) {
		new CounterRunnable() {
			@Override
			public int runCounter(int iteration) {
				builder.sendParticles(location, player);
				return iterations;
			}
		}.runTaskTimerAsynchronously(0, ticks);
	}

	public static void sendTimedEffectParticles(ParticleBuilder<?> builder, ParticleEffect effect, Location location, Player[] player, int iterations, long ticks) {
		new EffectRunnable(effect.getEffectData(), iterations).runTaskTimerAsynchronously(0, ticks);
	}

	public static void sendEffectParticles(ParticleBuilder<?> builder, ParticleEffect effect, Location location, Player[] player, int iterations) {
		new EffectRunnable(effect.getEffectData(), iterations).runTaskAsynchronously();
	}

	public static void sendParticle(DynamicLocation location, Player[] player, ParticleData data, ParticleEffect effect, int iterations, long ticks) {
		ParticleBuilder<?> builder = createParticle(data);
		if (effect != null) {
			sendTimedEffectParticles(builder, effect, location, player, iterations, ticks);
		} else {
			builder.sendParticles(location, player);
		}
	}

}
