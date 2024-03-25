package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.properties.IOrbitStep;
import me.sashie.skdragon.skript.expressions.CustomEffectPropertyExpression;
import org.jetbrains.annotations.NotNull;

@Name("Particles - Effect orbit properties")
@Description({"Get or set the orbit radius or density of an effect that uses this property"})
@Examples({"set orbit radius of effect \"uniqueID\" to 3.0",
		"set orbit density of effect \"uniqueID\" to 50"})
public class ExprEffectOrbitProperties extends CustomEffectPropertyExpression<Number> {

	static {
		register(ExprEffectOrbitProperties.class, Number.class, "orbit (1¦radius|2¦density)");
	}

	@Override
	public Number getPropertyValue(EffectData effect) {
		if (effect instanceof IOrbitStep) {
			return switch (this.mark) {
				case 1 -> ((IOrbitStep) effect).getOrbitStepProperty().getRadius();
				case 2 -> ((IOrbitStep) effect).getOrbitStepProperty().getDensity();
				default -> 0.0f;
			};
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		if (effect instanceof IOrbitStep) {
			Number n = (Number) (delta[0]);
			switch (this.mark) {
				case 1:
					((IOrbitStep) effect).getOrbitStepProperty().setRadius(n.floatValue());
					break;
				case 2:
					((IOrbitStep) effect).getOrbitStepProperty().setDensity(n.intValue());
					break;
			}
		}
	}

	@Override
	public @NotNull Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public String getPropertyName() {
		return "orbit " + switch (this.mark) {
			case 1 -> "radius";
			case 2 -> "density";
			default -> "";
		};
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.ORBIT;
	}
}
