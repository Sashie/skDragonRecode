package me.sashie.skdragon.skript.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.debug.SkriptNode;
import me.sashie.skdragon.particles.*;
import org.bukkit.Particle;
import org.bukkit.event.Event;

public class ParticleSection extends EffectSection {

	private static ParticleBuilder<?> particle;

	private Expression<Particle> type;
	int mark;
	
	private SkriptNode skriptNode;

	static {
		Skript.registerCondition(ParticleSection.class,
				"(create|make) [a] [new] (1¦normal|2¦material|3¦direction|4¦colo[u]red|5¦fade|6¦vibration) particle (of|from) %particle%");
	}

	@Override
	public void execute(Event event) {
		Particle type = this.type.getSingle(event);

		switch (mark) {
		case 1:
			this.particle = new NormalParticle(type);
			break;
		case 2:
			this.particle = new MaterialParticle(type);
			break;
		case 3:
			this.particle = new DirectionParticle(type);
			break;
		case 4:
			this.particle = new ColoredParticle(type);
			break;
		case 5:
			this.particle = new ColoredFadeParticle(type);
			break;
		case 6:
			this.particle = new VibrationParticle(type);
			break;
		}

		runSection(event);
	}

	public static ParticleBuilder<?> getParticle() {
		return particle;
	}


	@Override
	public String toString(Event event, boolean debug) {
		return "create particle " + type.toString(event, debug);
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
		this.mark = parseResult.mark;
		skriptNode = new SkriptNode(SkriptLogger.getNode());

		loadSection(true);
		return true;
	}
}