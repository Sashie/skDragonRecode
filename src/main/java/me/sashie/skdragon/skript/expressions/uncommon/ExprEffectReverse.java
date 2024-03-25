package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.PropertyAPI;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.properties.ISwingStep;
import me.sashie.skdragon.skript.expressions.CustomEffectPropertyExpression;
import org.jetbrains.annotations.NotNull;

@Name("Particles - Effect reverse")
@Description({"Makes the effect run in reverse"})
@Examples({"set reverse of effect \"uniqueID\" to true"})
public class ExprEffectReverse extends CustomEffectPropertyExpression<Boolean> {

	static {
		register(ExprEffectReverse.class, Boolean.class, "reverse");
	}

	@Override
	public Boolean getPropertyValue(EffectData effect) {
		if (effect instanceof ISwingStep) {
			return ((ISwingStep) effect).getSwingStepProperty().isReverse();
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		PropertyAPI.setReverse(effect, (Boolean) delta[0]);
	}

	@Override
	public @NotNull Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}

	@Override
	public String getPropertyName() {
		return "reverse";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.SWING_STEP;
	}
}