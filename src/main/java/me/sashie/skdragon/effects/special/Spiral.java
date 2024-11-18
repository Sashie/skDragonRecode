package me.sashie.skdragon.effects.special;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.SpecialRadiusDensityEffect;
import me.sashie.skdragon.effects.properties.*;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.util.*;
import me.sashie.skdragon.util.pool.ObjectPoolManager;
import org.bukkit.util.Vector;

public class Spiral extends SpecialRadiusDensityEffect implements IExtra, IAxis, IStyle, ISwingStep, IStepTypes {

	AxisProperty axisProperty;
	StyleProperty styleProparty;
	SwingStepProperty stepProperty;
	ExtraProperty extraProperty;
	StepTypesProperty stepTypesProperty;
	DynamicLocation loc;
	Vector v;
	float step;
	float i;

	public Spiral() {
		this.axisProperty = new AxisProperty();
		this.styleProparty = new StyleProperty();
		this.stepProperty = new SwingStepProperty();
		this.extraProperty = new ExtraProperty();
		this.stepTypesProperty = new StepTypesProperty();
		this.getRadiusProperty().initRadius(1.5f, 2f);
		this.getDensityProperty().initDensity(20);
		this.getExtraProperty().initValue(.02f);

		loc = ObjectPoolManager.getDynamicLocationPool().acquire();
		v = ObjectPoolManager.getVectorPool().acquire();
	}

	@Override
	public void update(DynamicLocation location) {
		loc.init(location);
		step = this.getSwingStepProperty().getStep();

		double angle = (MathUtils.PI2 / this.getDensityProperty().getDensity(1) ) * step;
		double y = i;//0.3 * i;
		if (this.getStyleProperty().getStyle() == 1)
			v.setX(Math.sin(angle) * this.getRadiusProperty().getRadius(1))
					.setY(y)
					.setZ(Math.cos(angle) * this.getRadiusProperty().getRadius(1));
		else
			v.setX(Math.cos(angle) * this.getRadiusProperty().getRadius(1))
					.setY(y)
					.setZ(Math.sin(angle) * this.getRadiusProperty().getRadius(1));
		this.getAxisProperty().rotateAxis(v);
		this.getParticleBuilder(1).sendParticles(loc.add(v).add(0, 0.1, 0), getPlayers());

		this.getSwingStepProperty().update();

		float height = this.getRadiusProperty().getRadius(2);
		if (this.getSwingStepProperty().isOscillating()){
			if (i > height) {
				this.getSwingStepProperty().setReverse(false);
			}
			else if (i < 0) {
				this.getSwingStepProperty().setReverse(true);
			}
		} else {
			if (i > height) {
				i = 0;
			}
			if (i < 0) {
				i = height;
			}
		}

		if (!this.getSwingStepProperty().isReverse())
			i += this.getExtraProperty().getValue(1);
		else
			i -= this.getExtraProperty().getValue(1);
		this.getRadiusProperty().updateRadius();
	}


	@Override
	public void onUnregister() {
		ObjectPoolManager.getDynamicLocationPool().release(loc);
		ObjectPoolManager.getVectorPool().release(v);
	}

	@Override
	public EffectProperty[] acceptProperties() {
		return EffectUtils.array(EffectProperty.AXIS, EffectProperty.EXTRA, EffectProperty.SWING_STEP, EffectProperty.STYLE);
	}

	@Override
	public ParticleBuilder<?>[] defaultParticles() {
		return new ParticleBuilder<?>[] { new ColoredParticle(ParticleUtils.REDSTONE).getParticleData().setColor(252, 192, 30) };
	}

	@Override
	public AxisProperty getAxisProperty() {
		return axisProperty;
	}

	@Override
	public StyleProperty getStyleProperty() {
		return styleProparty;
	}

	@Override
	public SwingStepProperty getSwingStepProperty() {
		return stepProperty;
	}

	@Override
	public ExtraProperty getExtraProperty() {
		return extraProperty;
	}

	@Override
	public StepTypesProperty getStepTypesProperty() {
		return stepTypesProperty;
	}
}