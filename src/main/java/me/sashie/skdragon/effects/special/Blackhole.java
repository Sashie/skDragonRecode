package me.sashie.skdragon.effects.special;

import me.sashie.skdragon.util.pool.ObjectPoolManager;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.SpecialRadiusDensityEffect;
import me.sashie.skdragon.effects.properties.ExtraProperty;
import me.sashie.skdragon.effects.properties.IExtra;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.particles.DirectionParticle;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.EffectUtils;
import me.sashie.skdragon.util.MathUtils;
import me.sashie.skdragon.util.RandomUtils;
import me.sashie.skdragon.util.VectorUtils;

/**
 * Created by Sashie on 12/12/2021
 */

public class Blackhole extends SpecialRadiusDensityEffect implements IExtra {

	private ExtraProperty extraProperty;
	Vector vector, direction;
	DynamicLocation add, subtract;

	public Blackhole() {
		extraProperty = new ExtraProperty();

		this.getExtraProperty().initValue(new float[] { 0.4f, 0.3f, 0.5f });
		this.getDensityProperty().initDensity(new int[] { 10, 5 });
		this.getRadiusProperty().initRadius(new float[] { 1.5f });

		vector = ObjectPoolManager.getVectorPool().acquire();
		direction = ObjectPoolManager.getVectorPool().acquire();
		add = ObjectPoolManager.getDynamicLocationPool().acquire();
		subtract = ObjectPoolManager.getDynamicLocationPool().acquire();
	}

	@Override
	public void update(DynamicLocation location, float step) {
		subtract.init(location).subtract(0.0, 0.3, 0.0);

		for (float i = this.getRadiusProperty().getRadius(1); i < this.getDensityProperty().getDensity(2); ++i) {
			for (double d = 0.0; d <= MathUtils.PI2; d += (MathUtils.PI / this.getDensityProperty().getDensity(1))) {
				final double angle = step * this.getExtraProperty().getValue(3) + Math.toRadians(i * 30);
                final double space = i * this.getExtraProperty().getValue(1);
                
				vector.setX(Math.cos(d) * space).setY(i * this.getExtraProperty().getValue(2)).setZ(Math.sin(d) * space);

                VectorUtils.rotateAroundAxisY(vector, angle);
                this.getParticleBuilder(1).sendParticles(subtract.add(vector), this.getPlayers());
                if (RandomUtils.randomRangeFloat(0, 100) >= 95) {
                	this.getParticleBuilder(2).sendParticles(subtract, this.getPlayers());
                	this.getParticleBuilder(3).sendParticles(subtract, this.getPlayers());
                }
                subtract.subtract(vector);
			}
		}

        if (step % 10 == 0) {
			add.init(subtract).add(RandomUtils.randomRangeFloat(-1.5f, 1.5f), 1.5, RandomUtils.randomRangeFloat(-1.5f, 1.5f));
            if (this.getParticleBuilder(4) instanceof DirectionParticle) {
                direction.setX(location.getX()).setY(location.getY()).setZ(location.getZ());
				vector.setX(add.getX()).setY(add.getY()).setZ(add.getZ());
                ((DirectionParticle) this.getParticleBuilder(4)).getParticleData().direction = direction.subtract(vector).normalize();
            }
            this.getParticleBuilder(4).sendParticles(add, this.getPlayers());
        }
	}

	@Override
	public void onUnregister() {
		ObjectPoolManager.getDynamicLocationPool().release(add);
		ObjectPoolManager.getVectorPool().release(vector);
		ObjectPoolManager.getDynamicLocationPool().release(subtract);
		ObjectPoolManager.getVectorPool().release(direction);
	}

	@Override
	public EffectProperty[] acceptProperties() {
		return EffectUtils.array(EffectProperty.EXTRA);
	}

	@Override
	public ParticleBuilder<?>[] defaultParticles() {
		ColoredParticle particle1 = new ColoredParticle(Particle.REDSTONE);
		particle1.getParticleData().setColor(0, 0, 0);
		ColoredParticle particle2 = new ColoredParticle(Particle.REDSTONE);
		particle2.getParticleData().setColor(204, 0, 204);
		ColoredParticle particle3 = new ColoredParticle(Particle.SPELL_MOB);
		particle3.getParticleData().setColor(5, 5, 5);
		DirectionParticle particle4 = new DirectionParticle(Particle.FLAME);
		particle4.getParticleData().speed = 0.1f;

		return new ParticleBuilder<?>[] { particle1, particle2, particle3, particle4 };
	}

	@Override
	public ExtraProperty getExtraProperty() {
		return extraProperty;
	}

}