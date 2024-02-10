package me.sashie.skdragon.effects.special;

import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.util.RandomUtils;
import me.sashie.skdragon.util.pool.ObjectPoolManager;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.SpecialRadiusDensityEffect;
import me.sashie.skdragon.effects.properties.AxisProperty;
import me.sashie.skdragon.effects.properties.IAxis;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.EffectUtils;

/**
 * Created by Sashie on 12/12/2021.
 */

public class Lightning extends SpecialRadiusDensityEffect implements IAxis {

	AxisProperty axisProperty;
	Vector vector;
	DynamicLocation bolt;

	public Lightning() {
		this.axisProperty = new AxisProperty();
		
		this.getRadiusProperty().initRadius(1.5f);
		this.getDensityProperty().initDensity(10);

		bolt = ObjectPoolManager.getDynamicLocationPool().acquire();
		vector = ObjectPoolManager.getVectorPool().acquire();
	}

	@Override
	public void update(DynamicLocation location, float step) {
		this.bolt.init(location);

		double x = 1 - this.getAxisProperty().getAxis().getX();
		double y = 1 - this.getAxisProperty().getAxis().getY();
		double z = 1 - this.getAxisProperty().getAxis().getZ();

		updateVector(x, y, z);

		double densityValue = 1.5 / this.getDensityProperty().getDensity(1);
		for (float count = this.getRadiusProperty().getRadius(1) * 20, i = 0; i < count; ++i) {
			this.getParticleBuilder(1).sendParticles(this.bolt, this.getPlayers());
			this.bolt.add(vector);
			if (RandomUtils.getRandomDouble() < densityValue) {
				updateVector(x, y, z);
				vector.normalize().multiply(0.1);
			}
		}
	}

	public void updateVector(double x, double y, double z) {
		vector.setX(vector.getX() + (RandomUtils.getRandomDouble() * 2 - 1) * x * 0.1)
			.setY(vector.getY() + (RandomUtils.getRandomDouble() * 2 - 1) * y * 0.1)
			.setZ(vector.getZ() + (RandomUtils.getRandomDouble() * 2 - 1) * z * 0.1);
	}

	@Override
	public void onUnregister() {
		ObjectPoolManager.getDynamicLocationPool().release(bolt);
		ObjectPoolManager.getVectorPool().release(vector);
	}

	@Override
	public EffectProperty[] acceptProperties() {
		return EffectUtils.array(EffectProperty.AXIS);
	}

	@Override
	public ParticleBuilder<?>[] defaultParticles() {
		return new ParticleBuilder<?>[] { new ColoredParticle(Particle.REDSTONE) };
	}

	@Override
	public AxisProperty getAxisProperty() {
		return axisProperty;
	}
}