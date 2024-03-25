 package me.sashie.skdragon.effects.special;

 import me.sashie.skdragon.effects.EffectProperty;
 import me.sashie.skdragon.effects.SpecialRadiusDensityEffect;
 import me.sashie.skdragon.effects.properties.*;
 import me.sashie.skdragon.particles.ColoredParticle;
 import me.sashie.skdragon.particles.ParticleBuilder;
 import me.sashie.skdragon.util.*;
 import me.sashie.skdragon.util.pool.ObjectPoolManager;
 import org.bukkit.Particle;
 import org.bukkit.util.Vector;

 import java.util.HashSet;
 import java.util.Set;

 public class Planet extends SpecialRadiusDensityEffect implements IAxis, IVelocity, IExtra, IOrbitStep, IStep {

	 AxisProperty axisProperty;
	 VelocityProperty velocityProperty;
	 ExtraProperty extraProperty;
	 OrbitStepProperty orbitStepProperty;
	 StepProperty stepProperty;
	 float step;
	 Set<Vector> cacheGreen = new HashSet<Vector>();
	 Set<Vector> cacheBlue = new HashSet<Vector>();
	 Set<Vector> cache = new HashSet<Vector>();

	 public Planet() {
		 axisProperty = new AxisProperty();
		 velocityProperty = new VelocityProperty();
		 extraProperty = new ExtraProperty();
		 orbitStepProperty = new OrbitStepProperty();
		 stepProperty = new StepProperty();

		 this.getRadiusProperty().initRadius(1.5f);
		 this.getDensityProperty().initDensity(10);
		 this.getExtraProperty().initValue(10, 20);
	 }

	 @Override
	 public void init() {
		 cacheGreen.clear();
		 cacheBlue.clear();
		 int sqrtParticles = (int) Math.sqrt(this.getDensityProperty().getDensity(1));
		 double theta = 0, phi, thetaStep = Math.PI / sqrtParticles, phiStep = Utils.PI2 / sqrtParticles;

		 for (int i = 0; i < sqrtParticles; i++) {
			 theta += thetaStep;
			 phi = 0;
			 for (int j = 0; j < sqrtParticles; j++) {
				 phi += phiStep;
				 double x = this.getRadiusProperty().getRadius(1) * Math.sin(theta) * Math.cos(phi);
				 double y = this.getRadiusProperty().getRadius(1) * Math.sin(theta) * Math.sin(phi);
				 double z = this.getRadiusProperty().getRadius(1) * Math.cos(theta);
				 cache.add(ObjectPoolManager.getVectorPool().acquire(x, y, z));
			 }
		 }

		 float increase = this.extraProperty.getValue(1) / this.extraProperty.getValue(2);

		 for (int i = 0; i < this.extraProperty.getValue(2); i++) {
			 double r1 = RandomUtils.getRandomAngle(), r2 = RandomUtils.getRandomAngle(), r3 = RandomUtils.getRandomAngle();
			 for (Vector v : cache) {
				 if (v.getY() > 0) {
					 v.setY(v.getY() + increase);
				 } else {
					 v.setY(v.getY() - increase);
				 }
				 if (i != this.extraProperty.getValue(2) - 1) {
					 VectorUtils.rotateVector(v, r1, r2, r3);
				 }
			 }
		 }

		 float minSquared = Float.POSITIVE_INFINITY, maxSquared = Float.NEGATIVE_INFINITY;

		 for (Vector current : cache) {
			 float lengthSquared = (float) current.lengthSquared();
			 if (minSquared > lengthSquared) {
				 minSquared = lengthSquared;
			 }
			 if (maxSquared < lengthSquared) {
				 maxSquared = lengthSquared;
			 }
		 }
		 // COLOR PARTICLES
		 float average = (minSquared + maxSquared) / 2;
		 for (Vector v : cache) {
			 float lengthSquared = (float) v.lengthSquared();
			 if (lengthSquared >= average) {
				 cacheGreen.add(v);
			 } else {
				 cacheBlue.add(v);
			 }
		 }
	 }

	 @Override
	 public void update(DynamicLocation location) {
		 step = stepProperty.getStep();
		 this.orbitStepProperty.updateOrbit(location);

		 if (this.velocityProperty.isRotating()) {
			 double rotX = this.velocityProperty.getAngularVelocityX() * step;
			 double rotY = this.velocityProperty.getAngularVelocityY() * step;
			 double rotZ = this.velocityProperty.getAngularVelocityZ() * step;

			 for (Vector v : cache) {
				 VectorUtils.rotateVector(v, rotX, rotY, rotZ);
			 }
		 }

		 for (Vector v : cacheGreen) {
			 getParticleBuilder(1).sendParticles(location.add(v), getPlayers());
			 location.subtract(v);
		 }

		 for (Vector v : cacheBlue) {
			 getParticleBuilder(2).sendParticles(location.add(v), getPlayers());
			 location.subtract(v);
		 }
		 this.stepProperty.update();
	 }

	 @Override
	 public void onUnregister() {
		 for (Vector v : cache) {
			 ObjectPoolManager.getVectorPool().release(v);
		 }
	 }

	 @Override
	 public EffectProperty[] acceptProperties() {
		 return EffectUtils.array(EffectProperty.EXTRA, EffectProperty.AXIS, EffectProperty.ROTATE_VELOCITY, EffectProperty.ORBIT);
	 }

	 @Override
	 public ParticleBuilder<?>[] defaultParticles() {
		 return new ParticleBuilder<?>[] { new ColoredParticle(Particle.REDSTONE), new ColoredParticle(Particle.FLAME) };
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
	 public OrbitStepProperty getOrbitStepProperty() {
		 return orbitStepProperty;
	 }

	 @Override
	 public StepProperty getStepProperty() {
		 return stepProperty;
	 }
 }