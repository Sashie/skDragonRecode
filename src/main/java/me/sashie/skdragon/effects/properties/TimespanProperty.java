package me.sashie.skdragon.effects.properties;

import me.sashie.skdragon.SkDragonRecode;

public class TimespanProperty {

	private long[] timespan;
	
	public long[] getTimespan() {
		return timespan;
	}

	public Long getTimespan(int index) {
		if (index > this.timespan.length) {
			SkDragonRecode.error("This effect only uses " + this.timespan.length + " timespan values not " + index);
			return 0L;
		}
		return timespan[index - 1];
	}

	public void setTimespan(int index, long timespan) {
		if (index > this.timespan.length) {
			SkDragonRecode.error("This effect only uses " + this.timespan.length + " timespan values not " + index);
			return;
		}
		this.timespan[index - 1] = timespan;
	}

	public void initTimespan(long... timespan) {
		this.timespan = timespan;
	}
	
}
