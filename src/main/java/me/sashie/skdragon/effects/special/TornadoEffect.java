package me.sashie.skdragon.effects.special;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.SpecialRadiusDensityEffect;
import me.sashie.skdragon.effects.properties.*;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.particles.NormalParticle;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.util.*;
import me.sashie.skdragon.util.pool.ObjectPoolManager;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import java.util.Iterator;

public class TornadoEffect extends SpecialRadiusDensityEffect implements IAxis, IVelocity, IExtra, IStep, ITimespan {

    private final DynamicList<SpiralAnimation> directionals = new DynamicList<SpiralAnimation>();

    AxisProperty axisProperty;
    VelocityProperty velocityProperty;
    ExtraProperty extraProperty;
    StepProperty stepProperty;
    TimespanProperty timespanProperty;
    //float step;
    private int tickCounter = 20;
    private float globalRotation = 0.0f;

    public TornadoEffect() {
        axisProperty = new AxisProperty();
        velocityProperty = new VelocityProperty();
        extraProperty = new ExtraProperty();
        stepProperty = new StepProperty();
        timespanProperty = new TimespanProperty();

        // Initialize properties
        this.getRadiusProperty().initRadius(0.5f, 3.0f, 20.0f, 2.0f, 5.0f, 4.0f);  // Tornado base radius, top radius, height, base dust radius,  top dust radius, dust height
        this.getDensityProperty().initDensity(15, 10); // dust amount, whole tornado
        this.getExtraProperty().initValue(40, 0.1f, 0.2f);  // Tornado randomness factor, spiral rotation speed, global rotation speed
    }

    @Override
    public void update(DynamicLocation location) {
        tickCounter++;

        globalRotation += this.getExtraProperty().getValue(3);
        if (globalRotation >= 360.0f) globalRotation -= 360.0f;

        int interval = 20;
        if (tickCounter >= interval) {
            directionals.add(new SpiralAnimation(location, globalRotation));
            tickCounter = 0;
        }

        Iterator<SpiralAnimation> iterator = null;
        for (iterator = directionals.iterator(); iterator.hasNext();) {
            SpiralAnimation animation = iterator.next();

            if (animation.needsUpdate()) {
                animation.update();
            } else {
                animation.onStop();
                iterator.remove();
                return;
            }
        }
        this.getRadiusProperty().updateRadius();
    }

    @Override
    public void onUnregister() {
        directionals.clear();
    }

    @Override
    public EffectProperty[] acceptProperties() {
        return EffectUtils.array(EffectProperty.EXTRA, EffectProperty.AXIS, EffectProperty.ROTATE_VELOCITY);
    }

    @Override
    public ParticleBuilder<?>[] defaultParticles() {
        return new ParticleBuilder<?>[] {
                new NormalParticle(ParticleUtils.SMOKE_NORMAL),  // Base particles
                new NormalParticle(Particle.CLOUD),         // Mid-level particles
                new ColoredParticle(ParticleUtils.REDSTONE),    // Top stormy particles
                new NormalParticle(Particle.WHITE_ASH)
        };
    }

    @Override
    public AxisProperty getAxisProperty() {
        return axisProperty;
    }

    @Override
    public VelocityProperty getVelocityProperty() {
        return velocityProperty;
    }

    @Override
    public ExtraProperty getExtraProperty() {
        return extraProperty;
    }

    @Override
    public StepProperty getStepProperty() {
        return stepProperty;
    }

    @Override
    public TimespanProperty getTimespanProperty() {
        return timespanProperty;
    }

    private class SpiralAnimation {
        private float step;
        DynamicLocation location;
        Vector v;
        float maxHeight;
        float heightProgression;
        float globalAngle;

        public SpiralAnimation(final DynamicLocation loc, float globalAngle) {
            location = ObjectPoolManager.getDynamicLocationPool().acquire(loc);
            v = ObjectPoolManager.getVectorPool().acquire();
            maxHeight = getRadiusProperty().getRadius(3);
            this.globalAngle = globalAngle;
        }

        public boolean needsUpdate() {
            return v.getY() < maxHeight - 1;
        }

        public void update() {
            if (location == null) {
                return;
            }
            // Tornado maximum height
            maxHeight = getRadiusProperty().getRadius(3);
            heightProgression = (step / 10.0f) % maxHeight;

            // Define the bottom (narrow) and top (wide) radius for the tornado
            float initialRadius = getRadiusProperty().getRadius(1);  // Narrow at the bottom
            float maxRadius = getRadiusProperty().getRadius(2);  // Wide at the top

            // Upward velocity with decay factor as height increases
            double upwardVelocity = (getVelocityProperty().getAngularVelocityY() + 0.05f) * (1.0f - (heightProgression / maxHeight));

            // Smoothly interpolate the radius based on height progression (bottom narrow, top wide)
            float radius = initialRadius + ((maxRadius - initialRadius) * (heightProgression / maxHeight));

            // Spiral motion around the axis
            float spiralAngle = step * getExtraProperty().getValue(2);  // Control the speed of rotation
            if (spiralAngle >= 360.0f) spiralAngle -= 360.0f;

            float totalAngle = spiralAngle + globalAngle;  // Add the global rotation to the spiral angle

            // Calculate spiral motion using axis properties
            double axisX = (float) MathUtils.cos(totalAngle) * radius * (!getAxisProperty().getAxis().isZero() ? getAxisProperty().getAxis().getX() : 1);
            double axisZ = (float) MathUtils.sin(totalAngle) * radius * (!getAxisProperty().getAxis().isZero() ? getAxisProperty().getAxis().getZ() : 1);

            // Randomness to simulate chaotic movement
            float randomnessFactor = getExtraProperty().getValue(1) / 100.0f;
            double randomX = (RandomUtils.getRandomDouble() - 0.5) * randomnessFactor;
            double randomZ = (RandomUtils.getRandomDouble() - 0.5) * randomnessFactor;

            v.setX(axisX + randomX).setY(heightProgression + upwardVelocity).setZ(axisZ + randomZ);

            location.add(v);

            for (int i = 0; i < getDensityProperty().getDensity(2); i++) {  // Adjust the number of dust particles as needed
                float angle = (float) (RandomUtils.getRandomDouble() * MathUtils.PI2);  // Random angle for dust spread
                float distance = (float) (RandomUtils.getRandomDouble() * radius);  // Random distance from the center

                // Calculate offset position
                double dustX = v.getX() + distance * MathUtils.cos(angle);
                double dustZ = v.getZ() + distance * MathUtils.sin(angle);
                double dustY = v.getY() + (RandomUtils.getRandomDouble() * 0.3);  // Slight vertical spread

                location.add(dustX, dustY, dustZ);
                // Add particles to the tornado
                getParticleBuilder(1).sendParticles(location, getPlayers());

                if (heightProgression  > 1.0f) {
                    getParticleBuilder(2).sendParticles(location, getPlayers());
                }

                if (heightProgression  > 8.0f) {
                    getParticleBuilder(3).sendParticles(location, getPlayers());
                }
                location.subtract(dustX, dustY, dustZ);
            }

            // "Dust" particles near the bottom
            float dustBaseHeight = getRadiusProperty().getRadius(6);
            if (heightProgression < dustBaseHeight) {
                float dustBaseRadius = getRadiusProperty().getRadius(4);
                float dustTopRadius = getRadiusProperty().getRadius(5);

                // Dust radius interpolation
                float dustRadius = dustBaseRadius + ((dustTopRadius - dustBaseRadius) * (heightProgression / dustBaseHeight));

                // Calculate dust particle positions with radius
                for (int i = 0; i < getDensityProperty().getDensity(1); i++) {  // Adjust the number of dust particles as needed
                    float angle = (float) (RandomUtils.getRandomDouble() * Utils.PI2);  // Random angle for dust spread
                    float distance = (float) (RandomUtils.getRandomDouble() * dustRadius);  // Random distance from the center

                    // Calculate offset position
                    double dustX = v.getX() + distance * MathUtils.cos(angle);
                    double dustZ = v.getZ() + distance * MathUtils.sin(angle);
                    double dustY = v.getY() + (RandomUtils.getRandomDouble() * 0.1);  // Slight vertical spread

                    location.add(dustX, dustY, dustZ);
                    // Send dust particle
                    getParticleBuilder(4).sendParticles(location, getPlayers());
                    location.subtract(dustX, dustY, dustZ);
                }
            }

            location.subtract(v);

            step++;
            if (step >= 359) {
                step = 0;
            }
        }

        public void onStop() {
            ObjectPoolManager.getDynamicLocationPool().release(location);
            ObjectPoolManager.getVectorPool().release(v);
        }
    }
}
