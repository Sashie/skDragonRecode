package me.sashie.skdragon.effects.special;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ch.njol.skript.util.ColorRGB;
import me.sashie.skdragon.effects.properties.IStep;
import me.sashie.skdragon.effects.properties.StepProperty;
import me.sashie.skdragon.effects.targets.Line;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.util.*;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.SpecialRadiusDensityEffect;
import me.sashie.skdragon.effects.properties.ExtraProperty;
import me.sashie.skdragon.effects.properties.IExtra;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.particles.DirectionParticle;
import me.sashie.skdragon.util.color.ColorUtils;

public class Lasers extends SpecialRadiusDensityEffect implements IExtra, IStep {

	private ExtraProperty extraProperty;
	StepProperty stepProperty;

	private List<Animation> lines = new ArrayList<Animation>();

	public Lasers() {
		extraProperty = new ExtraProperty();
		stepProperty = new StepProperty();
		this.getRadiusProperty().initRadius(3.0f, 0);
		this.getExtraProperty().initValue(3.0f);
		this.getDensityProperty().initDensity(20, 15);
	}

	@Override
	public void update(DynamicLocation location) {
		if (stepProperty.getStep() % 20 == 0) {
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
		this.stepProperty.update();
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
			for (double angle = 0.0; angle <= Utils.PI2; angle += (Math.PI / getDensityProperty().getDensity(2))) {
				if (getParticleBuilder(2) instanceof DirectionParticle) {
					((DirectionParticle) getParticleBuilder(2)).getParticleData().direction = new Vector(Math.cos(angle), 0.0, Math.sin(angle));
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
		ColoredParticle p1 = new ColoredParticle(Particle.REDSTONE);
		p1.getParticleData().colors = ColorUtils.generateMultiGradient2(new ColorRGB[]{ new ColorRGB(232, 54, 23),  new ColorRGB(234, 120, 14), new ColorRGB(232, 54, 23) }, 50);
		DirectionParticle p2 = new DirectionParticle(Particle.SMOKE_LARGE);
		p2.getParticleData().speed = 0.08f;
		return new ParticleBuilder<?>[] { p1, p2, new DirectionParticle(Particle.FLAME) };
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