/*
	This file is part of skDragon - A Skript addon
	Originally written for DragonsphereZ by bi0
	  
	Copyright (C) 2016  Sashie

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

package me.sashie.skdragon.util;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class DynamicLocation extends Location {

	private Entity entity = null;

	/**
	 * This constructor is used to initialize a pool and needs to have world, x, y, z, yaw and pitch set before use
	 */
	public DynamicLocation() {
		super(null, 0, 0, 0, 0, 0);
	}


	public DynamicLocation(Location location) {
		super(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	}
	
	public DynamicLocation(Entity entity) {
		super(entity.getWorld(), entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ(), entity.getLocation().getYaw(), entity.getLocation().getPitch());
		this.entity = entity;
	}
	
	public DynamicLocation update() {
		if (entity != null) {
			this.setX(entity.getLocation().getX());
			this.setY(entity.getLocation().getY());
			this.setZ(entity.getLocation().getZ());
			this.setYaw(entity.getLocation().getYaw());
			this.setPitch(entity.getLocation().getPitch());
		}
		return this;
	}

	public boolean isDynamic() {
		return entity != null;
	}

	public Entity getEntity() {
		return entity;
	}

	@Override
	public Vector toVector() {
		return new Vector(this.getX(), this.getY(), this.getZ());
	}

	public DynamicLocation lerp(final DynamicLocation target, float alpha) {
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();
		
		x += alpha * (target.getX() - this.getX());
		y += alpha * (target.getY() - this.getZ());
		z += alpha * (target.getZ() - this.getZ());

		this.setX(x);
		this.setY(y);
		this.setZ(z);
		
		return this;
	}

	@Override
	public DynamicLocation add(final Vector vec) {
		this.setX(this.getX() + vec.getX());
		this.setY(this.getY() + vec.getY());
		this.setZ(this.getZ() + vec.getZ());
		return this;
	}

	@Override
	public DynamicLocation add(final double x, final double y, final double z) {
		this.setX(this.getX() + x);
		this.setY(this.getY() + y);
		this.setZ(this.getZ() + z);
		return this;
	}

	@Override
	public DynamicLocation subtract(final Vector vec) {
		this.setX(this.getX() - vec.getX());
		this.setY(this.getY() - vec.getY());
		this.setZ(this.getZ() - vec.getZ());
		return this;
	}
	@Override
	public DynamicLocation subtract(final double x, final double y, final double z) {
		this.setX(this.getX() - x);
		this.setY(this.getY() - y);
		this.setZ(this.getZ() - z);
		return this;
	}
	@Override
	public DynamicLocation multiply(final double m) {
		this.setX(this.getX() * m);
		this.setY(this.getY() * m);
		this.setZ(this.getZ() * m);
		return this;
	}

	@Override
	public DynamicLocation clone() {
		return new DynamicLocation(this);
	}

	public static DynamicLocation init(Object center) {
		if (center instanceof Entity) return new DynamicLocation((Entity) center);
		else if (center instanceof Location) return new DynamicLocation((Location) center);
		else throw new IllegalArgumentException("[skDragon] The input object is not of type Entity or Location");
	}

	public DynamicLocation init(Entity entity) {
		this.entity = entity;
		return init(entity.getLocation());
	}

	public DynamicLocation init(Location location) {
		this.setWorld(location.getWorld());
		this.setX(location.getX());
		this.setY(location.getY());
		this.setZ(location.getZ());
		this.setYaw(location.getYaw());
		this.setPitch(location.getPitch());
		return this;
	}

	public void set(double x, double y, double z) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}

	public void reset() {
		this.entity = null;
		this.set(0, 0, 0);
		this.setPitch(0);
		this.setYaw(0);
	}
}
