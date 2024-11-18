 package me.sashie.skdragon.effects.special;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.SpecialRadiusDensityEffect;
import me.sashie.skdragon.effects.properties.*;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.EffectUtils;
import me.sashie.skdragon.util.MathUtils;
import me.sashie.skdragon.util.ParticleUtils;
import me.sashie.skdragon.util.RandomUtils;
import me.sashie.skdragon.util.VectorUtils;
import me.sashie.skdragon.util.pool.ObjectPoolManager;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Breath extends SpecialRadiusDensityEffect implements IAxis, IExtra, IStep {

	ExtraProperty extraProperty;
	StepProperty stepProperty;
	AxisProperty axisProperty;

	protected final List<Float> rndF = new ArrayList<>();
	protected final List<Double> rndAngle = new ArrayList<>();
	DynamicLocation loc;
	Vector v;

	static public final float degreesToRadians = MathUtils.PI / 180;

	public Breath() {
		extraProperty = new ExtraProperty();
		stepProperty = new StepProperty();
		axisProperty = new AxisProperty();

		this.getRadiusProperty().initRadius(6f); // length
		this.getDensityProperty().initDensity(40, 50, 3); // particle density, arc count, arc steps
		this.getExtraProperty().initValue(0.1f); // arc pitch

		loc = ObjectPoolManager.getDynamicLocationPool().acquire();
		v = ObjectPoolManager.getVectorPool().acquire();
	}

	@Override
	public void update(DynamicLocation location) {
		loc.init(location);
		if (location.isDynamic()) {
			loc.add(0, 1.475, 0);
		}

		float arcPitch = this.getExtraProperty().getValue(1);
		int arcDensity = this.getDensityProperty().getDensity(2);
		int particleDensity = this.getDensityProperty().getDensity(1);
		float particleInterval = this.getStepProperty().getStep() % particleDensity;
		for (int j = 0; j < this.getDensityProperty().getDensity(3); j++) {
			if (particleInterval == 0) {
				rndF.clear();
				rndAngle.clear();
			}
			while (rndF.size() < arcDensity) {
				rndF.add(RandomUtils.random.nextFloat());
			}
			while (rndAngle.size() < arcDensity) {
				rndAngle.add(RandomUtils.getRandomAngle());
			}
			for (int i = 0; i < arcDensity; i++) {
				float pitch = rndF.get(i) * 2 * arcPitch - arcPitch;
				float x = particleInterval * this.getRadiusProperty().getRadius(1) / particleDensity;
				float y = (float) (pitch * Math.pow(x, 2));
				v.setX(x).setY(y);
				//v = ObjectPoolManager.getVectorPool().acquire().setX(x).setY(y);

				this.getAxisProperty().rotateAxis(v);

				VectorUtils.rotateAroundAxisX(v, rndAngle.get(i).floatValue());
				VectorUtils.rotateAroundAxisZ(v, -loc.getPitch() * degreesToRadians);
				VectorUtils.rotateAroundAxisY(v, -(loc.getYaw() + 90) * degreesToRadians);

				this.getParticleBuilder(1).sendParticles(loc.add(v), this.getPlayers());
				loc.subtract(v);
				v.setX(0).setY(0).setZ(0);
			}
			this.getRadiusProperty().updateRadius();
			this.getStepProperty().update();
		}
	}

	@Override
	public void onUnregister() {
		ObjectPoolManager.getDynamicLocationPool().release(loc);
		ObjectPoolManager.getVectorPool().release(v);
	}

	@Override
	public EffectProperty[] acceptProperties() {
		return EffectUtils.array(EffectProperty.AXIS, EffectProperty.EXTRA);
	}

	@Override
	public ParticleBuilder<?>[] defaultParticles() {
		return new ParticleBuilder<?>[] { new ColoredParticle(ParticleUtils.REDSTONE) };
	}

	@Override
	public ExtraProperty getExtraProperty() {
		return extraProperty;
	}

	@Override
	public StepProperty getStepProperty() {
		return stepProperty;
	}

	 @Override
	 public AxisProperty getAxisProperty() {
		 return axisProperty;
	 }
 }