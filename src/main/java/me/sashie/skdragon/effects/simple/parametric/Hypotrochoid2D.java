package me.sashie.skdragon.effects.simple.parametric;

import me.sashie.skdragon.effects.Parametric2DEffect;

/**
 * Created by Sashie on 8/11/2017.
 * 
 * 		x = (a - b) * cos(t) + c * cos((a/b -1)t)
 * 		z = (a - b) * sin(t) - c * sin((a/b -1)t)
 * 
 */

public class Hypotrochoid2D extends Parametric2DEffect {

	public Hypotrochoid2D() {
		this.getExtraProperty().initValue(-0.6f, 0.3f, 0.5f);
		this.getSolidProperty().setSolid(true);
	}

	@Override
	public double vectorX(double angle) {
		return (this.getExtraProperty().getValue(1) - this.getExtraProperty().getValue(2)) * Math.cos(angle) + this.getExtraProperty().getValue(3) * Math.cos(((this.getExtraProperty().getValue(1) / this.getExtraProperty().getValue(2)) - 1) * angle);
	}

	@Override
	public double vectorZ(double angle) {
		return (this.getExtraProperty().getValue(1) - this.getExtraProperty().getValue(2)) * Math.sin(angle) - this.getExtraProperty().getValue(3) * Math.sin(((this.getExtraProperty().getValue(1) / this.getExtraProperty().getValue(2)) - 1) * angle);
	}

}
