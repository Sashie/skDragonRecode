package me.sashie.skdragon.skript.conditions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.sashie.skdragon.project.particles.ParticleProperty;
import me.sashie.skdragon.project.skript.PropertyCondition;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;


@Name("Particles - Requires material")
@Description("Checks whether a particle requires material.")
@Examples({"particle redstone has material #returns false"})
public class CondParticleRequiresData extends PropertyCondition<Particle> {

	static {
		register(
				CondParticleRequiresData.class,
				PropertyType.HAVE,
				"material",
				"particle"
		);
	}

	@Override
	public boolean check(@NotNull Particle particle) {
		return ParticleProperty.REQUIRES_DATA.hasProperty(particle);
	}

	@Override
	protected String getPropertyName() {
		return "material";
	}
}


