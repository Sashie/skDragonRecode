package me.sashie.skdragon.particles;

import java.util.function.Consumer;

import me.sashie.skdragon.particles.data.ParticleData;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.ParticleUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.sashie.skdragon.particles.data.MaterialParticleData;

public class MaterialParticle extends ParticleBuilder<MaterialParticleData> {

	public MaterialParticle() {
		super.initData(new MaterialParticleData(this));
	}

	public MaterialParticle(Particle particle) {
		this();
		this.data.setParticle(particle);
	}

	public MaterialParticle(MaterialParticleData inputData) {
		super.initData(inputData);
	}

	public MaterialParticle(MaterialParticleData inputData, Consumer<MaterialParticleData> data) {
		super(inputData);
		data.accept(this.data);
	}

	@Override
	public void sendParticles(DynamicLocation location, Player... player) {
		if (data.getMaterial() == null || data.getMaterial() == Material.AIR) {
			return;
		}
		if (ParticleUtils.isSameParticle(data.getParticle(), ParticleUtils.ITEM_CRACK)) {
			ItemStack item = new ItemStack(data.getMaterial());
			item.setDurability(data.getMaterialData());
			sendParticles(location, player, item);
		} else if (ParticleUtils.isSameParticle(data.getParticle(), ParticleUtils.BLOCK_CRACK) || ParticleUtils.isSameParticle(data.getParticle(), ParticleUtils.BLOCK_DUST) || ParticleUtils.isSameParticle(data.getParticle(), Particle.FALLING_DUST) || ParticleUtils.isSameParticle(data.getParticle(), Particle.DUST_PILLAR)  || ParticleUtils.isSameParticle(data.getParticle(), Particle.BLOCK_MARKER)) {
			Object output = data.getMaterial().createBlockData();
			if (output == null) {
				return;
			}
			sendParticles(location, player, output);
		}
	}

	private void sendParticles(Location location, Player[] player, Object item) {
		if (player == null || player.length == 0) {
			location.getWorld().spawnParticle(data.getParticle(), location, data.getAmount(), data.getOffset().getX(), data.getOffset().getY(), data.getOffset().getZ(), data.getSpeed(), item);
		} else {
			for (int i = 0; i < player.length; i++) {
				player[i].spawnParticle(data.getParticle(), location, data.getAmount(), data.getOffset().getX(), data.getOffset().getY(), data.getOffset().getZ(), data.getSpeed(), item);
			}
		}
	}

	@Override
	public void initParticle(ParticleData data) {
		this.data.setParticle(data.getParticle());
		this.data.setAmount(data.getAmount());
		this.data.setOffset(data.getOffset());
		if (data instanceof MaterialParticleData) {
			this.data.setMaterial(((MaterialParticleData) data).getMaterial());
			this.data.setMaterialData(((MaterialParticleData) data).getMaterialData());
			this.data.setSpeed(((MaterialParticleData) data).getSpeed());
		}
	}
}
