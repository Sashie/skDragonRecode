package me.sashie.skdragon.effects.special;

import me.sashie.skdragon.effects.properties.IStyle;
import me.sashie.skdragon.effects.properties.StyleProperty;
import me.sashie.skdragon.particles.ParticleBuilder;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.WingsEffect;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.EffectUtils;
import me.sashie.skdragon.util.VectorUtils;



/**
 * Created by Sashie on 3/16/2018.
 */

public class Wings extends WingsEffect implements IStyle {

	private static double DEFAULT_ANGLE = 110.0f;
	private StyleProperty styleProperty;
	boolean[][] shape1,shape2, shape3, shape4;

	public Wings() {
		// height, space, back spacing
		this.styleProperty = new StyleProperty();
		this.getExtraProperty().initValue(0.2f, 0.2f, 0.0f);
		setStyle(1);
	}

	@Override
	public void onUnregister() {

	}

	@Override
	public void updateWings(DynamicLocation location, float flap) {
		
		if (this.getParticleBuilder(1) != null)
			wing(location, this.getParticleBuilder(1), shape1, flap);
		if (this.getParticleBuilder(2) != null)
			wing(location, this.getParticleBuilder(2), shape2, flap);
		if (this.getParticleBuilder(3) != null)
			wing(location, this.getParticleBuilder(3), shape3, flap);
		if (this.getParticleBuilder(4) != null && shape4 != null)
			wing(location, this.getParticleBuilder(4), shape4, flap);

	}

	private void wing(DynamicLocation location, ParticleBuilder<?> particle, boolean[][] shape, float flap) {
		double x;
		double space = this.getExtraProperty().getValue(2);
		double back = this.getExtraProperty().getValue(3);
		final double defX = x = location.getX() + space;
		double y = location.getY() + 2.7 + this.getExtraProperty().getValue(1);

		for (int i = 0; i < shape.length; ++i) {
			for (int j = 0; j < shape[i].length; ++j) {
				if (shape[i][j]) {
					final Location target = location.clone();
					target.setX(x);
					target.setY(y);
					Vector vR = target.toVector().subtract(location.toVector());
					Vector vL = target.toVector().subtract(location.toVector());
					final Vector v2 = VectorUtils.getBackVector(location);
					double rightWing = Math.toRadians(location.getYaw() + 90.0f - ((DEFAULT_ANGLE + this.getWingAngle()) - flap));
					double leftWing = Math.toRadians(location.getYaw() + 90.0f + ((DEFAULT_ANGLE + this.getWingAngle()) - flap));
					vR = VectorUtils.rotateAroundAxisY(vR, -rightWing);
					vL = VectorUtils.rotateAroundAxisY(vL, -leftWing);
					v2.setY(0).multiply(-0.2 + back);
					particle.sendParticles(location.clone().add(vL).add(v2), this.getPlayers());
					particle.sendParticles(location.clone().add(vR).add(v2), this.getPlayers());
				}
				x += space;
			}
			y -= space;
			x = defX;
		}

	}

	@Override
	public EffectProperty[] acceptProperties() {
		return EffectUtils.array(EffectProperty.EXTRA, EffectProperty.STYLE, EffectProperty.WINGS);
	}

	@Override
	public ParticleBuilder<?>[] defaultParticles() {
		return new ParticleBuilder<?>[] { new ColoredParticle(Particle.REDSTONE), new ColoredParticle(Particle.REDSTONE), new ColoredParticle(Particle.REDSTONE), new ColoredParticle(Particle.REDSTONE) };
	}

	public void setStyle(int wingStyle) {
		this.getStyleProperty().setStyle(wingStyle);
		boolean x = true;
		boolean o = false;
		if (wingStyle <= 1) {
			shape1 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ x, o, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape2 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape3 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o } };
		} else if (wingStyle == 2) {
			//SaarBJ design
			shape1 = new boolean[][] {
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ x, o, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ x, o, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ x, o, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape2 = new boolean[][] {
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape3 = new boolean[][] {
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
				this.getExtraProperty().setValue(1, this.getExtraProperty().getValue(1) - 0.4f);
		} else if (wingStyle == 3) {
			//Epicskymi design
			shape1 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, x, x, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, x, x, o, o, o, o, o, o, o }, 
				{ o, o, o, x, x, o, x, o, o, o, o, o, o, o }, 
				{ o, o, x, x, o, o, x, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, o, x, o, o, o, o, o, o, o }, 
				{ x, x, o, o, o, o, x, o, o, o, o, o, o, o }, 
				{ x, o, o, o, o, o, x, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, x, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, x, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, x, x, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, x, o, o, o, o, o, o, o, o, o } };
			shape2 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape3 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, o, o, o, o, o, o, o, o, o, o, o, o } };
		} else if (wingStyle == 4) {
			shape1 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, x, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, x, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ x, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o } };
			shape2 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, x, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape3 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
		} else if (wingStyle == 5) {
			shape1 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o },
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o },
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o },
	        	{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
	        	{ o, o, o, o, x, x, x, x, o, o, o, o, o, o }, 
	        	{ o, o, x, x, o, o, o, o, o, o, o, o, o, o }, 
	        	{ x, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
	        	{ x, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
	        	{ o, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
	        	{ o, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
	        	{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
	        	{ o, o, o, x, x, o, o, o, o, o, o, o, o, o }, 
	        	{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape2 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o },
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o },
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o },
	        	{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
	        	{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
	        	{ o, o, o, o, x, x, x, o, o, o, o, o, o, o }, 
	        	{ o, o, x, x, o, o, o, o, o, o, o, o, o, o }, 
	        	{ o, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
	        	{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
	        	{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
	        	{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
	        	{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
	        	{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape3 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o },
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o },
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o },
	        	{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
	        	{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
	        	{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
	        	{ o, o, o, o, x, x, o, o, o, o, o, o, o, o }, 
	        	{ o, o, x, x, x, o, o, o, o, o, o, o, o, o }, 
	        	{ o, o, o, x, x, o, o, o, o, o, o, o, o, o }, 
	        	{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
	        	{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
	        	{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
	        	{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
		} else if (wingStyle == 6) {
			shape1 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ x, o, o, o, o, o, x, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, x, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, x, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, x, o, o, o, o, o, o },
				{ o, o, o, o, o, o, o, x, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, x, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape2 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ x, o, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ x, o, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, x, o, o, o, o, o, o, o, o },
				{ o, o, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape3 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, o, o, o, o, o, o, o, o, o },
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
		} else if (wingStyle == 7) {
			//Lopstv design
			shape1 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape2 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, x, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, x, x, o, o, o, o, o }, 
				{ o, o, o, o, o, o, x, x, o, o, o, o, o, o }, 
				{ o, o, o, o, o, x, x, x, o, o, o, o, o, o }, 
				{ o, o, o, x, x, x, x, o, o, o, o, o, o, o }, 
				{ o, o, x, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape3 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, x, x, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, x, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, x, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
		} else if (wingStyle == 8) {
			shape1 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, x, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, x, o, o, o, o, o, o, o }, 
				{ o, x, o, o, x, o, x, o, o, o, o, o, o, o }, 
				{ x, o, o, x, o, o, x, o, o, o, o, o, o, o }, 
				{ x, o, x, o, o, x, o, o, o, o, o, o, o, o }, 
				{ x, o, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape2 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape3 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, x, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, x, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, x, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
		} else if (wingStyle == 9) {
			shape1 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ x, o, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape2 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape3 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
		} else if (wingStyle == 10) {
			shape1 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape2 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape3 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
		} else if (wingStyle == 11) {
			//iCraftKSA
			shape1 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, x, o, o, o, o, o, o, o, o }, 
				{ x, o, o, x, x, o, o, o, o, o, o, o, o, o }, 
				{ x, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ x, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, x, o, x, o, o, o, o, o, o, o, o, o }, 
				{ x, o, o, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape2 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape3 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
		} else if (wingStyle == 12) {
			//iCraftKSA
			shape1 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, x, o, x, x, o, o, o, o, o, o, o }, 
				{ x, o, o, x, x, o, x, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, x, o, o, o, o, o, o, o, o }, 
				{ x, o, x, x, x, o, x, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, x, x, o, o, o, o, o, o, o } };
			shape2 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, x, o, o, o, o, o, o, o, o }, 
				{ x, o, o, x, x, o, o, o, o, o, o, o, o, o }, 
				{ x, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ x, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, x, o, x, o, o, o, o, o, o, o, o, o }, 
				{ x, o, o, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape3 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
		} else if (wingStyle == 13) {
			//Byakuya
			shape1 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, x, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, o, x, o, o, o, o, o, o, o }, 
				{ x, o, o, o, o, o, o, x, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape2 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, x, x, x, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, o, x, o, o, o, o, o, o, o }, 
				{ x, x, o, o, o, o, o, x, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, x, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, x, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape3 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, x, x, o, o, o, o, o, o, o, o }, 
				{ o, o, x, x, x, x, x, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, o, x, x, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, x, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
		} else if (wingStyle == 14) {
			shape1 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, x, o, o, x, o, o, o, o, o }, 
				{ x, o, o, o, o, o, x, x, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape2 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ x, o, o, o, o, o, x, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape3 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
		} else if (wingStyle == 15) {
			shape1 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ x, o, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ x, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape2 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, x, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, o, x, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape3 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
		} else if (wingStyle == 16) {
			shape1 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, o, o, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, x, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ x, o, o, o, o, o, x, o, o, o, o, o, o, o }, 
				{ x, o, x, o, x, o, x, o, o, o, o, o, o, o }, 
				{ o, x, o, x, o, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape2 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, x, x, o, o, o, o, o, o, o, o, o, o }, 
				{ x, x, o, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape3 = new boolean[][] { 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, x, x, x, x, o, o, o, o, o, o, o, o, o }, 
				{ o, x, o, x, o, x, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
				{ o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
		} else {
			shape1 = new boolean[][] { 
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
                { o, o, o, o, o, o, o, o, o, o, o, x, x, x }, 
                { o, o, o, o, o, o, o, o, o, o, x, x, x, x }, 
                { o, o, o, o, o, o, o, o, o, x, x, x, x, x }, 
                { o, o, o, o, o, o, o, o, x, x, o, o, o, o }, 
                { o, o, o, o, o, o, o, x, x, o, o, o, o, o }, 
                { o, o, o, o, o, o, x, x, o, o, o, o, o, o }, 
                { o, o, o, o, o, x, x, o, o, o, o, o, o, o }, 
                { o, o, o, o, x, x, o, o, o, o, o, o, o, o }, 
                { o, o, o, x, x, o, o, o, o, o, o, o, o, o }, 
                { o, o, x, x, o, o, o, o, o, o, o, o, o, o }, 
                { o, x, x, o, o, o, o, o, o, o, o, o, o, o }, 
                { o, x, o, o, o, o, o, o, o, o, o, o, o, o } };
            shape2 = new boolean[][] { 
                { o, o, o, o, o, o, o, o, o, o, o, x, x, x }, 
                { o, o, o, o, o, o, o, o, o, o, x, o, o, o }, 
                { o, o, o, o, o, o, o, o, o, x, o, o, o, o }, 
                { o, o, o, o, o, o, o, o, x, o, o, o, o, o }, 
                { o, o, o, o, o, o, o, x, o, o, o, o, o, o }, 
                { o, o, o, o, o, o, x, o, o, o, o, o, o, o }, 
                { o, o, o, o, o, x, o, o, o, o, o, o, o, o }, 
                { o, o, o, o, x, o, o, o, o, o, o, o, o, o }, 
                { o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
                { o, o, x, o, o, o, o, o, o, o, o, o, o, o }, 
                { o, x, o, o, o, o, o, o, o, o, o, o, o, o }, 
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
            shape3 = new boolean[][] { 
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
                { o, o, o, o, o, o, o, o, o, o, o, x, o, o }, 
                { o, o, o, o, o, o, o, o, o, o, o, x, o, o }, 
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
                { o, o, o, o, o, o, o, x, o, o, o, o, o, o }, 
                { o, o, o, o, o, o, o, x, o, o, o, o, o, o }, 
                { o, o, o, o, o, o, o, x, o, o, o, o, o, o }, 
                { o, o, o, x, o, o, o, x, o, o, o, o, o, o }, 
                { o, o, o, x, o, o, o, o, o, o, o, o, o, o }, 
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o } };
			shape4 = new boolean[][] { 
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
                { o, o, o, o, o, o, o, o, o, o, x, x, x, o }, 
                { o, o, o, o, o, o, o, o, o, o, o, x, o, o }, 
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
                { o, o, o, o, o, o, o, o, o, o, o, o, o, o }, 
                { o, o, x, o, x, o, x, x, x, o, o, o, o, o }, 
                { o, o, o, x, o, o, o, x, o, o, o, o, o, o } };
		}
		
		
	}

	@Override
	public StyleProperty getStyleProperty() {
		return styleProperty;
	}
}
