package me.sashie.skdragon.effects.special;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.SpecialEffect;
import me.sashie.skdragon.effects.properties.ExtraProperty;
import me.sashie.skdragon.effects.properties.IExtra;
import me.sashie.skdragon.effects.properties.IRadius;
import me.sashie.skdragon.effects.properties.RadiusProperty;
import me.sashie.skdragon.particles.DirectionParticle;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.EffectUtils;
import me.sashie.skdragon.util.MathUtils;
import me.sashie.skdragon.util.RandomUtils;
import me.sashie.skdragon.util.pool.ObjectPoolManager;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public class Meteor extends SpecialEffect implements IExtra, IRadius {

	private ExtraProperty extraProperty;
	private RadiusProperty radiusProperty;
	private List<Animation> meteors;

	public Meteor() {
		meteors = new ArrayList<Animation>();

		extraProperty = new ExtraProperty();
		radiusProperty = new RadiusProperty();

		this.getExtraProperty().initValue(3.0f);
		this.getRadiusProperty().initRadius(3.0f);
	}

	@Override
	public void update(DynamicLocation location, float step) {
		if (step % 30 == 0) {
			new Animation(location);
		}

		Iterator<Animation> iterator = null;
		for (iterator = meteors.iterator(); iterator.hasNext();) {
			Animation meteor = iterator.next();

			if (meteor.needsUpdate()) {
				meteor.update();
			} else {
				meteor.stop();
				iterator.remove();
				return;
			}

		}
	}

	private class Animation {
		DynamicLocation location, end;
		Vector v;
		double radius;

		public Animation(final DynamicLocation loc) {
			this.location = ObjectPoolManager.getDynamicLocationPool().acquire(loc);
			radius = getRadiusProperty().getRadius(1);
			end = ObjectPoolManager.getDynamicLocationPool().acquire(loc);
			end.add(RandomUtils.randomDoubleWithExclusion(-radius, radius, 0), 0.2,
					RandomUtils.randomDoubleWithExclusion(-radius, radius, 0));
			location.add(RandomUtils.randomDoubleWithExclusion(-radius, radius, 0, 1), getExtraProperty().getValue(1),
					RandomUtils.randomDoubleWithExclusion(-radius, radius, 0));
			(v = end.toVector().subtract(location.toVector()).normalize()).multiply(0.1);
			meteors.add(this);
		}

		public boolean needsUpdate() {
			return location.distance(end) >= 0.2;
		}

		public void update() {
			if (location == null) {
				return;
			}
			location.add(v);

			ParticleBuilder<?> p1 = getParticleBuilder(1);
			if (p1 instanceof DirectionParticle) {
				((DirectionParticle) p1).getParticleData().direction = v.clone().multiply(-1).add(new Vector(RandomUtils.randomRangeFloat(-0.01f, 0.01f), RandomUtils.randomRangeFloat(-0.01f, 0.01f), RandomUtils.randomRangeFloat(-0.01f, 0.01f)));
			}
			p1.sendParticles(location, getPlayers());

			ParticleBuilder<?> p2 = getParticleBuilder(2);
			if (p2 instanceof DirectionParticle) {
				((DirectionParticle) p2).getParticleData().direction = v.clone().multiply(1);
			}
			p2.sendParticles(location, getPlayers());
		}

		private void stop() {
			for (double n = 0.0; n <= MathUtils.PI2; n += (MathUtils.PI / 10)) {
				ParticleBuilder<?> p3 = getParticleBuilder(3);
				if (p3 instanceof DirectionParticle) {
					((DirectionParticle) p3).getParticleData().direction.setX(Math.cos(n)).setY(0.0).setZ(Math.sin(n));
				}
				p3.sendParticles(location, getPlayers());
			}
			ObjectPoolManager.getDynamicLocationPool().release(location);
			ObjectPoolManager.getDynamicLocationPool().release(end);
		}
	}

	@Override
	public void onUnregister() {/** ignore */}

	@Override
	public EffectProperty[] acceptProperties() {
		return EffectUtils.array(EffectProperty.RADIUS, EffectProperty.EXTRA);
	}

	@Override
	public ParticleBuilder<?>[] defaultParticles() {
		DirectionParticle particle = new DirectionParticle(Particle.FLAME);
		particle.getParticleData().speed = 0.1f;
		return new ParticleBuilder[] { new DirectionParticle(Particle.FLAME), new DirectionParticle(Particle.SMOKE_LARGE), particle };
	}

	@Override
	public ExtraProperty getExtraProperty() {
		return extraProperty;
	}

	@Override
	public RadiusProperty getRadiusProperty() {
		return radiusProperty;
	}
}
