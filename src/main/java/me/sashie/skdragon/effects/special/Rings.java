 package me.sashie.skdragon.effects.special;

import me.sashie.skdragon.effects.properties.*;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.SpecialRadiusDensityEffect;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.EffectUtils;
import me.sashie.skdragon.util.VectorUtils;

public class Rings extends SpecialRadiusDensityEffect implements IAxis, IVelocity, IExtra, IStep {

	AxisProperty axisProperty;
	VelocityProperty velocityProperty;
	ExtraProperty extraProperty;
	StepProperty stepProperty;
	float step;
	
	public Rings() {
		axisProperty = new AxisProperty();
		velocityProperty = new VelocityProperty();
		extraProperty = new ExtraProperty();
		stepProperty = new StepProperty();

		this.getRadiusProperty().initRadius(1.5f);
		this.getDensityProperty().initDensity(10, 10);
		this.getExtraProperty().initValue(80);
	}

	@Override
	public void update(DynamicLocation location) {
		step = stepProperty.getStep();
		double angularVelocity = Math.PI / this.getExtraProperty().getValue(1);
		for (int i = 0; i < this.getDensityProperty().getDensity(1); i++) {
			final double angle = step * angularVelocity;
			for (int j = 0; j < this.getDensityProperty().getDensity(2); j++) {
				final Vector v = new Vector(Math.cos(angle), Math.sin(angle), 0.0).multiply(this.getRadiusProperty().getRadius(1));
				VectorUtils.rotateAroundAxisX(v, Math.PI / this.getDensityProperty().getDensity(2) * j);
				VectorUtils.rotateAroundAxisY(v, 90);

				this.axisProperty.rotateAxis(v);
				this.velocityProperty.updateRotation(v, step);

				location.add(v);
				this.getParticleBuilder(1).sendParticles(location, this.getPlayers());
				location.subtract(v);

				/*if (properties.getStyle() == 1) {

				} else {

				}*/
			}
		}
		this.stepProperty.update();
	}

	@Override
	public void onUnregister() {

	}

	@Override
	public EffectProperty[] acceptProperties() {
		return EffectUtils.array(EffectProperty.EXTRA, EffectProperty.AXIS, EffectProperty.ROTATE_VELOCITY);
	}

	@Override
	public ParticleBuilder<?>[] defaultParticles() {
		return new ParticleBuilder<?>[] { new ColoredParticle(Particle.REDSTONE) };
	}

	@Override
	public AxisProperty getAxisProperty() {
		return axisProperty;
	}

	@Override
	public VelocityProperty getVelocityProperty() {
		return velocityProperty;
	}

	@Override
	public ExtraProperty getExtraProperty() {
		return extraProperty;
	}

	@Override
	public StepProperty getStepProperty() {
		return stepProperty;
	}
}