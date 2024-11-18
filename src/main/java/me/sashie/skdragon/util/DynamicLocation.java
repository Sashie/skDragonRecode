package me.sashie.skdragon.util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class DynamicLocation extends Location {

	private Entity entity = null;

	/**
	 * This constructor is used to initialize a pool and needs to have world, x, y, z, yaw and pitch set before use
	 */
	public DynamicLocation() {
		super(null, 0, 0, 0, 0, 0);
	}

	public DynamicLocation(@NotNull Location location) {
		super(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	}

	public DynamicLocation(@NotNull Entity entity) {
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
