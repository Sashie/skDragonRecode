package me.sashie.skdragon.skript.expressions.uncommon;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.PropertyAPI;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.EffectProperty;
import me.sashie.skdragon.effects.properties.IStepTypes;
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
		if (effect instanceof IStepTypes) {
			return ((IStepTypes) effect).getStepTypesProperty().isFill();
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		PropertyAPI.setFill(effect, (Boolean) delta[0]);
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
		return EffectProperty.STEP_TYPES;
	}
}