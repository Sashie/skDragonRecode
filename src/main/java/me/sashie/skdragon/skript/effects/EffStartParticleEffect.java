package me.sashie.skdragon.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.EffectAPI;
import me.sashie.skdragon.debug.SkriptNode;
import me.sashie.skdragon.runnable.RunnableType;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.EffectUtils;
import me.sashie.skdragon.util.Utils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Start particle effect")
@Description({"Starts a particle effect of a given id name.",
		"   - Using the sync optional makes the effect play in a synchronous runnable instead",
		"   - Adding a delay makes the effect start later",
		"   - The effect will play only once unless you add the repeating option",
		"   - The repeating option will play infinitely unless the number of times option is used",
		"   - When using repeat the interval is used to determine how fast between iterations",
		"   - The targeting option is only used for certain effects"})
@Examples({"start particle effect \"unique_name\" at location of player",
		"start sync particle effect \"%player%\"",
		"start particle effect \"%player%\" delayed by 10 ticks",
		"start particle effect \"%player%\" repeating with an interval of 1 tick",
		"start particle effect \"%player%\" repeating 5 times with an interval of 0 ticks",
		"start sync particle effect \"%player%\" delayed by 10 ticks repeating 5 times with an interval of 5 ticks"})
public class EffStartParticleEffect extends Effect {

	static {
		Skript.registerEffect(
				EffStartParticleEffect.class,
				"(start|run) [sync] particle effect %string% at %objects% " +
						"[targeting %-objects%] [delayed by %-timespan%] [repeat(ed|ing) [(for %-timespan%|%-number% times)] with [an] interval of %-timespan%]"
		);
	}

	private String parsedSyntax;
	private Expression<String> exprId;
	private Expression<Object> exprLocation;
	private Expression<Object> exprTargets;
	private Expression<Timespan> exprDelay;
	private Expression<Timespan> exprDuration;
	private Expression<Number> exprRepeat;
	private Expression<Timespan> exprInterval;

	private SkriptNode skriptNode;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		exprId = (Expression<String>) exprs[0];
		exprLocation = (Expression<Object>) exprs[1];

		exprTargets = (Expression<Object>) exprs[2];

		exprDelay = (Expression<Timespan>) exprs[3];
		exprDuration = (Expression<Timespan>) exprs[4];
		exprRepeat = (Expression<Number>) exprs[5];
		exprInterval = (Expression<Timespan>) exprs[6];

		parsedSyntax = parseResult.expr;
		skriptNode = new SkriptNode(SkriptLogger.getNode());

		return true;
	}

	@Override
	protected void execute(@NotNull Event e) {
		String id = Utils.verifyVar(e, exprId, null);
		Object[] locations = Utils.verifyVars(e, exprLocation, null);

		if (id == null || locations == null) return;

		long delay = Utils.verifyVar(e, exprDelay, new Timespan(0)).getTicks_i();
		long duration = Utils.verifyVar(e, exprDuration, new Timespan(0L)).getMilliSeconds();
		int repeat = Utils.verifyVar(e, exprRepeat, -1).intValue();
		long interval = Utils.verifyVar(e, exprInterval, new Timespan(0)).getTicks_i();
		Object[] targets = Utils.verifyVars(e, exprTargets, null);

		RunnableType type = RunnableType.INSTANT;
		if (delay > 0)
			type = RunnableType.DELAYED;
		if (parsedSyntax.contains("repeat") || exprRepeat != null)
			type = RunnableType.REPEATING;

		DynamicLocation[] locs = EffectUtils.toDynamicLocations(locations);

		DynamicLocation[] targetLocations = null;
		if (targets != null) {
			targetLocations = EffectUtils.toDynamicLocations(targets);
		}

		EffectAPI.start(id, type, duration, repeat, delay, interval, parsedSyntax.contains(" sync "), locs, targetLocations, skriptNode);
	}

	@Override
	public @NotNull String toString(Event event, boolean debug) {
		return "start" + (parsedSyntax.contains(" sync ") ? " sync " : " ") + "particle effect " + exprId.toString(event, debug) + (exprDelay != null ? " delayed by " + exprDelay.toString(event, debug) : " ") + (parsedSyntax.contains(" repeat") ? "repeating" + (exprRepeat != null ? exprRepeat.toString(event, debug) + " times" : " ") + "with an interval of " + exprInterval.toString(event, debug) : "");
	}

}
