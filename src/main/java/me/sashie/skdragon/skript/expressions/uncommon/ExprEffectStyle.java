package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.properties.IStyle;
import me.sashie.skdragon.effects.special.Wings;
import me.sashie.skdragon.skript.expressions.CustomEffectPropertyExpression;
import org.jetbrains.annotations.NotNull;

@Name("Particles - Effect style")
@Description({"Get or set the style number of an effect returns 1 if that effect only has one style"})
@Examples({"set style of effect \"uniqueID\" to 2"})
public class ExprEffectStyle extends CustomEffectPropertyExpression<Number> {

	static {
		register(ExprEffectStyle.class, Number.class, "style");
	}

	@Override
	public Number getPropertyValue(EffectData effect) {
		if (effect instanceof IStyle) {
			return ((IStyle) effect).getStyleProperty().getStyle();
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		if (effect instanceof IStyle) {
			Number b = (Number) (delta[0]);
			if (effect instanceof Wings) {
				((Wings) effect).setStyle(b.intValue());
			} else {
				((IStyle) effect).getStyleProperty().setStyle(b.intValue());
			}
		}
	}

	@Override
	public @NotNull Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public String getPropertyName() {
		return "style";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.STYLE;
	}
}
