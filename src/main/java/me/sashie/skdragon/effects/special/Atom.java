/*
	This file is part of skDragon - A Skript addon
	  
	Copyright (C) 2016-2022  Sashie

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.sashie.skdragon.effects.special;

import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.util.pool.ObjectPoolManager;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.SpecialRadiusDensityEffect;
import me.sashie.skdragon.effects.properties.ExtraProperty;
import me.sashie.skdragon.effects.properties.IExtra;
import me.sashie.skdragon.particles.ColoredParticle;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.EffectUtils;
import me.sashie.skdragon.util.RandomUtils;
import me.sashie.skdragon.util.VectorUtils;


/**
 * 
 *
 */
public class Atom extends SpecialRadiusDensityEffect implements IExtra {

	private ExtraProperty extraProperty;
	Vector vector;

	public Atom() {
		extraProperty = new ExtraProperty();
		this.getExtraProperty().initValue(40f, 30f);
		this.getRadiusProperty().initRadius(.5f, 1.5f);
		this.getDensityProperty().initDensity(20, 20, 20);

		vector = ObjectPoolManager.getVectorPool().acquire();
	}

	@Override
	public void update(DynamicLocation location, float step) {
		double densityAngle = Math.PI / this.getDensityProperty().getDensity(2);
		double angularVelocity = Math.PI / this.getExtraProperty().getValue(1);

		for (int i = 0; i < this.getDensityProperty().getDensity(1); i++) {
			double angle = step * angularVelocity;
			for (int j = 0; j < this.getDensityProperty().getDensity(2); j++) {
				double xRotation = densityAngle * j;
				vector.setX(Math.sin(angle) * ( 0.6 + this.getRadiusProperty().getRadius(2) ));
				vector.setY(Math.cos(angle) * ( 0.6 + this.getRadiusProperty().getRadius(2) ));
				vector.setZ(0);
				VectorUtils.rotateAroundAxisX(vector, xRotation);
				VectorUtils.rotateAroundAxisY(vector, this.getExtraProperty().getValue(2));
				this.getParticleBuilder(2).sendParticles(location.add(vector), this.getPlayers());
				location.subtract(vector);
			}
			step++;
		}

		//Sphere
		for (int i = 0; i < this.getDensityProperty().getDensity(3); i++) {
			vector = RandomUtils.getRandomVector(vector).multiply(this.getRadiusProperty().getRadius(1));
			location.add(vector);
			this.getParticleBuilder(1).sendParticles(location, this.getPlayers());
			location.subtract(vector);
		}
	}

	@Override
	public void onUnregister() {
		ObjectPoolManager.getVectorPool().release(vector);
	}

	@Override
	public EffectProperty[] acceptProperties() {
		return EffectUtils.array(EffectProperty.EXTRA);
	}

	@Override
	public ParticleBuilder<?>[] defaultParticles() {
		return new ParticleBuilder<?>[] { new ColoredParticle(Particle.REDSTONE), new ColoredParticle(Particle.REDSTONE) };
	}

	@Override
	public ExtraProperty getExtraProperty() {
		return extraProperty;
	}

}