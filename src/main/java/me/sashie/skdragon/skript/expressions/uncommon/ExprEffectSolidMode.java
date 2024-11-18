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

@Name("Particles - Effect solid mode")
@Description({"Some effects particles can either appear solid or moves along its path, such as the circle or other simple effects."})
@Examples({"set solid mode of effect \"uniqueID\" to true"})
public class ExprEffectSolidMode extends CustomEffectPropertyExpression<Boolean> {

	static {
		register(ExprEffectSolidMode.class, Boolean.class, "solid [mode]");
	}

	@Override
	public Boolean getPropertyValue(EffectData effect) {
		if (effect instanceof IStepTypes) {
			return ((IStepTypes) effect).getStepTypesProperty().isSolid();
		}
		return null;
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		PropertyAPI.setSolid(effect, (Boolean) delta[0]);
	}

	@Override
	public @NotNull Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}

	@Override
	public String getPropertyName() {
		return "solid mode";
	}

	@Override
	protected EffectProperty getEffectProperty() {
		return EffectProperty.STEP_TYPES;
	}
}