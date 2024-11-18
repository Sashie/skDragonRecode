package me.sashie.skdragon.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.runnable.RunnableType;
import me.sashie.skdragon.skript.sections.ParticleSection;
import me.sashie.skdragon.util.EffectUtils;
import me.sashie.skdragon.util.ParticleUtils;
import me.sashie.skdragon.util.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

@Name("Particles - Draw last created/current particle")
@Description({"Draws the last created particle"})
@Examples({"create particle of flame:",
		"   set count of particle to 100",
		"   set offset of particle to vector 1, 1 and 1",
		"draw current particle at player",
		"#draw current particle at player repeating 100 times with an interval of 1 tick",
		"#draw current particle at player repeating for 5 seconds with an interval of 1 tick"})
public class EffSpawnCurrentParticle extends Effect {

	static {
		Skript.registerEffect(
				EffSpawnCurrentParticle.class,
				"(draw|send) [the] ([last] created|current) particle (at|to) %objects% [with [a] displacement [of] %-vector%] [for %-players%] [delayed by %-timespan%] [repeat(ed|ing) (for %-timespan%|%-number% times) with [an] interval of %-timespan%]"
		);
	}

	private Expression<Object> exprLocations;
	private Expression<Vector> exprDisplacement;
	private Expression<Player> exprPlayers;
	private Expression<Timespan> exprDelay;
	private Expression<Timespan> exprDuration;
	private Expression<Number> exprIterations;
	private Expression<Timespan> exprInterval;

	private String parsedSyntax;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parser) {
		exprLocations = (Expression<Object>) exprs[0];
		exprDisplacement = (Expression<Vector>) exprs[1];
		exprPlayers = (Expression<Player>) exprs[2];
		exprDelay = (Expression<Timespan>) exprs[3];
		exprDuration = (Expression<Timespan>) exprs[4];
		exprIterations = (Expression<Number>) exprs[5];
		exprInterval = (Expression<Timespan>) exprs[6];

		parsedSyntax = parser.expr;

		return true;
	}

	@Override
	protected void execute(@NotNull Event e) {
		if (ParticleSection.getParticle() == null) return;

		Object[] locations = Utils.verifyVars(e, exprLocations, null);
		if (locations == null) return;

		Vector displacement = Utils.verifyVar(e, exprDisplacement, new Vector());
		Player[] players = Utils.verifyVars(e, exprPlayers, null); // null is handled later
		long delay = Utils.verifyVar(e, exprDelay, new Timespan(0)).getTicks_i();
		long duration = Utils.verifyVar(e, exprDuration, new Timespan(0L)).getMilliSeconds();
		int iterations = Utils.verifyVar(e, exprIterations, 1).intValue();
		long interval = Utils.verifyVar(e, exprInterval, new Timespan(0)).getTicks_i();

		RunnableType type = RunnableType.INSTANT;
		if (delay > 0L)
			type = RunnableType.DELAYED;
		if (parsedSyntax.contains("repeat") || exprIterations != null)
			type = RunnableType.REPEATING;

		ParticleUtils.sendTimedParticles(ParticleSection.getParticle(), EffectUtils.toDynamicLocations(locations), displacement, players, type, delay, duration, iterations, interval);
	}

	@Override
	public @NotNull String toString(Event e, boolean debug) {
		return "draw current particle at " + exprLocations.toString(e, debug);
	}

}
