package me.sashie.skdragon.skript.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.debug.SkriptNode;
import me.sashie.skdragon.particles.*;
import me.sashie.skdragon.util.ParticleUtils;
import org.bukkit.Particle;
import org.bukkit.event.Event;

public class ParticleSection extends EffectSection {

	public static ParticleBuilder<?> particle;
	private Expression<Particle> type;
	private SkriptNode skriptNode;

	static {
		Skript.registerCondition(ParticleSection.class,
				"(create|make) [a] [new] particle (of|from|using) %particle%");
	}

	@Override
	public void execute(Event event) {
		Particle type = this.type.getSingle(event);

		if (type == null)
			return;

		setParticle(ParticleUtils.createParticle(type));

		runSection(event);
	}

	public static ParticleBuilder<?> getParticle() {
		return particle;
	}

	public static void setParticle(ParticleBuilder<?> p) {
		particle = p;
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "create particle of " + type.toString(event, debug);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		if (checkIfCondition())
			return false;
		if (!hasSection()) {
			Skript.error("A particle creation scope is useless without any content!");
			return false;
		}
		type = (Expression<Particle>) exprs[0];
		skriptNode = new SkriptNode(SkriptLogger.getNode());

		loadSection(true);
		return true;
	}
}