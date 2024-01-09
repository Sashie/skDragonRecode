/*
	This file is part of skDragon - A Skript addon
	Parts of this class are from skRayFall, thanks eyesniper <3
      
	Copyright (C) 2016 - 2021  Sashie

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.sashie.skdragon.skript;

import java.awt.*;
import java.io.StreamCorruptedException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;

import javax.annotation.Nullable;

import me.sashie.skdragon.util.ParticleUtil;
import org.bukkit.Particle;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.yggdrasil.Fields;
import me.sashie.skdragon.effects.ParticleEffect;

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

						@Nullable
						@Override
						public Particle parse(String input, ParseContext context) {
							return ParticleUtil.parse(input.replace(" ", "_"));
						}

						@Override
						public String toString(Particle particle, int flags) {
							return "" + ParticleUtil.getName(particle);
						}

						@Override
						public String toVariableNameString(Particle particle) {
							return "particle:" + toString(particle, 0);
						}
					}));
		}


		Classes.registerClass(new ClassInfo<ParticleEffect>(ParticleEffect.class, "particleeffect")
				.user("particle ?effects?")
				.defaultExpression(new EventValueExpression<>(ParticleEffect.class))
				.since("1.0")
				.parser(new Parser<ParticleEffect>() {

					@Override
					@Nullable
					public ParticleEffect parse(String input, ParseContext context) {
						return ParticleEffect.getByName(input);
					}

					@Override
					public String toString(ParticleEffect effect, int flags) {
						return effect.getName();
					}

					@Override
					public String toVariableNameString(ParticleEffect effect) {
						return effect.getName();
					}
				}).serializer(new Serializer<ParticleEffect>() {
					@Override
					public Fields serialize(final ParticleEffect e) {
						final Fields f = new Fields();
						f.putObject("name", e.toString());
						return f;
					}

					@Override
					public void deserialize(final ParticleEffect o, final Fields f) {
						assert false;
					}

					@Override
					public boolean canBeInstantiated() {
						return false;
					}

					@Override
					protected ParticleEffect deserialize(final Fields fields) throws StreamCorruptedException {
						final String name = fields.getObject("name", String.class);
						final ParticleEffect e = ParticleEffect.valueOf(name);
						if (e == null)
							throw new StreamCorruptedException("Missing particle effect " + name);
						return e;
					}

					@Override
					@Nullable
					public ParticleEffect deserialize(final String s) {
						return ParticleEffect.valueOf(s);
					}

					@Override
					public boolean mustSyncDeserialization() {
						return true;
					}
				}));

	}

}