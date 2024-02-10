

package me.sashie.skdragon.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.util.Kleenean;
import me.sashie.skdragon.EffectAPI;
import me.sashie.skdragon.debug.SkriptNode;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.ParticleEffect;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.util.Utils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;


@Name("Register new particle effect")
@Description({"Registers a new particle effect, once it is registered you can start or stop it using those expressions. Optionally you can also set the particles it uses however you can do this any time before or after starting the effect."})
@Examples({"register new particle effect circle with id \"%player%\""})
public class EffRegisterNewParticleEffect extends Effect {

	static {
		Skript.registerEffect(EffRegisterNewParticleEffect.class,
				"(create|make|register) [a] [new] particle effect %particleeffect% with id %string% [using %-particles%]");
	}

	private Expression<ParticleEffect> exprType;
	private Expression<String> exprId;
	private Expression<ParticleBuilder<?>> exprParticles;
	private static SkriptNode skriptNode;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int i, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
		this.exprType = (Expression<ParticleEffect>) expressions[0];
		this.exprId = (Expression<String>) expressions[1];
		this.exprParticles = (Expression<ParticleBuilder<?>>) expressions[2];

		skriptNode = new SkriptNode(SkriptLogger.getNode());

		return true;
	}

	@Override
	protected void execute(@Nonnull Event e) {
		String id = Utils.verifyVar(e, exprId, null);
		ParticleEffect type = Utils.verifyVar(e, exprType, null);

		if (id == null || type == null) return;

		EffectData effect = EffectAPI.register(id, type, skriptNode);

		ParticleBuilder<?>[] particles = Utils.verifyVars(e, exprParticles, null);
		if (particles != null) {
			effect.setParticles(particles, skriptNode);
		}
	}

	@Override
	public @NotNull String toString(Event event, boolean debug) {
		return "register particle effect " + exprType.toString(event, debug) + " with id " + exprId.toString(event, debug) + (exprParticles != null ? " using " + exprParticles.toString(event, debug) : "");
	}

}
