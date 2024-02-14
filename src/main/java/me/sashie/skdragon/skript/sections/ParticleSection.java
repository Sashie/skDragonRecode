package me.sashie.skdragon.skript.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.api.particles.ParticleBuilder;
import me.sashie.skdragon.api.util.ParticleUtils;
import org.bukkit.Particle;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class ParticleSection extends EffectSection {

	static {
		Skript.registerCondition(
				ParticleSection.class,
				"(create|make) [a] [new] particle (of|from|using) %particle%"
		);
	}

	public static ParticleBuilder<?> particle;
	private Expression<Particle> type;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?> @NotNull [] exprs, int i, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
		if (checkIfCondition())
			return false;
		if (!hasSection()) {
			Skript.error("A particle creation scope is useless without any content!");
			return false;
		}
		type = (Expression<Particle>) exprs[0];

		loadSection(true);
		return true;
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
	public @NotNull String toString(Event event, boolean debug) {
		return "create particle of " + type.toString(event, debug);
	}

}