package me.sashie.skdragon.particles;

import java.util.function.Consumer;

import me.sashie.skdragon.particles.data.ParticleData;
import me.sashie.skdragon.util.DynamicLocation;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import me.sashie.skdragon.particles.data.NormalParticleData;

public class NormalParticle extends ParticleBuilder<NormalParticleData> {

	public NormalParticle() {
		super.initData(new NormalParticleData(this));
	}

	public NormalParticle(Particle particle) {
		this();
		this.data.setParticle(particle);
	}

	public NormalParticle(NormalParticleData inputData) {
		super.initData(inputData);
	}

	public NormalParticle(NormalParticleData inputData, Consumer<NormalParticleData> data) {
		super(inputData);
		data.accept(this.data);
	}

	@Override
	public void sendParticles(DynamicLocation location, Player... player) {
		if (player == null || player.length == 0) {
			location.getWorld().spawnParticle(data.getParticle(), location, data.getAmount(), data.getOffset().getX(), data.getOffset().getY(), data.getOffset().getZ(), data.getSpeed());
		} else {
			for (int i = 0; i < player.length; i++) {
				player[i].spawnParticle(data.getParticle(), location, data.getAmount(), data.getOffset().getX(), data.getOffset().getY(), data.getOffset().getZ(), data.getSpeed());
			}
		}
	}

	@Override
	public void initParticle(ParticleData data) {
		this.data.setParticle(data.getParticle());
		this.data.setAmount(data.getAmount());
		this.data.setOffset(data.getOffset());
		if (data instanceof NormalParticleData) {
			this.data.setSpeed(((NormalParticleData) data).getSpeed());
		}
	}
}
