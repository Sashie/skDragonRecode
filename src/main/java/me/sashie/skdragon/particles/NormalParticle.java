package me.sashie.skdragon.particles;

import java.util.function.Consumer;

import me.sashie.skdragon.particles.data.ParticleData;
import me.sashie.skdragon.util.DynamicLocation;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import me.sashie.skdragon.particles.data.NormalParticleData;

public class NormalParticle extends ParticleBuilder<NormalParticleData> {

	public NormalParticle() {
		super(new NormalParticleData());
	}

    public NormalParticle(Particle particle) {
    	super(new NormalParticleData());
    	this.data.particle = particle;
	}

	public NormalParticle(NormalParticleData inputData) {
		super(inputData);
	}

	public NormalParticle(Consumer<NormalParticleData> data) {
		this(new NormalParticleData(), data);
	}

	public NormalParticle(NormalParticleData inputData, Consumer<NormalParticleData> data) {
		super(inputData);
		data.accept(this.data);
	}

	@Override
	public void sendParticles(DynamicLocation location, Player... player) {
		if (player == null || player.length == 0) {
			location.getWorld().spawnParticle(data.particle, location, data.amount, data.offset.getX(), data.offset.getY(), data.offset.getZ(), data.speed);
		} else {
			for (int i = 0; i < player.length; i++) {
				player[i].spawnParticle(data.particle, location, data.amount, data.offset.getX(), data.offset.getY(), data.offset.getZ(), data.speed);
			}
		}
	}

	@Override
	public void initParticle(ParticleData data) {
		this.data.setParticle(data.getParticle());
		this.data.setAmount(data.getAmount());
		this.data.setOffset(data.getOffset());
		if (data instanceof NormalParticleData) {
			this.data.speed = ((NormalParticleData) data).speed;
		}
	}
}
