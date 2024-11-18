package me.sashie.skdragon.particles.data;

import me.sashie.skdragon.particles.MaterialParticle;
import org.bukkit.Material;

public class MaterialParticleData extends ParticleData<MaterialParticle> {

	private double speed = 0;
	private Material material = null;//Material.DIRT;
	private byte materialData = 0;

	public MaterialParticleData(MaterialParticle particle) {
		super(particle);
	}

	public double getSpeed() {
		return speed;
	}

	public MaterialParticle setSpeed(double speed) {
		this.speed = speed;
		return returnType;
	}

	public Material getMaterial() {
		return material;
	}

	public MaterialParticle setMaterial(Material material) {
		this.material = material;
		return returnType;
	}

	public byte getMaterialData() {
		return materialData;
	}

	public MaterialParticle setMaterialData(byte materialData) {
		this.materialData = materialData;
		return returnType;
	}
}
