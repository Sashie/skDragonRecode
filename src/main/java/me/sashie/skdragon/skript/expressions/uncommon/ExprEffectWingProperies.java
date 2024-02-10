

package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.WingsEffect;
import me.sashie.skdragon.skript.expressions.CustomEffectPropertyExpression;
import org.jetbrains.annotations.NotNull;



@Name("Particles - Wing effect properties")
@Description({"Get or set the angle, flap range and flap step of a wing effect",
		" - Flap range is how far the wings will flap back and forth, default is 20",
		" - Flap step is how fast the wings flap, default is 0.3"})
@Examples({"set wing angle of effect \"uniqueID\" to 10",
		"set wing flap range of effect \"uniqueID\" to 10",
		"set wing flap step of effect \"uniqueID\" to 1"})
public class ExprEffectWingProperies extends CustomEffectPropertyExpression<Number> {

	static {
		register(ExprEffectWingProperies.class, Number.class, "wing (1¦angle|2¦flap range|3¦flap step)");
	}

	@Override
	public Number getPropertyValue(EffectData effect) {
		if (effect instanceof WingsEffect) {
			return switch (this.mark) {
				case 1 -> ((WingsEffect) effect).getWingAngle();
				case 2 -> ((WingsEffect) effect).getFlapRange();
				case 3 -> ((WingsEffect) effect).getFlapStep();
				default -> 0.0f;
			};
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		if (effect instanceof WingsEffect) {
			Number n = (Number) (delta[0]);
			switch (this.mark) {
				case 1:
					((WingsEffect) effect).setWingAngle(n.floatValue());
					break;
				case 2:
					((WingsEffect) effect).setFlapRange(n.floatValue());
					break;
				case 3:
					((WingsEffect) effect).setFlapStep(n.floatValue());
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
		return "wing " + switch (this.mark) {
			case 1 -> "angle";
			case 2 -> "flap range";
			case 3 -> "flap step";
			default -> "";
		};
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.WINGS;
	}
}
