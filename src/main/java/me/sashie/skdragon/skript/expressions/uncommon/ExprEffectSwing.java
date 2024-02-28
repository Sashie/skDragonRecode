package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.properties.ISwingStep;
import me.sashie.skdragon.skript.expressions.CustomEffectPropertyExpression;
import org.jetbrains.annotations.NotNull;

@Name("Particles - Effect swing")
@Description({"Makes the effect run back and forth once it reaches its density"})
@Examples({"set swing of effect \"uniqueID\" to true"})
public class ExprEffectSwing extends CustomEffectPropertyExpression<Boolean> {

	static {
		register(ExprEffectSwing.class, Boolean.class, "(swing|oscillate)");
	}

	@Override
	public Boolean getPropertyValue(EffectData effect) {
		if (effect instanceof ISwingStep) {
			return ((ISwingStep) effect).getSwingStepProperty().isOscillating();
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		if (effect instanceof ISwingStep) {
			Boolean b = (Boolean) delta[0];
			((ISwingStep) effect).getSwingStepProperty().setOscillation(b);
		}
	}

	@Override
	public @NotNull Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}

	@Override
	public String getPropertyName() {
		return "swing";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.SWING_STEP;
	}
}