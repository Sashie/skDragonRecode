 package me.sashie.skdragon.effects.special;

import me.sashie.skdragon.effects.properties.*;
import me.sashie.skdragon.util.*;
import org.bukkit.util.Vector;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.SpecialRadiusDensityEffect;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.particles.ParticleBuilder;

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
		float angularVelocity = MathUtils.PI / this.getExtraProperty().getValue(1);
		for (int i = 0; i < this.getDensityProperty().getDensity(1); i++) {
			final float angle = step * angularVelocity;
			for (int j = 0; j < this.getDensityProperty().getDensity(2); j++) {
				final Vector v = new Vector(MathUtils.cos(angle), MathUtils.sin(angle), 0.0).multiply(this.getRadiusProperty().getRadius(1));
				VectorUtils.rotateAroundAxisX(v, MathUtils.PI / this.getDensityProperty().getDensity(2) * j);
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
		this.getRadiusProperty().updateRadius();
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
		return new ParticleBuilder<?>[] { new ColoredParticle(ParticleUtils.REDSTONE) };
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