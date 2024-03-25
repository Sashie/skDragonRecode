package me.sashie.skdragon.effects.properties;

public class StepTypesProperty {

	private boolean solid;
	private boolean fill;

	/**
	 * Whether all particles are played at the same time or one at a time
	 * 
	 * Certain effects shouldn't use this
	 */
	public boolean isSolid() {
		return this.solid;
	}

	public void setSolid(boolean solid) {
		if (solid && this.fill) this.fill = false;
		this.solid = solid;
	}

	/**
	 * Makes the effects particles fill up to density then clears for another loop
	 */
	public boolean isFill() {
		return this.fill;
	}

	public void setFill(boolean fill) {
		if (fill && this.solid) this.solid = false;
		this.fill = fill;
	}
}
