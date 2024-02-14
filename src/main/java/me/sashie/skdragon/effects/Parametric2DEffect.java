package me.sashie.skdragon.effects;

import me.sashie.skdragon.util.MathUtils;

/**
 * Created by Sashie on 8/11/2017.
 */

public abstract class Parametric2DEffect extends SimpleValueEffect {

    public Parametric2DEffect() {
        this.getDensityProperty().initDensity(60);
    }

    public abstract double vectorX(double angle);

    public abstract double vectorZ(double angle);

    @Override
    public void math(float step) {
        double angle = step * MathUtils.PI2 / this.getDensityProperty().getDensity(1);
        v.setX(vectorX(angle));
        v.setZ(vectorZ(angle));
    }

}
