package me.sashie.skdragon.effects;

import java.util.List;

import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.util.*;
import me.sashie.skdragon.util.pool.ObjectPoolManager;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import me.sashie.skdragon.effects.properties.AxisProperty;
import me.sashie.skdragon.effects.properties.DensityProperty;
import me.sashie.skdragon.effects.properties.IAxis;
import me.sashie.skdragon.effects.properties.IDensity;
import me.sashie.skdragon.effects.properties.IRotation;
import me.sashie.skdragon.effects.properties.ISolid;
import me.sashie.skdragon.effects.properties.IVelocity;
import me.sashie.skdragon.effects.properties.RotationProperty;
import me.sashie.skdragon.effects.properties.SolidProperty;
import me.sashie.skdragon.effects.properties.VelocityProperty;
import me.sashie.skdragon.particles.ColoredParticle;

public abstract class SimpleEffect extends EffectData implements IDensity, ISolid, IRotation, IVelocity, IAxis {

	private DensityProperty densityProperty;
	private SolidProperty solidProperty;
	private RotationProperty rotationProperty;
	private VelocityProperty velocityProperty;
	private AxisProperty axisProperty;
	
	private boolean init;
	protected Vector v;

	public SimpleEffect() {

		densityProperty = new DensityProperty();
		solidProperty = new SolidProperty();
		rotationProperty = new RotationProperty();
		velocityProperty = new VelocityProperty();
		axisProperty = new AxisProperty();

		v = ObjectPoolManager.getVectorPool().acquire();

		this.getDensityProperty().initDensity(20);
	}

	public void init(DynamicLocation location) {};
	public abstract void math(float step);
	
	@Override
	public void update(float step) {
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

				if (this.solidProperty.isSolid()){
					for (int j = 0; j < this.densityProperty.getDensity(1); j++) {
						v.setX(0).setY(0).setZ(0);
						math(j);
						spawnParticle(this.getLocations()[i], rotateShape(v, step));
					}
				} else {
					math(step);
					spawnParticle(this.getLocations()[i], rotateShape(v, step));
				}
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
		return EffectUtils.array(EffectProperty.AUTO_ROTATE, EffectProperty.XYZ_ANGULAR_VELOCITY, EffectProperty.AXIS, EffectProperty.DENSITY, EffectProperty.SOLID_SHAPE, EffectProperty.DISPLACEMENT);
	}

	@Override
	public DensityProperty getDensityProperty() {
		return densityProperty;
	}

	@Override
	public SolidProperty getSolidProperty() {
		return solidProperty;
	}

	@Override
	public RotationProperty getRotateProperty() {
		return rotationProperty;
	}
	
	@Override
	public VelocityProperty getVelocityProperty() {
		return velocityProperty;
	}

	@Override
	public AxisProperty getAxisProperty() {
		return axisProperty;
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
		VectorUtils.rotateVector(v, this.axisProperty.getAxis().getX(), this.axisProperty.getAxis().getY(), this.axisProperty.getAxis().getZ());
		if (this.rotationProperty.isRotating())
			return VectorUtils.rotateVector(v, (this.velocityProperty.getAngularVelocityX()) * step, (this.velocityProperty.getAngularVelocityY()) * step, (this.velocityProperty.getAngularVelocityZ()) * step);
		return v;
	}
}
