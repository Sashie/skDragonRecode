package me.sashie.skdragon.effects.properties;

public class StyleProperty {

	private int style;
	
	public int getStyle() {
		if (style == 0)
			return 1;
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}
	
}
