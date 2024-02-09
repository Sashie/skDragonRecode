package me.sashie.skdragon.effects.targets;

import me.sashie.skdragon.particles.ParticleBuilder;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.TargetEffect;
import me.sashie.skdragon.effects.properties.DensityProperty;
import me.sashie.skdragon.effects.properties.ExtraProperty;
import me.sashie.skdragon.effects.properties.IDensity;
import me.sashie.skdragon.effects.properties.IExtra;
import me.sashie.skdragon.effects.properties.IRadius;
import me.sashie.skdragon.effects.properties.RadiusProperty;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.EffectUtils;


/**
 * Created by Sashie on 12/12/2016.
 */

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
		return new ParticleBuilder<?>[] { new ColoredParticle(Particle.REDSTONE) };
	}

	@Override
	public DensityProperty getDensityProperty() {
		return densityProperty;
	}

}