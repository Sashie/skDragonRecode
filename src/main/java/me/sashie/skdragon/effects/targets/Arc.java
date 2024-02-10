package me.sashie.skdragon.effects.targets;

import org.bukkit.Particle;
import org.bukkit.util.Vector;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.TargetEffect;
import me.sashie.skdragon.effects.properties.DensityProperty;
import me.sashie.skdragon.effects.properties.ExtraProperty;
import me.sashie.skdragon.effects.properties.IDensity;
import me.sashie.skdragon.effects.properties.IExtra;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.EffectUtils;
import me.sashie.skdragon.util.VectorUtils;


/**
 * Created by Sashie on 12/12/2016.
 */

public class Arc extends TargetEffect implements IDensity, IExtra {

	private DensityProperty densityProperty;
	private ExtraProperty extraProperty;

	public Arc() {
		densityProperty = new DensityProperty();
		extraProperty = new ExtraProperty();

		this.getExtraProperty().initValue(4f, 1.5f, 0f);
		this.getDensityProperty().initDensity(40);

	}

	@Override
	public void update(DynamicLocation location, DynamicLocation target, float step) {
		Vector link = target.toVector().subtract(location.toVector());
		float length = (float) link.length();
		float pitch = (float) (this.getExtraProperty().getValue(2) * this.getExtraProperty().getValue(1) / Math.pow(length, 2));//pitchMultiplier default = 4
		for (int i = 0; i < this.getDensityProperty().getDensity(1); i++) {
			Vector v = link.clone().normalize().multiply((float) length * i / this.getDensityProperty().getDensity(1));
			float x = ((float) i / this.getDensityProperty().getDensity(1)) * length - length / 2;
			float y = (float) (-pitch * Math.pow(x, 2) + this.getExtraProperty().getValue(1));
			//TODO test \/
			Vector v2 = new Vector(0, y, 0);
			v2 = VectorUtils.rotateAroundAxisX(v2, this.getExtraProperty().getValue(3));
			location.add(v).add(v2);//.add(0, y, 0);
			this.getParticleBuilder(1).sendParticles(location, this.getPlayers());
			location.subtract(v2).subtract(v);
		}
		//TODO make it rotate more
	}

	@Override
	public void onUnregister() {

	}

	@Override
	public EffectProperty[] acceptProperties() {	//TODO
		return EffectUtils.array(EffectProperty.DENSITY, EffectProperty.EXTRA);
	}

	@Override
	public ParticleBuilder<?>[] defaultParticles() {
		return new ParticleBuilder<?>[] { new ColoredParticle(Particle.REDSTONE) };
	}

	@Override
	public ExtraProperty getExtraProperty() {
		return extraProperty;
	}

	@Override
	public DensityProperty getDensityProperty() {
		return densityProperty;
	}

}