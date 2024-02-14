package me.sashie.skdragon.project.particles;

import me.sashie.skdragon.project.util.Utils;
import org.bukkit.Particle;

import java.util.*;

public enum ParticleProperty {
	/**
	 * The particle effect requires water to be displayed
	 */
	USES_WATER,
	/**
	 * The particle effect requires block or item data to be displayed
	 */
	REQUIRES_DATA,
	/**
	 * The particle effect uses the offsets as direction values
	 */
	DIRECTIONAL,
	/**
	 * The particle effect uses the offsets as color values
	 */
	COLORABLE,

	/**
	 * The particle effect uses Particle.DustTransition as DataType
	 */
	COLOR_TRANSITION,

	/**
	 * Particle that uses Vibration
	 */
	VIBRATION;

	private static final Map<ParticleProperty, Set<Particle>> propertyToParticles = new EnumMap<>(ParticleProperty.class);

	static {

		List<String> waterParticles = List.of("BUBBLE_COLUMN_UP", "BUBBLE_POP", "CURRENT_DOWN", "WATER_BUBBLE", "WATER_SPLASH", "WATER_WAKE");
		for (String particleName : waterParticles) {
			Particle particle = Utils.getEnumValue(Particle.class, particleName);
			if (particle != null) {
				propertyToParticles.computeIfAbsent(ParticleProperty.USES_WATER, key -> EnumSet.noneOf(Particle.class)).add(particle);
			}
		}

		List<String> dataParticles = List.of("BLOCK_CRACK", "BLOCK_DUST", "BLOCK_MARKER", "FALLING_DUST", "ITEM_CRACK", "LEGACY_BLOCK_CRACK", "LEGACY_BLOCK_DUST", "LEGACY_FALLING_DUST");
		for (String particleName : dataParticles) {
			Particle particle = Utils.getEnumValue(Particle.class, particleName);
			if (particle != null) {
				propertyToParticles.computeIfAbsent(ParticleProperty.REQUIRES_DATA, key -> EnumSet.noneOf(Particle.class)).add(particle);
			}
		}

		List<String> directionParticles = List.of("EXPLOSION_NORMAL", "FIREWORKS_SPARK", "WATER_WAKE", "CRIT", "CRIT_MAGIC", "SMOKE_NORMAL", "SMOKE_LARGE", "SPELL", "SPELL_INSTANT", "SPELL_MOB", "SPELL_MOB_AMBIENT", "SPELL_WITCH", "VILLAGER_HAPPY", "TOWN_AURA", "PORTAL", "ENCHANTMENT_TABLE", "FLAME", "CLOUD", "ITEM_CRACK", "BLOCK_DUST", "DRAGON_BREATH", "END_ROD", "DAMAGE_INDICATOR", "TOTEM", "SPIT", "SQUID_INK", "BUBBLE_POP", "NAUTILUS", "DOLPHIN", "SNEEZE", "CAMPFIRE_COSY_SMOKE", "CAMPFIRE_SIGNAL_SMOKE", "SOUL_FIRE_FLAME", "SOUL", "REVERSE_PORTAL", "VIBRATION", "SMALL_FLAME", "SNOWFLAKE", "GLOW_SQUID_INK", "GLOW", "WAX_ON", "WAX_OFF", "ELECTRIC_SPARK", "SCRAPE", "LEGACY_BLOCK_DUST");
		for (String particleName : directionParticles ) {
			Particle particle = Utils.getEnumValue(Particle.class, particleName);
			if (particle != null) {
				propertyToParticles.computeIfAbsent(ParticleProperty.DIRECTIONAL, key -> EnumSet.noneOf(Particle.class)).add(particle);
			}
		}

		List<String> colorParticles = List.of("NOTE", "REDSTONE", "SPELL_MOB", "SPELL_MOB_AMBIENT", "DUST_COLOR_TRANSITION");
		for (String particleName : colorParticles) {
			Particle particle = Utils.getEnumValue(Particle.class, particleName);
			if (particle != null) {
				propertyToParticles.computeIfAbsent(ParticleProperty.COLORABLE, key -> EnumSet.noneOf(Particle.class)).add(particle);
			}
		}

		Particle transitionParticle = Utils.getEnumValue(Particle.class, "DUST_COLOR_TRANSITION");
		if (transitionParticle != null) {
			propertyToParticles.computeIfAbsent(ParticleProperty.COLOR_TRANSITION, key -> EnumSet.noneOf(Particle.class)).add(transitionParticle);
		}

		Particle vibrationParticle = Utils.getEnumValue(Particle.class, "VIBRATION");
		if (vibrationParticle != null) {
			propertyToParticles.computeIfAbsent(ParticleProperty.VIBRATION, key -> EnumSet.noneOf(Particle.class)).add(vibrationParticle);
		}

	}

	public static Set<Particle> getParticlesByProperty(ParticleProperty property) {
		return propertyToParticles.getOrDefault(property, EnumSet.noneOf(Particle.class));
	}

	public static List<ParticleProperty> getPropertiesForParticle(Particle particle) {
		List<ParticleProperty> properties = new ArrayList<>();

		for (ParticleProperty property : ParticleProperty.values()) {
			if (property.hasProperty(particle)) {
				properties.add(property);
			}
		}

		return properties;
	}

	public static boolean hasProperty(Particle particle, ParticleProperty property) {
		Set<Particle> particlesWithProperty = getParticlesByProperty(property);
		return particlesWithProperty.contains(particle);
	}

	public boolean hasProperty(Particle particle) {
		Set<Particle> particlesWithProperty = getParticlesByProperty(this);
		return particlesWithProperty.contains(particle);
	}

}