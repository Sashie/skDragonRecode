package me.sashie.skdragon.project.effects.properties;

import me.sashie.skdragon.project.debug.ParticleEffectException;

public class TimespanProperty {

	private long[] timespan;

	public long[] getTimespan() {
		return timespan;
	}

	public Long getTimespan(int index) throws ParticleEffectException {
		if (index > this.timespan.length) {
			throw new ParticleEffectException("This effect only uses " + this.timespan.length + " density values not " + index);
			//return null;
		}
		return timespan[index - 1];
	}

	public void setTimespan(int index, long timespan) throws ParticleEffectException {
		if (index > this.timespan.length) {
			throw new ParticleEffectException("This effect only uses " + this.timespan.length + " density values not " + index);
		}
		this.timespan[index - 1] = timespan;
	}

	public void initTimespan(long... timespan) {
		this.timespan = timespan;
	}

}
