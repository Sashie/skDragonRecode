package me.sashie.skdragon.api.effects.targets;

import me.sashie.skdragon.api.effects.EffectProperty;
import me.sashie.skdragon.api.effects.TargetEffect;
import me.sashie.skdragon.api.effects.properties.DensityProperty;
import me.sashie.skdragon.api.effects.properties.IDensity;
import me.sashie.skdragon.api.particles.ColoredParticle;
import me.sashie.skdragon.api.particles.ParticleBuilder;
import me.sashie.skdragon.api.util.DynamicLocation;
import me.sashie.skdragon.api.util.EffectUtils;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;


public class Line extends TargetEffect implements IDensity {

	private DensityProperty densityProperty;

	public Line() {
		densityProperty = new DensityProperty();
		this.getDensityProperty().initDensity(40);
	}

	@Override
	public void update(DynamicLocation location, DynamicLocation target, float step) {
		drawLine(this.getParticleBuilder(1), this.getPlayers(), location, target, this.getDensityProperty().getDensity(1));
	}

	public static void drawLine(ParticleBuilder particle, Player[] players, DynamicLocation from, DynamicLocation to, int density) {
		Vector link = from.toVector().subtract(to.toVector());
		float length = (float) link.length();
		link.normalize();
		Vector v = link.multiply(length / density);
		DynamicLocation loc = to.clone().subtract(v);
		for (int i = 0; i < density; i++) {
			loc.add(v);
			particle.sendParticles(loc, players);
		}
	}

	@Override
	public void onUnregister() {

	}

	@Override
	public EffectProperty[] acceptProperties() {
		return EffectUtils.array(EffectProperty.DENSITY);
	}

	@Override
	public ParticleBuilder<?>[] defaultParticles() {
		return new ParticleBuilder<?>[]{new ColoredParticle(Particle.REDSTONE)};
	}

	@Override
	public DensityProperty getDensityProperty() {
		return densityProperty;
	}

}