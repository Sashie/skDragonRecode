package me.sashie.skdragon.skript.conditions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.project.particles.ParticleProperty;
import me.sashie.skdragon.project.skript.PropertyCondition;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;


@Name("Particles - Is Directional")
@Description("Checks whether a particle is directional.")
@Examples({"particle redstone is directional"})
public class CondParticleIsDirectional extends PropertyCondition<Particle> {

	static {
		register(
				CondParticleIsDirectional.class,
				PropertyType.BE,
				"directional",
				"particle"
		);
	}

	@Override
	public boolean check(@NotNull Particle particle) {
		return ParticleProperty.DIRECTIONAL.hasProperty(particle);
	}

	@Override
	protected String getPropertyName() {
		return "directional";
	}
}