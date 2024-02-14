package me.sashie.skdragon.skript.conditions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.project.particles.ParticleProperty;
import me.sashie.skdragon.project.skript.PropertyCondition;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;

@Name("Particles - Is Colorable")
@Description("Checks whether a particle is colorable.")
@Examples({"particle redstone is colorable",
		"particle flame is colorable"})
public class CondParticleIsColorable extends PropertyCondition<Particle> {

	static {
		register(
				CondParticleIsColorable.class,
				PropertyType.BE,
				"colo[u]rable",
				"particle"
		);
	}

	@Override
	public boolean check(@NotNull Particle particle) {
		return ParticleProperty.COLORABLE.hasProperty(particle);
	}

	@Override
	protected String getPropertyName() {
		return "colo[u]rable";
	}
}