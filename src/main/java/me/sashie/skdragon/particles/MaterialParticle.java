package me.sashie.skdragon.particles;

import java.util.function.Consumer;

import me.sashie.skdragon.particles.data.ParticleData;
import me.sashie.skdragon.util.DynamicLocation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.sashie.skdragon.particles.data.MaterialParticleData;

public class MaterialParticle extends ParticleBuilder<MaterialParticleData> {

	public MaterialParticle() {
		super(new MaterialParticleData());
	}

	public MaterialParticle(Particle particle) {
		super(new MaterialParticleData());
		this.data.particle = particle;
	}

	public MaterialParticle(MaterialParticleData inputData) {
		super(inputData);
	}

	public MaterialParticle(Consumer<MaterialParticleData> data) {
		this(new MaterialParticleData(), data);
	}

	public MaterialParticle(MaterialParticleData inputData, Consumer<MaterialParticleData> data) {
		super(inputData);
		data.accept(this.data);
	}

	@Override
	public void sendParticles(DynamicLocation location, Player... player) {
		if (data.material == null || data.material == Material.AIR) {
			return;
		}
		if (data.particle == Particle.ITEM_CRACK) {
			ItemStack item = new ItemStack(data.material);
			item.setDurability(data.materialData);
			//player.spawnParticle(data.particle, location, data.amount, data.offset.getX(), data.offset.getY(), data.offset.getZ(), data.speed, item);
			sendParticles(location, player, item);
			return;
		}
		Object output = null;
		if (data.particle == Particle.BLOCK_CRACK || data.particle == Particle.BLOCK_DUST || data.particle == Particle.FALLING_DUST) {

			output = data.material.createBlockData();
			if (output == null) {
				return;
			}
		}
		//player.spawnParticle(data.particle, location, data.amount, data.offset.getX(), data.offset.getY(), data.offset.getZ(), data.speed, output);
		sendParticles(location, player, output);
	}

	private void sendParticles(Location location, Player[] player, Object item) {
		if (player == null || player.length == 0) {
			location.getWorld().spawnParticle(data.particle, location, data.amount, data.offset.getX(), data.offset.getY(), data.offset.getZ(), data.speed, item);
		} else {
			for (int i = 0; i < player.length; i++) {
				player[i].spawnParticle(data.particle, location, data.amount, data.offset.getX(), data.offset.getY(), data.offset.getZ(), data.speed, item);
			}
		}
	}

	@Override
	public void initParticle(ParticleData data) {
		this.data.setParticle(data.getParticle());
		this.data.setAmount(data.getAmount());
		this.data.setOffset(data.getOffset());
		if (data instanceof MaterialParticleData) {
			this.data.material = ((MaterialParticleData) data).material;
			this.data.materialData = ((MaterialParticleData) data).materialData;
			this.data.speed = ((MaterialParticleData) data).speed;
		}
	}
}
