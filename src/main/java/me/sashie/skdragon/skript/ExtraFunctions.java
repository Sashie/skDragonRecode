package me.sashie.skdragon.skript;

import java.awt.Color;

import javax.annotation.Nullable;

import me.sashie.skdragon.particles.ParticleBuilder;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.lang.function.FunctionEvent;
import ch.njol.skript.lang.function.Functions;
import ch.njol.skript.lang.function.JavaFunction;
import ch.njol.skript.lang.function.Parameter;
import ch.njol.skript.lang.util.SimpleLiteral;
import ch.njol.skript.registrations.Classes;

public class ExtraFunctions {

	static {
		final ClassInfo<Number> numberClass = Classes.getExactClassInfo(Number.class);

		Functions.registerFunction(new JavaFunction<Vector>("vec",
					new Parameter[] { 
							new Parameter<>("x", numberClass, true, null),
							new Parameter<>("y", numberClass, true, null), 
							new Parameter<>("z", numberClass, true, null) },
					Classes.getExactClassInfo(Vector.class), true) {

					@Override
					@Nullable
					public Vector[] execute(FunctionEvent e, Object[][] params) {
						return new Vector[] { new Vector(
								((Number) params[0][0]).doubleValue(),
								((Number) params[1][0]).doubleValue(), 
								((Number) params[2][0]).doubleValue()
							) 
						};
					}
			}.description("Creates a new vector.")
					.examples("vec(0, 0, 0)")
					.since("1.0"));

		Functions.registerFunction(new JavaFunction<Color>("javacolor",
				new Parameter[] { 
						new Parameter<>("r", numberClass, true, null),
						new Parameter<>("g", numberClass, true, null), 
						new Parameter<>("b", numberClass, true, null) },
				Classes.getExactClassInfo(Color.class), true) {

				@Override
				@Nullable
				public Color[] execute(FunctionEvent e, Object[][] params) {
					return new Color[] { new Color(
							((Number) params[0][0]).intValue(),
							((Number) params[1][0]).intValue(), 
							((Number) params[2][0]).intValue()
						) 
					};
				}
			}.description("Creates a new java color using integers 0-255.")
					.examples("javacolor(255, 0, 0)")
					.since("1.0"));
/*
		Functions.registerFunction(new JavaFunction<ParticleBuilder>("particle",
				new Parameter[] { 
					new Parameter<>("type", Classes.getExactClassInfo(Particle.class), true, null),
					new Parameter<>("range", numberClass, true, new SimpleLiteral<Number>(32, true)),
					new Parameter<>("amount", numberClass, true, new SimpleLiteral<Number>(1, true)), 
					new Parameter<>("offsetX", numberClass, true, new SimpleLiteral<Number>(0, true)), 
					new Parameter<>("offsetY", numberClass, true, new SimpleLiteral<Number>(0, true)), 
					new Parameter<>("offsetZ", numberClass, true, new SimpleLiteral<Number>(0, true)), 
					new Parameter<>("speed", numberClass, true, new SimpleLiteral<Number>(1, true)) },
				Classes.getExactClassInfo(ParticleBuilder.class), true) {
	
					@Override
					@Nullable
					public ParticleBuilder[] execute(FunctionEvent e, Object[][] params) {
						return new Particle[] {

							new ParticleBuilder(
								(Particle) params[0][0],
								((Number) params[1][0]).doubleValue(), 
								((Number) params[2][0]).intValue(), 
								((Number) params[3][0]).floatValue(), 
								((Number) params[4][0]).floatValue(), 
								((Number) params[5][0]).floatValue(), 
								((Number) params[6][0]).floatValue()
							) 
						};
					}
				}.description("Creates a new particle instance, which can be used with various particle related effects and functions.")
						.examples("particle(flame, 100, 1, 0.5, 0.5, 0.5, 1)",
								"particle(flame)")
						.since("1.0"));
*/

/*

		Functions.registerFunction(new JavaFunction<Particle>("colorParticle",
				new Parameter[] { 
					new Parameter<>("type", Classes.getExactClassInfo(ParticleEffect.class), true, null),
					new Parameter<>("range", numberClass, true, new SimpleLiteral<Number>(32, true)),
					new Parameter<>("amount", numberClass, true, new SimpleLiteral<Number>(1, true)), 
					new Parameter<>("offsetX", numberClass, true, new SimpleLiteral<Number>(0, true)), 
					new Parameter<>("offsetY", numberClass, true, new SimpleLiteral<Number>(0, true)), 
					new Parameter<>("offsetZ", numberClass, true, new SimpleLiteral<Number>(0, true)), 
					new Parameter<>("speed", numberClass, true, new SimpleLiteral<Number>(1, true)), 
					new Parameter<>("color", Classes.getExactClassInfo(Color.class), false, null), 
					new Parameter<>("randomColor", Classes.getExactClassInfo(Boolean.class), true, new SimpleLiteral<Boolean>(false, true)) },
				Classes.getExactClassInfo(Particle.class), true) {
	
					@Override
					@Nullable
					public Particle[] execute(FunctionEvent e, Object[][] params) {
						Particle type = (Particle) params[0][0];
						if (ParticleProperty.COLORABLE.hasProperty(type)) {
							return new Particle[] {
								new Particle(
									type,
									((Number) params[1][0]).doubleValue(), 
									((Number) params[2][0]).intValue(), 
									((Number) params[3][0]).floatValue(), 
									((Number) params[4][0]).floatValue(), 
									((Number) params[5][0]).floatValue(), 
									((Number) params[6][0]).floatValue(), 
									(Color[]) params[7], 
									((Boolean) params[8][0]).booleanValue()
								) 
							};
						}
						//TODO warn that particle isnt the right type
						return null;
					}
				}.description("Creates a new particle instance, which can be used with various particle related effects and functions.")
						.examples("particle(flame, 100, 1, 0.5, 0.5, 0.5, 1)",
								"particle(flame)")
						.since("1.0"));

		Functions.registerFunction(new JavaFunction<Particle>("directionParticle",
				new Parameter[] { 
					new Parameter<>("type", Classes.getExactClassInfo(ParticleEffect.class), true, null),
					new Parameter<>("range", numberClass, true, new SimpleLiteral<Number>(32, true)),
					new Parameter<>("amount", numberClass, true, new SimpleLiteral<Number>(1, true)), 
					new Parameter<>("offsetX", numberClass, true, new SimpleLiteral<Number>(0, true)), 
					new Parameter<>("offsetY", numberClass, true, new SimpleLiteral<Number>(0, true)), 
					new Parameter<>("offsetZ", numberClass, true, new SimpleLiteral<Number>(0, true)), 
					new Parameter<>("speed", numberClass, true, new SimpleLiteral<Number>(1, true)), 
					new Parameter<>("direction", Classes.getExactClassInfo(Vector.class), true, new SimpleLiteral<Vector>(new Vector(0, 1, 0), true)) },
				Classes.getExactClassInfo(Particle.class), true) {
	
					@Override
					@Nullable
					public Particle[] execute(FunctionEvent e, Object[][] params) {
						ParticleEffect type = (ParticleEffect) params[0][0];
						if (type.hasProperty(ParticleProperty.DIRECTIONAL)) {
							return new Particle[] {
								new Particle(
									type,
									((Number) params[1][0]).doubleValue(), 
									((Number) params[2][0]).intValue(), 
									((Number) params[3][0]).floatValue(), 
									((Number) params[4][0]).floatValue(), 
									((Number) params[5][0]).floatValue(), 
									((Number) params[6][0]).floatValue(),
									(Vector) params[7][0]
								) 
							};
						}
						//TODO warn that particle isnt the right type
						return null;
					}
				}.description("Creates a new particle instance, which can be used with various particle related effects and functions.")
						.examples("particle(flame, 100, 1, 0.5, 0.5, 0.5, 1)",
								"particle(flame)")
						.since("1.0"));

		Functions.registerFunction(new JavaFunction<Particle>("materialParticle",
				new Parameter[] { 
						new Parameter<>("type", Classes.getExactClassInfo(ParticleEffect.class), true, null),
						new Parameter<>("range", numberClass, true, new SimpleLiteral<Number>(32, true)),
						new Parameter<>("amount", numberClass, true, new SimpleLiteral<Number>(1, true)), 
						new Parameter<>("offsetX", numberClass, true, new SimpleLiteral<Number>(0, true)), 
						new Parameter<>("offsetY", numberClass, true, new SimpleLiteral<Number>(0, true)), 
						new Parameter<>("offsetZ", numberClass, true, new SimpleLiteral<Number>(0, true)), 
						new Parameter<>("speed", numberClass, true, new SimpleLiteral<Number>(1, true)), 
						new Parameter<>("material", Classes.getExactClassInfo(Material.class), true, null), 
						new Parameter<>("data", numberClass, true, new SimpleLiteral<Number>(0, true)) },
					Classes.getExactClassInfo(Particle.class), true) {
		
						@Override
						@Nullable
						public Particle[] execute(FunctionEvent e, Object[][] params) {
							ParticleEffect type = (ParticleEffect) params[0][0];
							if (type.hasProperty(ParticleProperty.REQUIRES_DATA)) {
								return new Particle[] {
									new Particle(
										type,
										((Number) params[1][0]).doubleValue(), 
										((Number) params[2][0]).intValue(), 
										((Number) params[3][0]).floatValue(), 
										((Number) params[4][0]).floatValue(), 
										((Number) params[5][0]).floatValue(), 
										((Number) params[6][0]).floatValue(),
										(Material) params[7][0],
										((Number) params[8][0]).byteValue()
									) 
								};
							}
							//TODO warn that particle isnt the right type
							return null;
						}
					}.description("Creates a new particle instance, which can be used with various particle related effects and functions.")
							.examples("particle(flame, 100, 1, 0.5, 0.5, 0.5, 1)",
									"particle(flame)")
							.since("1.0"));

	*/
	}

	
}
