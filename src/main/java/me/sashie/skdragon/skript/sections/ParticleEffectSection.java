package me.sashie.skdragon.skript.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.EffectAPI;
import me.sashie.skdragon.debug.SkriptNode;
import me.sashie.skdragon.effects.ParticleEffect;
import org.bukkit.event.Event;

public class ParticleEffectSection extends EffectSection {

	private static String id;
	private Expression<ParticleEffect> effectType;
	private Expression<String> name;
	private SkriptNode skriptNode;

	static {
		Skript.registerCondition(ParticleEffectSection.class,
				"(create|make|register) [a] [new] particle effect %particleeffect% with id %string%");
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
	public String toString(Event event, boolean debug) {
		return "register particle effect " + effectType.toString(event, debug) + " with id " + name.toString(event, debug);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
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
}