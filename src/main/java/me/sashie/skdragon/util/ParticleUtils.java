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

	private static Class<?> dustOptions;
	private static Class<?> dustTransition;
	private static Class<?> vibration;

	static {
        try {
			dustOptions = Class.forName("org.bukkit.Particle$DustOptions");
			dustTransition = Class.forName("org.bukkit.Particle$DustTransition");
			vibration = Class.forName("org.bukkit.Vibration");
        } catch (ClassNotFoundException e) {
            // ignore
        }
    }

	public static Particle REDSTONE = compatibleParticle("REDSTONE", "DUST");
	public static Particle SMOKE_LARGE = compatibleParticle("SMOKE_LARGE", "LARGE_SMOKE");
	public static Particle SMOKE_NORMAL = compatibleParticle("SMOKE_NORMAL", "SMOKE");
	public static Particle SPELL_MOB = compatibleParticle("SPELL_MOB", "ENTITY_EFFECT");
	public static Particle SPELL_MOB_AMBIENT = Utils.getEnumValue(Particle.class, "SPELL_MOB_AMBIENT");
	public static Particle ITEM_CRACK = compatibleParticle("ITEM_CRACK", "ITEM");
	public static Particle BLOCK_CRACK = compatibleParticle("BLOCK_CRACK", "BLOCK");
	public static Particle BLOCK_DUST = Utils.getEnumValue(Particle.class, "BLOCK_DUST");

	public static boolean isSameParticle(Particle particle1, Particle particle2) {
		return particle1 == particle2;
	}

	private static Particle compatibleParticle(String oldName, String newName) {
		Particle particle = Utils.getEnumValue(Particle.class, oldName);
		if (particle == null) {
			particle = Utils.getEnumValue(Particle.class, newName);
		}
		return particle;
	}

	/**
	 * Updates the colors of ColoredParticleData or FadeParticleData in the provided EffectData.
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
		if (builder == null) return;
		ParticleData<?> particleData = builder.getParticleData();
		if (particleData instanceof ColoredParticleData) {
			ColoredParticleData colorData = (ColoredParticleData) particleData;
			if (colorData.getColors() != null && colorData.getColors().size() > 1) {
				colorData.getColors().next();
			}
		} else if (particleData instanceof FadeParticleData) {
			FadeParticleData colorData = (FadeParticleData) particleData;
			if (colorData.getColors() != null && colorData.getColors().size() > 1) {
				colorData.getColors().next();
				colorData.getFadeColors().next();
			}
		}
	}

	public static <T extends ParticleData<?>, R extends ParticleBuilder<T>> R createParticle(T data) {
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

	public static ParticleBuilder<?> createParticle(Particle particle) {
		Class<?> dataType = particle.getDataType();
		if (dataType == ItemStack.class) {
			return new MaterialParticle(particle);
		} else if (dustOptions != null && dataType == dustOptions) {
			return new ColoredParticle(particle);
		} else if (dataType == BlockData.class) {
			return new MaterialParticle(particle);
		} else if (dustTransition != null && dataType == dustTransition) {
			return new ColoredFadeParticle(particle);
		} else if (vibration != null && dataType == vibration) {
			return new VibrationParticle(particle);
		} else if (ParticleProperty.DIRECTIONAL.hasProperty(particle)) {
			return new DirectionParticle(particle);
		}
		return new NormalParticle(particle);
	}

	public static ParticleBuilder<?> createParticle(Particle particle, ParticleData<?> data) {
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
					finalParticles[i] = new ColoredParticle(REDSTONE);
				}
			}

			data.setDefaultParticles(finalParticles);
		}
	}

	public static Location getOffsetLocation(ParticleData<?> data, Location location) {
		if (data.getOffset().getX() != 0 || data.getOffset().getY() != 0 || data.getOffset().getZ() != 0) {
			return location.clone().add(
					RandomUtils.randomRangeDouble(-data.getOffset().getX(), data.getOffset().getX()),
					RandomUtils.randomRangeDouble(-data.getOffset().getY(), data.getOffset().getY()),
					RandomUtils.randomRangeDouble(-data.getOffset().getZ(), data.getOffset().getZ()));
		}
		return location;
	}

	public static ParticleBuilder<?>[] isSupported(ParticleData<?>[] data, SkriptNode skriptNode) {
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

	public static void sendParticle(DynamicLocation location, Player[] player, ParticleData<?> data, ParticleEffect effect, long duration, int iterations, long ticks) {
		ParticleBuilder<?> builder = createParticle(data);
		if (effect != null) {
			sendTimedEffectParticles(builder, effect, location, player, duration, iterations, ticks);
		} else {
			builder.sendParticles(location, player);
		}
	}

}
