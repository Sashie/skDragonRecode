package me.sashie.skdragon.skript;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.yggdrasil.Fields;
import me.sashie.skdragon.api.effects.ParticleEffect;
import me.sashie.skdragon.api.util.ParticleUtil;
import org.bukkit.Particle;

import javax.annotation.Nonnull;
import java.io.StreamCorruptedException;

public class Types {

	static {
		if (Classes.getExactClassInfo(Particle.class) == null) {
			Classes.registerClass(new ClassInfo<>(Particle.class, "particle")
					.user("particles?")
					.name("Particle")
					.description("A mirror of SkBee's Particle type. For use when SkBee is not installed.",
							"Represents a particle which can be used as a shape's particle, or in the Draw Particle and Particle Spawn effects.",
							"Some particles require extra data, these are distinguished by their data type within the square brackets.",
							"DustOption, DustTransition and Vibration each have their own functions to build the appropriate data for these particles.")
					.usage(ParticleUtil.getNamesAsString())
					.examples("draw 1 of soul at location of player",
							"draw 10 of dust using dustOption(green, 10) at location of player",
							"draw 3 of item using player's tool at location of player",
							"draw 1 of block using dirt at location of player",
							"draw 1 of dust_color_transition using dustTransition(blue, green, 3) at location of player",
							"draw 1 of vibration using vibration({loc1}, {loc2}, 1 second) at {loc1}")
					.since("1.0")
					.parser(new Parser<>() {

						@Override
						public Particle parse(@Nonnull String input, @Nonnull ParseContext context) {
							return ParticleUtil.parse(input.replace(" ", "_"));
						}

						@Override
						@Nonnull
						public String toString(Particle particle, int flags) {
							return ParticleUtil.getName(particle);
						}

						@Override
						@Nonnull
						public String toVariableNameString(Particle particle) {
							return "particle:" + toString(particle, 0);
						}
					})
			);
		}

		Classes.registerClass(new ClassInfo<ParticleEffect>(ParticleEffect.class, "particleeffect")
				.user("particleeffects?")
				.defaultExpression(new EventValueExpression<>(ParticleEffect.class))
				.since("1.0")
				.parser(new Parser<ParticleEffect>() {

					@Override
					public ParticleEffect parse(@Nonnull String input, @Nonnull ParseContext context) {
						return ParticleEffect.getByName(input);
					}

					@Override
					@Nonnull
					public String toString(ParticleEffect effect, int flags) {
						return effect.getName();
					}

					@Override
					@Nonnull
					public String toVariableNameString(ParticleEffect effect) {
						return effect.getName();
					}

				}).serializer(new Serializer<ParticleEffect>() {

					@Override
					@Nonnull
					public Fields serialize(final ParticleEffect e) {
						final Fields f = new Fields();
						f.putObject("name", e.toString());
						return f;
					}

					@Override
					public void deserialize(final ParticleEffect o, @Nonnull final Fields f) {
						assert false;
					}

					@Override
					public boolean canBeInstantiated() {
						return false;
					}

					@Override
					@Nonnull
					protected ParticleEffect deserialize(@Nonnull final Fields fields) throws StreamCorruptedException {
						final String name = fields.getObject("name", String.class);
						final ParticleEffect e = ParticleEffect.getByName(name);
						if (e == null) throw new StreamCorruptedException("Missing particle effect " + name);
						return e;
					}

					@Override
					public ParticleEffect deserialize(@Nonnull final String s) {
						return ParticleEffect.valueOf(s);
					}

					@Override
					public boolean mustSyncDeserialization() {
						return true;
					}

				})
		);

	}

}