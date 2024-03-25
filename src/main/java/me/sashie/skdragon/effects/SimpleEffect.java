package me.sashie.skdragon.effects;

import java.util.List;

import me.sashie.skdragon.effects.properties.*;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.util.*;
import me.sashie.skdragon.util.pool.ObjectPoolManager;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import me.sashie.skdragon.particles.ColoredParticle;

public abstract class SimpleEffect extends EffectData implements IDensity, IRadius, IStepTypes, IVelocity, IAxis, ISwingStep, IOrbitStep {

	private DensityProperty densityProperty;
	private RadiusProperty radiusProperty;
	private StepTypesProperty stepTypesProperty;
	private VelocityProperty velocityProperty;
	private AxisProperty axisProperty;
	private SwingStepProperty stepProperty;
	private OrbitStepProperty orbitStepProperty;

	private boolean init;
	private DynamicList<Vector> particleVectors;
	protected Vector v;

	public SimpleEffect() {

		densityProperty = new DensityProperty();
		radiusProperty = new RadiusProperty();
		stepTypesProperty = new StepTypesProperty();
		velocityProperty = new VelocityProperty();
		axisProperty = new AxisProperty();
		stepProperty = new SwingStepProperty();
		orbitStepProperty = new OrbitStepProperty();

		particleVectors = new DynamicList<>();
		v = ObjectPoolManager.getVectorPool().acquire();

		this.getDensityProperty().initDensity(20);
	}

	public void init(DynamicLocation location) {};
	public abstract void math(float step);

	@Override
	public void update() {
		for (int i = 0; i < this.getLocations().length; i++) {

			if (!init) {
				if (!this.getLocations()[i].isDynamic())
					this.getLocations()[i].add(this.getDisplacement().getX(), this.getDisplacement().getY(), this.getDisplacement().getZ());

				init(this.getLocations()[i]);

				if (i == this.getLocations().length - 1)
					init = true;
			} else {
				this.getLocations()[i].update();
				if (this.getLocations()[i].isDynamic())
					this.getLocations()[i].add(this.getDisplacement().getX(), this.getDisplacement().getY(), this.getDisplacement().getZ());

				float step = stepProperty.getStep();
				int density = this.densityProperty.getDensity(1);

				this.orbitStepProperty.updateOrbit(this.getLocations()[i]);

				if (this.stepTypesProperty.isSolid()) {
					for (int j = 0; j < density; j++) {
						v.setX(0).setY(0).setZ(0);
						math(j);
						spawnParticle(this.getLocations()[i], rotateShape(v, step));
					}
				} else if (this.stepTypesProperty.isFill()) {
					math(step);
					Vector clone = ObjectPoolManager.getVectorPool().acquire(v);
					particleVectors.add(clone);
					for (Vector particleVector : particleVectors) {
						spawnParticle(this.getLocations()[i], rotateShape(particleVector, step));
					}
					if (step >= density || step <= 0) {
						for (Vector v : particleVectors) {
							ObjectPoolManager.getVectorPool().release(v);
						}
						particleVectors.clear();
					}
				} else {
					math(step);
					spawnParticle(this.getLocations()[i], rotateShape(v, step));
				}

				this.radiusProperty.updateRadius();
				this.stepProperty.update(density);

			}
		}
	}

	@Override
	public void onUnregister() {
		ObjectPoolManager.getVectorPool().release(v);
	}

	@Override
	public ParticleBuilder<?>[] defaultParticles() {
		return new ParticleBuilder<?>[] { new ColoredParticle(Particle.REDSTONE) };
	}

	@Override
	public EffectProperty[] acceptDefaultProperties() {
		return EffectUtils.array(EffectProperty.ROTATE_VELOCITY, EffectProperty.AXIS, EffectProperty.DENSITY, EffectProperty.RADIUS, EffectProperty.STEP_TYPES, EffectProperty.DISPLACEMENT, EffectProperty.SWING_STEP, EffectProperty.ORBIT);
	}

	@Override
	public DensityProperty getDensityProperty() {
		return densityProperty;
	}

	@Override
	public RadiusProperty getRadiusProperty() {
		return radiusProperty;
	}

	@Override
	public StepTypesProperty getStepTypesProperty() {
		return stepTypesProperty;
	}

	@Override
	public VelocityProperty getVelocityProperty() {
		return velocityProperty;
	}

	@Override
	public AxisProperty getAxisProperty() {
		return axisProperty;
	}

	@Override
	public SwingStepProperty getSwingStepProperty() {
		return stepProperty;
	}

	@Override
	public OrbitStepProperty getOrbitStepProperty() {
		return orbitStepProperty;
	}

	public void spawnParticle(List<Vector> vectors) {
		for (int i = 0; i < this.getLocations().length; i++) {
			for (Vector vector : vectors)
				spawnParticle(this.getLocations()[i], vector);
		}
	}

	public void spawnParticle(DynamicLocation location, Vector vector) {
		for (ParticleBuilder<?> p : this.getParticleBuilders())
			spawnParticles(p, location, vector);
	}

	private void spawnParticles(ParticleBuilder<?> builder, DynamicLocation location, Vector vector) {
		location.add(vector);
		builder.sendParticles(location, this.getPlayers());
		location.subtract(vector);
	}

	public Vector rotateShape(Vector v, float step) {
		this.axisProperty.rotateAxis(v);
		this.velocityProperty.updateRotation(v, step);
		return v;
	}
}
