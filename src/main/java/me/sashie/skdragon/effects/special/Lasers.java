package me.sashie.skdragon.effects.special;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.sashie.skdragon.effects.target.Line;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.util.pool.ObjectPoolManager;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.SpecialRadiusDensityEffect;
import me.sashie.skdragon.effects.properties.ExtraProperty;
import me.sashie.skdragon.effects.properties.IExtra;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.particles.DirectionParticle;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.EffectUtils;
import me.sashie.skdragon.util.MathUtils;
import me.sashie.skdragon.util.RandomUtils;
import me.sashie.skdragon.util.color.ColorUtils;

/**
 * Created by Sashie on 10/28/2017.
 */

public class Lasers extends SpecialRadiusDensityEffect implements IExtra {

	private ExtraProperty extraProperty;
	
	private List<Animation> lines = new ArrayList<Animation>();

	public Lasers() {
		extraProperty = new ExtraProperty();
		this.getRadiusProperty().initRadius(3.0f, 0);
		this.getExtraProperty().initValue(3.0f);
		this.getDensityProperty().initDensity(20, 15);

		if (this.getParticleBuilder(1) instanceof ColoredParticle) {
			((ColoredParticle) this.getParticleBuilder(1)).getParticleData().colors = ColorUtils.generateMultiGradient2(new Color[]{ new Color(232, 54, 23),  new Color(234, 120, 14), new Color(232, 54, 23) }, 50);
		}
		if (this.getParticleBuilder(2) instanceof DirectionParticle) {
        	((DirectionParticle) this.getParticleBuilder(2)).getParticleData().speed = 0.08f;
        }

	}

	@Override
	public void update(DynamicLocation location, float step) {
		if (step % 20 == 0) {
			new Animation(location);
		}

		Iterator<Animation> iterator = null;
		for (iterator = lines.iterator(); iterator.hasNext();) {
			Animation line = iterator.next();

			if (line.start.distance(line.end) >= 0.2) {
				line.update();
			} else {
				line.stop();
				iterator.remove();
				return;
			}
		}
	}

	private class Animation {
		DynamicLocation location, start, end;
		Vector v;
		double r;

		public Animation(final DynamicLocation loc) {
			this.location = loc.clone();
			r = getRadiusProperty().getRadius(1);
			end = location.clone();
			end.add(RandomUtils.randomRangeDouble(-r, r), 0.2,
					RandomUtils.randomRangeDouble(-r, r));
			
			start = location.clone();
			start.add(RandomUtils.randomRangeDouble(-r, r), 0.2,
					RandomUtils.randomRangeDouble(-r, r));
			
			if (getRadiusProperty().getRadius(2) == 0) {
				location.add(0, getExtraProperty().getValue(1), 0);
			} else {
				location.add(RandomUtils.randomRangeFloat(-getRadiusProperty().getRadius(2), getRadiusProperty().getRadius(2)), getExtraProperty().getValue(1),
						RandomUtils.randomRangeFloat(-getRadiusProperty().getRadius(2), getRadiusProperty().getRadius(2)));
			}
			
			(v = end.toVector().subtract(start.toVector()).normalize()).multiply(0.1);
			
			lines.add(this);
		}

		public void update() {
			if (start == null) {
				return;
			}	
			start.add(v);

			Line.drawLine(getParticleBuilder(1), getPlayers(), start, location, getDensityProperty().getDensity(1));

			if (getParticleBuilder(2) instanceof DirectionParticle) {
				((DirectionParticle) getParticleBuilder(2)).getParticleData().direction = v.clone().multiply(1);
			}
			getParticleBuilder(2).sendParticles(start, getPlayers());
			
			if (getParticleBuilder(3) instanceof DirectionParticle) {
				((DirectionParticle) getParticleBuilder(3)).getParticleData().direction = v.clone().multiply(-0.5).add(new Vector(RandomUtils.randomRangeFloat(-0.01f, 0.01f), RandomUtils.randomRangeFloat(-0.01f, 0.01f), RandomUtils.randomRangeFloat(-0.01f, 0.01f)));
			}
			getParticleBuilder(3).sendParticles(start, getPlayers());

		}

		public void stop() {
			for (double angle = 0.0; angle <= MathUtils.PI2; angle += (MathUtils.PI / getDensityProperty().getDensity(2))) {
				if (getParticleBuilder(2) instanceof DirectionParticle) {
					((DirectionParticle) getParticleBuilder(3)).getParticleData().direction = new Vector(Math.cos(angle), 0.0, Math.sin(angle));
				}
				getParticleBuilder(2).sendParticles(start, getPlayers());
			}
		}
	}

	@Override
	public void onUnregister() {/** ignore */}

	@Override
	public EffectProperty[] acceptProperties() {
		return EffectUtils.array(EffectProperty.EXTRA);
	}

	@Override
	public ParticleBuilder<?>[] defaultParticles() {
		return new ParticleBuilder<?>[] { new ColoredParticle(Particle.REDSTONE), new DirectionParticle(Particle.SMOKE_LARGE), new DirectionParticle(Particle.FLAME) };
	}

	@Override
	public ExtraProperty getExtraProperty() {
		return extraProperty;
	}
}