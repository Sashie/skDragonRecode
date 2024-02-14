package me.sashie.skdragon.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.skript.sections.ParticleSection;
import me.sashie.skdragon.project.util.DynamicLocation;
import me.sashie.skdragon.project.util.Utils;
import me.sashie.skdragon.project.util.pool.ObjectPoolManager;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;



@Name("Particles - Draw last created/current particle")
@Description({"Draws the last created particle"})
@Examples({"create particle of flame:",
		"   set count of particle to 100",
		"   set offset of particle to vector 1, 1 and 1",
		"draw current particle at player"})
public class EffSpawnCurrentParticle extends Effect {

	static {
		Skript.registerEffect(
				EffSpawnCurrentParticle.class,
				"(draw|send) [the] ([last] created|current) particle (at|to) %objects%"
		);
	}

	private Expression<Object> exprLocations;

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parser) {
		exprLocations = (Expression<Object>) exprs[0];
		return true;
	}

	@Override
	protected void execute(@NotNull Event e) {
		if (ParticleSection.getParticle() == null) return;

		Object[] locations = Utils.verifyVars(e, exprLocations, null);
		if (locations == null) return;

		for (Object loc : locations) {
			DynamicLocation dynLoc = ObjectPoolManager.getDynamicLocationPool().acquire(loc);
			if (dynLoc == null) continue;

			ParticleSection.getParticle().sendParticles(dynLoc);
			ObjectPoolManager.getDynamicLocationPool().release(dynLoc);
		}
	}

	@Override
	public @NotNull String toString(Event e, boolean debug) {
		return "draw current particle at " + exprLocations.toString(e, debug);
	}

}
