package me.sashie.skdragon.effects.simple.parametric;

import me.sashie.skdragon.effects.Parametric2DEffect;

/**
 * x = (a + b) * cos(t) - b * cos((a / b + 1) * t)
 * z = (a + b) * sin(t) - b * sin((a / b + 1) * t)
 */
public class Epicycloid2D extends Parametric2DEffect {

    public Epicycloid2D() {
        this.getExtraProperty().initValue(1.5f, 1.5f);
        this.getSolidProperty().setSolid(true);
    }

    @Override
    public double vectorX(double angle) {
        return (this.getExtraProperty().getValue(1) + this.getExtraProperty().getValue(2)) * Math.cos(angle) - this.getExtraProperty().getValue(2) * Math.cos((this.getExtraProperty().getValue(1) / this.getExtraProperty().getValue(2) + 1) * angle);
    }

    @Override
    public double vectorZ(double angle) {
        return (this.getExtraProperty().getValue(1) + this.getExtraProperty().getValue(2)) * Math.sin(angle) - this.getExtraProperty().getValue(2) * Math.sin((this.getExtraProperty().getValue(1) / this.getExtraProperty().getValue(2) + 1) * angle);
    }

}
