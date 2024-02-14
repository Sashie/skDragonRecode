package me.sashie.skdragon.effects.targets;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.TargetEffect;
import me.sashie.skdragon.effects.properties.*;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.EffectUtils;
import me.sashie.skdragon.util.RandomUtils;
import me.sashie.skdragon.util.pool.ObjectPoolManager;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public class TargetLightning extends TargetEffect implements IRadius, IDensity, IStyle, IExtra {

	private final RadiusProperty radiusProperty;
	private final StyleProperty styleProperty;
	private final ExtraProperty extraProperty;
	private final DensityProperty densityProperty;
	private DynamicLocation loc;

	public TargetLightning() {
		densityProperty = new DensityProperty();
		radiusProperty = new RadiusProperty();
		styleProperty = new StyleProperty();
		extraProperty = new ExtraProperty();

		this.getExtraProperty().initValue(0.5f);
		this.getRadiusProperty().initRadius(0.5f);
		this.getDensityProperty().initDensity(40);
	}

	@Override
	public void update(DynamicLocation location, DynamicLocation target, float step) {
		electricity(location, target, this.getRadiusProperty().getRadius(1), this.getDensityProperty().getDensity(1));
	}

	public void electricity(DynamicLocation startLoc, DynamicLocation endLoc, float maxOffset, int density) {
		double currentX = startLoc.getX();
		double currentY = startLoc.getY();
		double currentZ = startLoc.getZ();
		double lastX = startLoc.getX();
		double lastY = startLoc.getY();
		double lastZ = startLoc.getZ();

		//int segments = (int) (startLoc.distance(endLoc) / distanceBetweenPoints) + 1;
		//int segments = (int) (startLoc.distanceSquared(endLoc) / distanceBetweenPoints) + 1;
		double distance = startLoc.distanceSquared(endLoc);
		int segments = 1;
		if (this.getStyleProperty().getStyle() == 1)
			segments = (int) (distance / this.getExtraProperty().getValue(1)) + 1;
		else if (this.getStyleProperty().getStyle() == 2)
			segments = (int) (distance / RandomUtils.randomRangeDouble(distance / this.getExtraProperty().getValue(1), distance / 2)) + 1;
		else
			segments = this.getExtraProperty().getValue(1).intValue();

		//System.out.println(segments / density);
		//TODO make a better equation for total length to segment length ratio
		int dps = density / segments;
		if (dps <= 1)
			dps = 1;

		for (int i = 0; i < segments; i++) {
			currentX = ((endLoc.getX() - startLoc.getX()) / segments) * i + startLoc.getX();
			currentY = ((endLoc.getY() - startLoc.getY()) / segments) * i + startLoc.getY();
			currentZ = ((endLoc.getZ() - startLoc.getZ()) / segments) * i + startLoc.getZ();

			// offsetting
			currentX += maxOffset * RandomUtils.random(1f) * RandomUtils.randomSign();
			currentY += maxOffset * RandomUtils.random(1f) * RandomUtils.randomSign();
			currentZ += maxOffset * RandomUtils.random(1f) * RandomUtils.randomSign();

			Vector start = new Vector(lastX, lastY, lastZ);
			Vector end = new Vector(currentX, currentY, currentZ);

			line(start, end, dps);

			lastX = currentX;
			lastY = currentY;
			lastZ = currentZ;
		}

		line(new Vector(currentX, currentY, currentZ), new Vector(endLoc.getX(), endLoc.getY(), endLoc.getZ()), dps);
	}

	private void line(Vector start, Vector end, int density) {
		Vector link = end.subtract(start);
		float length = (float) link.length();
		link.normalize();
		float ratio = length / density;
		Vector v = link.multiply(ratio);
		//DynamicLocation loc = new DynamicLocation(start.subtract(v).toLocation(this.getLocations()[0].getWorld()));
		loc = ObjectPoolManager.getDynamicLocationPool().acquire(start.subtract(v).toLocation(this.getLocations()[0].getWorld()));

		for (int i = 0; i < density; i++) {
			loc.add(v);
			this.getParticleBuilder(1).sendParticles(loc, this.getPlayers());
		}
	}

	@Override
	public void onUnregister() {
		ObjectPoolManager.getDynamicLocationPool().release(loc);
	}

	@Override
	public EffectProperty[] acceptProperties() {
		return EffectUtils.array(EffectProperty.STYLE, EffectProperty.RADIUS, EffectProperty.DENSITY, EffectProperty.EXTRA);
	}

	@Override
	public ParticleBuilder<?>[] defaultParticles() {
		return new ParticleBuilder<?>[]{new ColoredParticle(Particle.REDSTONE)};
	}

	@Override
	public ExtraProperty getExtraProperty() {
		return extraProperty;
	}

	@Override
	public RadiusProperty getRadiusProperty() {
		return radiusProperty;
	}

	@Override
	public DensityProperty getDensityProperty() {
		return densityProperty;
	}

	@Override
	public StyleProperty getStyleProperty() {
		return styleProperty;
	}
}