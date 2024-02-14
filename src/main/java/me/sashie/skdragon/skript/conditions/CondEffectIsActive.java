package me.sashie.skdragon.skript.conditions;

import me.sashie.skdragon.project.EffectAPI;
import me.sashie.skdragon.project.skript.PropertyCondition;
import org.jetbrains.annotations.NotNull;

public class CondEffectIsActive extends PropertyCondition<String> {

	static {
		register(
				CondEffectIsActive.class,
				PropertyType.BE,
				"active",
				"string"
		);
	}

	@Override
	public boolean check(@NotNull String particleId) {
		return EffectAPI.isRunning(particleId);
	}

	@Override
	protected String getPropertyName() {
		return "active";
	}
}