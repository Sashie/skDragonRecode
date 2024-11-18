package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.PropertyAPI;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.properties.IRadius;
import me.sashie.skdragon.skript.expressions.CustomArrayPropertyExpression;
import org.jetbrains.annotations.NotNull;

@Name("Particles - Effect radius")
@Description({"Sets or gets the radius of most effects",
		"Also used for parametric equation effect types as well(can use negative numbers)"})
@Examples({"set radius of effect \"uniqueID\" to 2"})
public class ExprEffectRadius extends CustomArrayPropertyExpression<Number> {

	static {
		register(ExprEffectRadius.class, Number.class, "radius [(1¦start|2¦end|3¦step amount)]");
	}

	@Override
	public Object getPropertyArray(EffectData effect) {
		if (effect instanceof IRadius) {
			return ((IRadius) effect).getRadiusProperty().getArray();
		}
		return null;
	}

	@Override
	public Number getPropertyValue(int propertyNumber, EffectData effect) {
		if (effect instanceof IRadius) {
			return switch (mark) {
				case 1 -> ((IRadius) effect).getRadiusProperty().getStartRadius(propertyNumber);
				case 2 -> ((IRadius) effect).getRadiusProperty().getEndRadius(propertyNumber);
				case 3 -> ((IRadius) effect).getRadiusProperty().getStepAmount(propertyNumber);
				default -> ((IRadius) effect).getRadiusProperty().getRadius(propertyNumber);
			};
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, int propertyNumber, Object delta) {
		Number value = (Number) delta;
		switch (mark) {
			case 1:
				PropertyAPI.setRadiusStart(effect, propertyNumber, value.floatValue());
				break;
			case 2:
				PropertyAPI.setRadiusEnd(effect, propertyNumber, value.floatValue());
				break;
			case 3:
				PropertyAPI.setRadiusStepAmount(effect, propertyNumber, value.floatValue());
				break;
			default:
				PropertyAPI.setRadius(effect, propertyNumber, value.floatValue());
		}
	}

	@Override
	public @NotNull Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public String getPropertyName() {
		return "radius" + switch (mark) {
			case 1 -> " start";
			case 2 -> " end";
			case 3 -> " step amount";
			default -> "";
		};
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.RADIUS;
	}
}
