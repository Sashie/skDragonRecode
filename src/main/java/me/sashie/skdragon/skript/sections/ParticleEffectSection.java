package me.sashie.skdragon.skript.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.api.EffectAPI;
import me.sashie.skdragon.api.debug.SkriptNode;
import me.sashie.skdragon.api.effects.ParticleEffect;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class ParticleEffectSection extends EffectSection {

	static {
		Skript.registerCondition(
				ParticleEffectSection.class,
				"(create|make|register) [a] [new] particle effect %particleeffect% with id %string%"
		);
	}

	private static String id;
	private Expression<ParticleEffect> effectType;
	private Expression<String> name;
	private SkriptNode skriptNode;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?> @NotNull [] exprs, int i, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
		if (checkIfCondition())
			return false;
		if (!hasSection()) {
			Skript.error("A particle effect creation scope is useless without any content!");
			return false;
		}

		effectType = (Expression<ParticleEffect>) exprs[0];
		name = (Expression<String>) exprs[1];
		skriptNode = new SkriptNode(SkriptLogger.getNode());

		loadSection(true);
		return true;
	}

	@Override
	public void execute(Event event) {
		String id = this.name.getSingle(event);
		ParticleEffect effectType = this.effectType.getSingle(event);

		if (id == null || effectType == null)
			return;

		ParticleEffectSection.id = id;
		EffectAPI.register(id, effectType, skriptNode);

		runSection(event);
	}

	public static String getID() {
		return id;
	}

	@Override
	public @NotNull String toString(Event event, boolean debug) {
		return "register particle effect " + effectType.toString(event, debug) + " with id " + name.toString(event, debug);
	}

}