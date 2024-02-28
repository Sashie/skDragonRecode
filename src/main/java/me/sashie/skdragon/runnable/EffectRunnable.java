package me.sashie.skdragon.runnable;

import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.properties.IDensity;
import me.sashie.skdragon.util.ParticleUtils;

public class EffectRunnable extends BukkitRunnableTask {

	private int size = 0, iterations = -1;
	private long startTime, duration;

	private EffectData data;

	public EffectRunnable(EffectData data, long duration, int iterations) {
		this.data = data;
		this.duration = duration;
		this.iterations = iterations;
		this.startTime = System.currentTimeMillis();
	}

	@Override
	public void run() {
		synchronized(this.data) {
			if (this.data == null) {
				cancel();
				return;
			}

			if (duration != 0L && System.currentTimeMillis() - startTime >= duration) {
				cancel();
				return;
			}

			this.data.update();

			if (this.data.stopTriggered()) {
				cancel();
				return;
			}

			ParticleUtils.updateColoredParticles(this.data);

			if (duration == 0) {
				if (iterations == -1) {
					//ignore iterations and run endlessly
					return;
				}

				size++;
				if (size >= (this.data instanceof IDensity ? ((IDensity) this.data).getDensityProperty().getDensity(1) : 1)) { // Effects such as circle need a chance to travel around its path, using their density keeps those effects running long enough to finish
					size = 0;
					iterations--;
				}

				if (iterations < 1) {
					cancel();
				}
			}
		}
	}

	public EffectData getData() {
		return this.data;
	}

	public void setData(EffectData data) {
		this.data = data;
	}
}
