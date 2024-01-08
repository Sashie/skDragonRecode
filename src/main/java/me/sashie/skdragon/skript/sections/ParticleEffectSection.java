package me.sashie.skdragon.skript.sections;

import ch.njol.skript.log.SkriptLogger;
import me.sashie.skdragon.debug.SkriptNode;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.EffectAPI;
import me.sashie.skdragon.SkDragonRecode;
import me.sashie.skdragon.effects.EffectData;
/*
"\t\tcreate a new particle effect meteor:",
"\t\t\tlocation: player",
"\t\t\tid: \"uniqueID\"",
"\t\t\tset title of the embed to \"Google!\"",
"\t\tset {_embed} to last made embed"
*/
import me.sashie.skdragon.effects.ParticleEffect;

public class ParticleEffectSection extends EffectSection {

	private static String id;
	private Expression<ParticleEffect> particleEffect;
	private Expression<String> name;
	private SkriptNode skriptNode;

	static {
		Skript.registerCondition(ParticleEffectSection.class,
				"(create|make|register) [a] [new] particle effect %particleeffect% with id %string%");
	}

	@Override
	public void execute(Event event) {
		EffectData data = this.particleEffect.getSingle(event).getEffectData();
		register(this.name.getSingle(event), data);
		runSection(event);
	}

	public static String getID() {
		return id;
	}

	private void register(String id, EffectData effect) {
		if (EffectAPI.ALL_EFFECTS.containsKey(id)) {
			SkDragonRecode.warn("[REGISTER] Particle Effect '" + EffectAPI.ALL_EFFECTS.get(id).getClass().getName() + "' is being replaced with '" + effect.getClass().getName() + "' for id (" + id + ")", skriptNode);
			if (EffectAPI.isRunning(id))
				EffectAPI.stop(id, skriptNode);
			EffectAPI.ALL_EFFECTS.remove(id);
		}
		EffectAPI.ALL_EFFECTS.put(id, effect);
		ParticleEffectSection.id = id;
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "create particle effect " + particleEffect.toString(event, debug);
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
		particleEffect = (Expression<ParticleEffect>) exprs[0];
		name = (Expression<String>) exprs[1];
		skriptNode = new SkriptNode(SkriptLogger.getNode());

		loadSection(true);
		return true;
	}
}