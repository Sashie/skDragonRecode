package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.properties.IFill;
import me.sashie.skdragon.skript.expressions.CustomEffectPropertyExpression;
import org.jetbrains.annotations.NotNull;

@Name("Particles - Effect fill")
@Description({"Some effects particles can fill to a solid shape, such as the circle or other simple effects."})
@Examples({"set fill of effect \"uniqueID\" to true"})
public class ExprEffectFill extends CustomEffectPropertyExpression<Boolean> {

	static {
		register(ExprEffectFill.class, Boolean.class, "fill");
	}

	@Override
	public Boolean getPropertyValue(EffectData effect) {
		if (effect instanceof IFill) {
			return ((IFill) effect).getFillProperty().isFill();
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		if (effect instanceof IFill) {
			Boolean b = (Boolean) delta[0];
			((IFill) effect).getFillProperty().setFill(b);
		}
	}

	@Override
	public @NotNull Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}

	@Override
	public String getPropertyName() {
		return "fill";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.FILL;
	}
}