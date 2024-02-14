package me.sashie.skdragon.util;

import ch.njol.skript.Skript;
import ch.njol.util.StringUtils;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Particle.DustTransition;
import org.bukkit.Registry;
import org.bukkit.Vibration;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.reflect.Field;
import java.util.*;

/*
 * Thanks to ShaneBee at SkBee for the original code.
 */
public class ParticleUtil {

	private static final Map<String, Particle> PARTICLES = new HashMap<>();
	private static final Map<Particle, String> PARTICLE_NAMES = new HashMap<>();

	/*
	 * Load and map Minecraft particle names
	 * Bukkit does not have any API for getting the Minecraft names of particles (how stupid)
	 * This method fetches them from the server and maps them with the Bukkit particle enums
	 */
	static {
		// Added in Spigot 1.20.2 (Oct 20/2023)
		if (Skript.methodExists(Particle.class, "getKey")) {
			Registry.PARTICLE_TYPE.forEach(particle -> {
				String key = particle.getKey().getKey();
				PARTICLES.put(key, particle);
				PARTICLE_NAMES.put(particle, key);
			});
		} else {
			// Load and map Minecraft particle names
			// Prior to 1.20.2, Bukkit does not have any API for getting the Minecraft names of particles (how stupid)
			// This method fetches them from the server and maps them with the Bukkit particle enums
			try {
				@Nullable Class<?> cbParticle = ReflectionUtils.PackageType.CRAFTBUKKIT.getClass("CraftParticle");
				assert cbParticle != null;
				Field bukkitParticleField = cbParticle.getDeclaredField("bukkit");
				bukkitParticleField.setAccessible(true);
				Field mcKeyField = cbParticle.getDeclaredField("minecraftKey");
				mcKeyField.setAccessible(true);

				for (Object enumConstant : cbParticle.getEnumConstants()) {
					String mcKey = mcKeyField.get(enumConstant).toString().replace("minecraft:", "");
					Particle bukkitParticle = (Particle) bukkitParticleField.get(enumConstant);

					if (!bukkitParticle.toString().contains("LEGACY")) {
						PARTICLES.put(mcKey, bukkitParticle);
						PARTICLE_NAMES.put(bukkitParticle, mcKey);
					}
				}
			} catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns a string for docs of all names of particles
	 *
	 * @return Names of all particles in one long string
	 */
	public static String getNamesAsString() {
		List<String> names = new ArrayList<>();
		PARTICLES.forEach((s, particle) -> {
			String name = s;

			if (particle.getDataType() != Void.class) {
				name = name + " [" + getDataType(particle) + "]";
			}
			names.add(name);
		});
		Collections.sort(names);
		return StringUtils.join(names, ", ");
	}

	/**
	 * Get the Minecraft name of a particle
	 *
	 * @param particle Particle to get name of
	 * @return Minecraft name of particle
	 */
	public static String getName(Particle particle) {
		return PARTICLE_NAMES.get(particle);
	}

	/**
	 * Get a list of all available particles
	 *
	 * @return List of all available particles
	 */
	public static List<Particle> getAvailableParticles() {
		return new ArrayList<>(PARTICLES.values());
	}

	public static String[] getAllNames() {
		return PARTICLES.keySet().toArray(new String[PARTICLES.size()]);
	}

	/**
	 * Parse a particle by its Minecraft name
	 *
	 * @param key Minecraft name of particle
	 * @return Bukkit particle from Minecraft name (null if not available)
	 */
	@Nullable
	public static Particle parse(String key) {
		if (PARTICLES.containsKey(key)) {
			return PARTICLES.get(key);
		}
		return null;
	}

	private static String getDataType(Particle particle) {
		Class<?> dataType = particle.getDataType();
		if (dataType == ItemStack.class) {
			return "itemtype";
		} else if (dataType == DustOptions.class) {
			return "dust-option";
		} else if (dataType == BlockData.class) {
			return "blockdata/itemtype";
		} else if (dataType == DustTransition.class) {
			return "dust-transition";
		} else if (dataType == Vibration.class) {
			return "vibration";
		} else if (dataType == Integer.class) {
			return "number(int)";
		} else if (dataType == Float.class) {
			return "number(float)";
		}
		// For future particle data additions that haven't been added here yet
		return "UNKNOWN";
	}

}