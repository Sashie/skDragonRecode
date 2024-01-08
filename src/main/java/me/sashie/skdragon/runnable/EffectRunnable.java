package me.sashie.skdragon.runnable;

import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.properties.IDensity;
import me.sashie.skdragon.util.ParticleUtils;

public class EffectRunnable extends BukkitRunnableTask {

	private int step, size = 0, iterations = -1;

	private EffectData data;

	public EffectRunnable(EffectData data, int iterations) {
		this.data = data;
		this.iterations = iterations;
	}

	@Override
	public void run() {
		synchronized(this.data) {
			if (this.data == null) {
				cancel();
			}

			this.data.update(step);

			if (this.data.stopTriggered()) {
				cancel();
			}

			ParticleUtils.updateColoredParticles(this.data);

			step++;
			if (step >= 719999) { // Some parametric effects look more interesting this way, why spin around one circle when you can spin around many
				step = 0;
			}

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

	public EffectData getData() {
		return this.data;
	}

	public void setData(EffectData data) {
		this.data = data;
	}
}
