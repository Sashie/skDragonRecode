package me.sashie.skdragon.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.util.coll.CollectionUtils;
import me.sashie.skdragon.project.effects.EffectData;
import me.sashie.skdragon.project.util.EffectUtils;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;


@Name("Particles - Location")
@Description({"Similar to the location vector expression this one instead sets the location from either another location or an entity. Adding and removing locations instead of to the location"})
@Examples({"set location of effect \"uniqueID\" to location of player"})
public class ExprEffectLocation extends CustomEffectPropertyExpression<Object> {

	static {
		register(
				ExprEffectLocation.class,
				Object.class,
				"location[s]"
		);
	}

	@Override
	public Object getPropertyValue(EffectData effect) {
		return effect.getLocations();
	}

	@Override
	public void setPropertyValue(EffectData effect, Object[] delta) {
		switch (getMode()) {
			case ADD:
				effect.setLocations(EffectUtils.addArray(effect.getLocations(), EffectUtils.toDynamicLocations(delta)));
				break;
			case REMOVE:
				effect.setLocations(EffectUtils.removeLocations(effect.getLocations(), EffectUtils.toDynamicLocations(delta)));
				break;
			case RESET:
				effect.resetLocations();
				break;
			case SET:
				effect.setLocations(EffectUtils.toDynamicLocations(delta));
				break;
			default:
				break;
		}
	}

	@Override
	public Class<?> @NotNull [] acceptChange(final Changer.@NotNull ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.RESET)
			return CollectionUtils.array(Object[].class);
		return null;
	}

	@Override
	public @NotNull Class<?> getReturnType() {
		return Location.class;
	}

	@Override
	public String getPropertyName() {
		return "location(s)";
	}
}
