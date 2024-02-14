package me.sashie.skdragon.api.effects;

import me.sashie.skdragon.api.effects.properties.*;
import me.sashie.skdragon.api.util.DynamicLocation;
import me.sashie.skdragon.api.util.EffectUtils;

public abstract class ScanEffect extends EffectData implements IRadius, IDensity, IExtra {

	private RadiusProperty radiusProperty;
	private DensityProperty densityProperty;
	private ExtraProperty extraProperty;

	private boolean init;
	boolean scan = false;

	public ScanEffect() {
		radiusProperty = new RadiusProperty();
		densityProperty = new DensityProperty();
		extraProperty = new ExtraProperty();

		init = false;
	}

	public void init(DynamicLocation location) {
	}

	;

	public abstract void update(DynamicLocation location, float step);

	@Override
	public void update(float step) {

		for (int i = 0; i < this.getLocations().length; i++) {
			if (!init) {
				if (!this.getLocations()[i].isDynamic())
					this.getLocations()[i].add(this.getDisplacement().getX(), this.getDisplacement().getY(), this.getDisplacement().getZ());
				init(this.getLocations()[i]);
				if (i == this.getLocations().length) {
					init = true;
				}
			} else {
				this.getLocations()[i].update();
				if (this.getLocations()[i].isDynamic())
					this.getLocations()[i].add(this.getDisplacement().getX(), this.getDisplacement().getY(), this.getDisplacement().getZ());
				update(this.getLocations()[i], step);
			}
		}
	}

	public void setScan(boolean scan) {
		this.scan = scan;
	}

	public boolean getScan() {
		return scan;
	}

	protected float heightCounter;
	protected float heightCounterRev = this.getExtraProperty().getValue(1);
	protected float stepHeight = (this.getExtraProperty().getValue(1) / this.getDensityProperty().getDensity(1));
	float stepHeightDown = (this.getExtraProperty().getValue(1) / this.getDensityProperty().getDensity(1));

	public void heightScan() {
		scan(heightCounter, stepHeight, this.getExtraProperty().getValue(1), false);
	}

	public void heightScanRev() {
		scan(heightCounterRev, stepHeightDown, this.getExtraProperty().getValue(1), true);
	}

	protected float midPoint = this.getExtraProperty().getValue(1) / 2;
	protected float middleCounterUp = midPoint;
	protected float middleCounterDown = midPoint;
	float stepHalfUp = (midPoint / this.getDensityProperty().getDensity(1));
	float stepHalfDown = (midPoint / this.getDensityProperty().getDensity(1));

	public void middleScanUp() {
		if (scan) {
			if (middleCounterUp > this.getExtraProperty().getValue(1))
				stepHalfUp = -stepHalfUp;
			else if (middleCounterUp < midPoint)
				stepHalfUp = -stepHalfUp;
		} else {
			if (middleCounterUp > this.getExtraProperty().getValue(1))
				middleCounterUp = midPoint;
			if (middleCounterUp < midPoint)
				middleCounterUp = this.getExtraProperty().getValue(1);
		}
		middleCounterUp += stepHalfUp;
	}

	public void middleScanDown() {
		scan(middleCounterDown, stepHalfDown, midPoint, true);
	}

	protected float radiusCounter;
	protected float radiusCounterRev = this.getRadiusProperty().getRadius(1);
	float stepRadius = (this.getRadiusProperty().getRadius(1) / this.getDensityProperty().getDensity(1));
	float stepRadiusRev = (this.getRadiusProperty().getRadius(1) / this.getDensityProperty().getDensity(1));

	public void radiusScan() {
		scan(radiusCounter, stepRadius, this.getRadiusProperty().getRadius(1), false);
	}

	public void radiusScanRev() {

		scan(radiusCounterRev, stepRadiusRev, this.getRadiusProperty().getRadius(1), true);
	}

	public void scan(float counter, float step, float radius, boolean reverse) {
		if (scan) {
			if (counter > radius || counter < 0)
				step = -step;
		} else {
			if (counter > radius)
				counter = 0;
			else if (counter < 0)
				counter = radius;
		}
		if (reverse)
			counter -= step;
		else
			counter += step;
	}

	@Override
	public EffectProperty[] acceptDefaultProperties() {
		return EffectUtils.array(EffectProperty.RADIUS, EffectProperty.DENSITY, EffectProperty.EXTRA, EffectProperty.DISPLACEMENT);
	}

	@Override
	public RadiusProperty getRadiusProperty() {
		return radiusProperty;
	}

	@Override
	public DensityProperty getDensityProperty() {
		return densityProperty;
	}

	@Override
	public ExtraProperty getExtraProperty() {
		return extraProperty;
	}
}