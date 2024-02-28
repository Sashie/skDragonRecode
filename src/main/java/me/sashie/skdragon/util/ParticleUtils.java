package me.sashie.skdragon.util;

import me.sashie.skdragon.SkDragonRecode;
import me.sashie.skdragon.effects.ParticleEffect;
import me.sashie.skdragon.particles.*;
import me.sashie.skdragon.particles.data.*;
import me.sashie.skdragon.runnable.ParticleRunnable;
import me.sashie.skdragon.runnable.RunnableType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Vibration;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import me.sashie.skdragon.debug.SkriptNode;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.runnable.CounterRunnable;
import me.sashie.skdragon.runnable.EffectRunnable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ParticleUtils {

	/**
	 * Updates the colors of ColoredParticleData in the provided EffectData.
	 * This method advances the color index for particles with multiple colors.
	 *
	 * @param data The EffectData to update.
	 */
	public static void updateColoredParticles(EffectData data) {
		for (ParticleBuilder<?> builder : data.getParticleBuilders()) {
			updateColoredParticles(builder);
		}
	}

	public static void updateColoredParticles(ParticleBuilder<?> builder) {
		ParticleData particleData = builder.getParticleData();
		if (particleData instanceof ColoredParticleData) {
			ColoredParticleData colorData = (ColoredParticleData) particleData;
			if (colorData.colors != null && colorData.colors.size() > 1) {
				colorData.colors.next();
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

	public static ParticleBuilder createParticle(Particle particle, ParticleData data) {
		ParticleBuilder<?> p = ParticleUtils.createParticle(particle);
		p.initParticle(data);
		p.getParticleData().setParticle(particle);
		return p;
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

	public static ParticleBuilder<?>[] isSupported(ParticleData[] data, SkriptNode skriptNode) {
		ParticleBuilder<?>[] out = new ParticleBuilder[data.length];
		for (int i = 0; i < data.length; i++) {
			if (!Utils.isValidEnum(Particle.class, data[i].getParticle().toString())) {
				SkDragonRecode.error("The particle '" + data[i] + "' is not supported by your version of bukkit/spigot", skriptNode);
				return null;
			}
			out[i] = createParticle(data[i]);
		}
		return out;
	}

	public static boolean isAirBlock(Block block) {
		return block.getType() == Material.AIR;
	}

	public static void sendTimedParticles(ParticleBuilder<?> builder, DynamicLocation location, Player[] player, long delay, int iterations, long ticks) {
		new CounterRunnable() {
			@Override
			public int runCounter(int iteration) {
				builder.sendParticles(location, player);
				ParticleUtils.updateColoredParticles(builder);
				return iterations;
			}
		}.runTaskTimerAsynchronously(delay, ticks);
	}

	public static void sendTimedEffectParticles(ParticleBuilder<?> builder, ParticleEffect effect, Location location, Player[] player, long duration, int iterations, long ticks) {
		new EffectRunnable(effect.getEffectData(), duration, iterations).runTaskTimerAsynchronously(0, ticks);
	}

	public static void sendEffectParticles(ParticleBuilder<?> builder, ParticleEffect effect, Location location, Player[] player, long duration, int iterations) {
		new EffectRunnable(effect.getEffectData(), duration, iterations).runTaskAsynchronously();
	}

	public static void sendTimedParticles(ParticleBuilder<?> builder, DynamicLocation[] locations, Vector displacement, Player[] player, RunnableType runType, long delay, long duration, int iterations, long ticks) {
		ParticleRunnable runnable = new ParticleRunnable(builder, locations, displacement, player, duration, iterations);

		switch (runType) {
			case INSTANT:
				runnable.runTaskAsynchronously();
				break;
			case DELAYED:
				runnable.runTaskLaterAsynchronously(delay);
				break;
			case REPEATING:
				runnable.runTaskTimerAsynchronously(delay, ticks);
				break;
		}
	}

	public static void sendParticle(DynamicLocation location, Player[] player, ParticleData data, ParticleEffect effect, long duration, int iterations, long ticks) {
		ParticleBuilder<?> builder = createParticle(data);
		if (effect != null) {
			sendTimedEffectParticles(builder, effect, location, player, duration, iterations, ticks);
		} else {
			builder.sendParticles(location, player);
		}
	}

}
