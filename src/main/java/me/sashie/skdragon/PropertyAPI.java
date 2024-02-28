package me.sashie.skdragon;

import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.properties.*;
import me.sashie.skdragon.effects.special.Wings;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.particles.DirectionParticle;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.skript.sections.ParticleSection;
import me.sashie.skdragon.util.ParticleUtils;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public class PropertyAPI {

	public static void setStyle(EffectData data, int style) {
		if (data instanceof IStyle) {
			if (data instanceof Wings) {
				((Wings) data).setStyle(style);
			} else {
				((IStyle) data).getStyleProperty().setStyle(style);
			}
		}
	}

	public static void setSolid(EffectData data, boolean solid) {
		if (data instanceof ISolid) {
			((ISolid) data).getSolidProperty().setSolid(solid);
		}
	}

	public static void setFill(EffectData data, boolean fill) {
		if (data instanceof IFill) {
			((IFill) data).getFillProperty().setFill(fill);
		}
	}

	public static void setRotate(EffectData data, boolean rotate) {
		if (data instanceof IRotation) {
			((IRotation) data).getRotateProperty().setRotating(rotate);
		}
	}

	public static void setOscillating(EffectData data, boolean oscillating) {
		if (data instanceof ISwingStep) {
			((ISwingStep) data).getSwingStepProperty().setOscillation(oscillating);
		}
	}

	public static void setReverse(EffectData data, boolean reverse) {
		if (data instanceof ISwingStep) {
			((ISwingStep) data).getSwingStepProperty().setReverse(reverse);
		}
	}

	public static void setAxis(EffectData data, float x, float y, float z) {
		if (data instanceof IAxis) {
			((IAxis) data).getAxisProperty().setAxis(x, y, z);
		}
	}

	public static void setVelocity(EffectData data, float x, float y, float z) {
		if (data instanceof IVelocity) {
			((IVelocity) data).getVelocityProperty().setAngularVelocity(x, y, z);
		}
	}

	public static void setDensity(EffectData data, int index, int density) {
		if (data instanceof IDensity) {
			((IDensity) data).getDensityProperty().setDensity(index, density);
		}
	}

	public static void setRadius(EffectData data, int index, float radius) {
		if (data instanceof IRadius) {
			((IRadius) data).getRadiusProperty().setRadius(index, radius);
		}
	}

	public static void setExtra(EffectData data, int index, float extra) {
		if (data instanceof IExtra) {
			((IExtra) data).getExtraProperty().setValue(index, extra);
		}
	}

	public static void setParticleColor(ParticleBuilder<?> particle, int r, int g, int b) {
		if (particle instanceof ColoredParticle) {
			((ColoredParticle) particle).getParticleData().setColor(r, g, b);
		}
	}

	public static void setParticleType(EffectData data, int index, Particle type) {
		ParticleBuilder<?> p = ParticleUtils.createParticle(type, data.getParticleBuilder(index).getParticleData());
		data.setParticle(index, p, null);
	}

	public static void setParticleSpeed(ParticleBuilder<?> particle, float speed) {
		if (particle instanceof DirectionParticle) {
			((DirectionParticle) particle).getParticleData().speed = speed;
		}
	}

	public static void setParticleDirection(ParticleBuilder<?> particle, double x, double y, double z) {
		if (particle instanceof DirectionParticle) {
			((DirectionParticle) particle).getParticleData().direction = new Vector(x, y, z);
		}
	}

	public static void setParticleOffset(ParticleBuilder<?> particle, double x, double y, double z) {
		particle.getParticleData().setOffset(x, y, z);
	}
}
