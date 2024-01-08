package me.sashie.skdragon.skript.expressions;

import java.util.List;

import javax.annotation.Nullable;

import me.sashie.skdragon.particles.ParticleProperty;
import org.bukkit.Particle;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

/**
 * Created by Sashie on 8/14/2017.
 */

@Name("Particles - Property")
@Description({"Lists any properties this particle has if any"})
@Examples({	"set {list::*} to particle properties of redstone"})
public class ExprAllParticleProperties extends SimpleExpression<String> {
	
	static {
		Skript.registerExpression(ExprAllParticleProperties.class, String.class, ExpressionType.SIMPLE,
				"[all] particle propert(y|ies) of %particle%");
	}
	
	private Expression<Particle> particle;

	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, ParseResult arg3) {
		particle = (Expression<Particle>) args[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "all particle properties of " + particle.toString(event, debug);
	}

	@Override
	@Nullable
	protected String[] get(Event e) {
		Particle particle = this.particle.getSingle(e);
		if (particle == null)
			return null;

		List<ParticleProperty> properties = ParticleProperty.getPropertiesForParticle(particle);

		String[] result = properties.stream()
				.map(ParticleProperty::toString)
				.toArray(String[]::new);

		return result;
	}
}