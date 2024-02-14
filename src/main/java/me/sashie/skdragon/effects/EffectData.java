package me.sashie.skdragon.effects;

import me.sashie.skdragon.SkDragonRecode;
import me.sashie.skdragon.debug.SkriptNode;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.particles.Value3d;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.EffectUtils;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public abstract class EffectData {

	protected Player[] players;
	protected ParticleBuilder<?>[] builders;
	protected DynamicLocation[] locations;
	protected double[][] startlocations;
	private final Value3d displacement = new Value3d();
	private boolean stopTriggered;

	public EffectData() {
		this.builders = defaultParticles();
	}

	public abstract void update(float iteration);

	/**
	 * If there are any Pools to release do them here
	 */
	public abstract void onUnregister();

	/**
	 * Every effect has a certain amount of particles, for instance a circle uses one particle and an atom uses two particles
	 * This method is here so that you don't forget to initialize particles for each effect <3
	 * Checking for the amount of particles per effect uses getParticleBuilder().length and can return null or zero
	 */
	public abstract ParticleBuilder<?>[] defaultParticles();

	/**
	 * Defines properties specific to this effect only
	 * Used to warn a user if an effect can use a certain property expression
	 *
	 * @return List of effect specific properties
	 */
	public abstract EffectProperty[] acceptProperties();

	/**
	 * Properties specific to each effect type
	 *
	 * @return List of default properties
	 */
	public abstract EffectProperty[] acceptDefaultProperties();

	public EffectProperty[] getEffectProperties() {
		return EffectUtils.combineArrays(acceptProperties(), acceptDefaultProperties());
	}

	public void setDefaultParticles(ParticleBuilder<?>... builders) {
		this.builders = builders;
	}

	/**
	 * defaultParticles() must be populated otherwise you can not set any particles for this effect
	 */
	public void setParticles(ParticleBuilder<?>[] builders, SkriptNode skriptNode) {
		synchronized (this) {
			if (this.builders == null) {
				SkDragonRecode.warn("This effect does not have any editable particles", skriptNode);
				return;
			}

			if (builders.length > this.builders.length) {
				SkDragonRecode.warn("This effect only uses " + this.builders.length + " particles not " + builders.length, skriptNode);
			} else {
				System.arraycopy(builders, 0, this.builders, 0, builders.length);
			}
		}
	}

	/**
	 * defaultParticles() must be populated otherwise you can not set any particles for this effect
	 */
	public void setParticle(int index, ParticleBuilder<?> builder, SkriptNode skriptNode) {
		synchronized (this) {
			if (this.builders == null) {
				SkDragonRecode.warn("This effect does not have any editable particles", skriptNode);
				return;
			}
			if (index > this.builders.length) {
				SkDragonRecode.warn("This effect only uses " + this.builders.length + " particles not " + index, skriptNode);
				return;
			}
			this.builders[index - 1] = builder;
		}
	}

	/**
	 * Gets a particle from 1 to whatever the effect uses
	 * <p>
	 * Returns null if particle doesn't exist
	 */
	public ParticleBuilder<?> getParticleBuilder(int index) {
		if (index > this.builders.length)
			return null;
		return this.builders[index - 1];
	}

	public ParticleBuilder<?>[] getParticleBuilders() {
		return builders;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public DynamicLocation[] getLocations() {
		return locations;
	}

	public void setLocations(DynamicLocation[] locations) {
		this.locations = locations;
	}

	public void saveStartLocations() {
		this.startlocations = EffectUtils.convertLocationArrayToArray(this.locations);
	}

	/**
	 * Resets the current locations back to their original start locations
	 */
	public void resetLocations() {
		if (startlocations != null) {
			EffectUtils.convertArrayToLocationArray(this.locations, this.startlocations);
		}
	}

	public void setDisplacement(double x, double y, double z) {
		this.displacement.setX(x);
		this.displacement.setY(y);
		this.displacement.setZ(z);
	}

	public void setDisplacement(Vector displacement) {
		this.displacement.setX(displacement.getX());
		this.displacement.setY(displacement.getY());
		this.displacement.setZ(displacement.getZ());
	}

	public Value3d getDisplacement() {
		return this.displacement;
	}

	public boolean stopTriggered() {
		return this.stopTriggered;
	}

	/**
	 * Triggers a stop of the effect after .update() finishes
	 */
	public void triggerStop(boolean triggerStop) {
		this.stopTriggered = triggerStop;
	}

}
