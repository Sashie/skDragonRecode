package me.sashie.skdragon.commands;

import me.sashie.skdragon.EffectAPI;
import me.sashie.skdragon.PropertyAPI;
import me.sashie.skdragon.effects.EffectData;
import me.sashie.skdragon.effects.ParticleEffect;
import me.sashie.skdragon.particles.DirectionParticle;
import me.sashie.skdragon.particles.ParticleBuilder;
import me.sashie.skdragon.runnable.RunnableType;
import me.sashie.skdragon.util.DynamicLocation;
import me.sashie.skdragon.util.ParticleUtil;
import me.sashie.skdragon.util.Utils;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EffectCommand implements TabExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("This command can only be executed by a player.");
			return true;
		}

		Player player = (Player) sender;

		if (args.length < 1) {
			player.sendMessage("Usage: /skdragon <register/unregister/start/stop/effect/particle> <id> [args...]");
			return true;
		}

		String subCommand = args[0];
		String id = args[1];

		EffectData data;

		switch (subCommand.toLowerCase()) {
			case "register":
				if (args.length < 3) {
					player.sendMessage("Usage: /skdragon register <id> <effect>");
					break;
				}
				String effectType = args[2];
				if (!Utils.isValidEnum(ParticleEffect.class, effectType)) {
					player.sendMessage("The effect " + effectType + " doesn't exist");
					break;
				}

				EffectAPI.register(id, ParticleEffect.valueOf(effectType.toUpperCase()), null);
				player.sendMessage("Effect " + id + " registered.");
				break;

			case "unregister":
				if (id == null) {
					player.sendMessage("Usage: /skdragon unregister <id>");
					break;
				}
				EffectAPI.unregister(id, null);
				player.sendMessage("Effect " + id + " unregistered.");
				break;

			case "start":
				if (args.length < 3) {
					player.sendMessage("Usage: /skdragon start <id> <player|location> [ticks] [iterations]");
					break;
				}

				String location = args[2];
				DynamicLocation[] loc = null;
				if (location.equalsIgnoreCase("player")) {
					loc = new DynamicLocation[]{ new DynamicLocation(player) };
				} else if (location.equalsIgnoreCase("location")) {
					loc = new DynamicLocation[]{ new DynamicLocation(player.getLocation()) };
				}
				if (loc == null) {
					player.sendMessage("Location needs to be either player or location");
					return true;
				}

				int ticks = 1;
				if (args[3] != null) {
					ticks = Integer.parseInt(args[3]);
				}
				if (args.length == 5) {
					int iterations = Integer.parseInt(args[4]);
					EffectAPI.start(id, RunnableType.REPEATING, 0, iterations, 0, ticks, false, loc, null, null);
				} else {
					EffectAPI.start(id, RunnableType.REPEATING, 0, -1, 0, ticks, false, loc, null, null);
				}

				player.sendMessage("Effect " + id + " started.");
				break;

			case "stop":
				if (id == null) {
					player.sendMessage("Usage: /skdragon stop <id>");
					break;
				}
				EffectAPI.stop(id, null);
				player.sendMessage("Effect " + id + " stopped.");
				break;

			case "effect":
				if (args.length < 4) {
					player.sendMessage("Usage: /skdragon effect <property> <id> <value>");
					break;
				}

				id = args[2];
				String property = args[1];
				int effectIndex = Integer.parseInt(args[3]);

				data = EffectAPI.get(id, null);
				if (data == null ) {
					sender.sendMessage("Effect with id " + id + " does not exist!");
					return true;
				}

				switch (property.toLowerCase()) {
					case "style":
						int style = Integer.parseInt(args[3]);
						PropertyAPI.setStyle(data, style);
						break;

					case "solid":
						boolean solid = Boolean.parseBoolean(args[3]);
						PropertyAPI.setSolid(data, solid);
						break;

					case "fill":
						boolean fill = Boolean.parseBoolean(args[3]);
						PropertyAPI.setFill(data, fill);
						break;

					case "rotate":
						boolean rotate = Boolean.parseBoolean(args[3]);
						PropertyAPI.setRotate(data, rotate);
						break;

					case "oscillating":
						boolean oscillating = Boolean.parseBoolean(args[3]);
						PropertyAPI.setOscillating(data, oscillating);
						break;

					case "reverse":
						boolean reverse = Boolean.parseBoolean(args[3]);
						PropertyAPI.setReverse(data, reverse);
						break;

					case "axis":
						if (args.length < 6) {
							player.sendMessage("Usage: /skdragon effect axis <id> <x> <y> <z>");
							break;
						}
						float x = Float.parseFloat(args[3]);
						float y = Float.parseFloat(args[4]);
						float z = Float.parseFloat(args[5]);

						PropertyAPI.setAxis(data, x, y, z);
						break;

					case "velocity":
						if (args.length < 6) {
							player.sendMessage("Usage: /skdragon effect velocity <id> <x> <y> <z>");
							break;
						}
						float x2 = Float.parseFloat(args[3]);
						float y2 = Float.parseFloat(args[4]);
						float z2 = Float.parseFloat(args[5]);

						PropertyAPI.setVelocity(data, x2, y2, z2);
						break;

					case "density":
						if (args.length < 5) {
							player.sendMessage("Usage: /skdragon effect density <id> <index> <value>");
							break;
						}

						int density = Integer.parseInt(args[4]);
						PropertyAPI.setDensity(data, effectIndex, density);
						player.sendMessage("Set density for effect " + id + " at index " + effectIndex + " to " + density);

						break;

					case "radius":
						if (args.length < 5) {
							player.sendMessage("Usage: /skdragon effect radius <id> <index> <value>");
							break;
						}
						float radius = Float.parseFloat(args[4]);
						PropertyAPI.setRadius(data, effectIndex, radius);
						player.sendMessage("Set radius for effect " + id + " at index " + effectIndex + " to " + radius);

						break;

					case "extra":
						if (args.length < 5) {
							player.sendMessage("Usage: /skdragon effect extra <id> <index> <value>");
							break;
						}
						float extra = Float.parseFloat(args[4]);
						PropertyAPI.setExtra(data, effectIndex, extra);
						player.sendMessage("Set extra for effect " + id + " at index " + effectIndex + " to " + extra);

						break;

					default:
						player.sendMessage("Unknown property: " + property);
						break;
				}
				break;

			case "particle":
				if (args.length < 4) {
					player.sendMessage("Usage: /skdragon particle <property> <id> <index> <args...>");
					break;
				}

				id = args[2];
				String particleProperty = args[1];
				int particleIndex = Integer.parseInt(args[3]);

				data = EffectAPI.get(id, null);
				if (data == null ) {
					sender.sendMessage("Effect with id " + id + " does not exist!");
					return true;
				}
				ParticleBuilder<?> particle = data.getParticleBuilders()[particleIndex - 1];

				switch (particleProperty.toLowerCase()) {
					case "color":
						if (args.length < 7) {
							player.sendMessage("Usage: /skdragon particle color <id> <index> <1-255> <1-255> <1-255>");
							break;
						}

						int r = Integer.parseInt(args[4]);
						int g = Integer.parseInt(args[5]);
						int b = Integer.parseInt(args[6]);

						PropertyAPI.setParticleColor(particle, r, g, b);
						player.sendMessage("Set color for particle " + id + " at index " + particleIndex + " to (" + r + ", " + g + ", " + b + ")");

						break;

					case "amount":
						if (args.length < 5) {
							player.sendMessage("Usage: /skdragon particle amount <id> <index> <int>");
							break;
						}

						int amount = Integer.parseInt(args[4]);

						particle.getParticleData().setAmount(amount);
						player.sendMessage("Set amount for particle " + id + " at index " + particleIndex + " to " + amount);
						break;

					case "type":
						if (args.length < 5) {
							player.sendMessage("Usage: /skdragon particle type <id> <index> <particle>");
							break;
						}

						String particleType = args[4];
						if (!Utils.isValidEnum(Particle.class, particleType)) {
							player.sendMessage("The particle type " + particleType + " doesn't exist");
							break;
						}

						Particle type = Particle.valueOf(particleType);
						PropertyAPI.setParticleType(data, particleIndex, type);
						player.sendMessage("Set type for particle " + id + " at index " + particleIndex + " to " + particleType);
						break;

					case "speed":
						if (args.length < 5) {
							player.sendMessage("Usage: /skdragon particle speed <id> <index> <float>");
							break;
						}

						if (particle instanceof DirectionParticle) {
							float speed = Float.parseFloat(args[4]);

							((DirectionParticle) particle).getParticleData().setSpeed(speed);
							player.sendMessage("Set speed for particle " + id + " at index " + particleIndex + " to " + speed);
						} else {
							player.sendMessage("Only directional particles have a speed property");
						}
						break;

					case "direction":
						if (args.length < 7) {
							player.sendMessage("Usage: /skdragon particle direction <id> <index> <x> <y> <z>");
							break;
						}

						if (particle instanceof DirectionParticle) {
							float x = Float.parseFloat(args[4]);
							float y = Float.parseFloat(args[5]);
							float z = Float.parseFloat(args[6]);

							((DirectionParticle) particle).getParticleData().setDirection(new Vector(x, y, z));
							player.sendMessage("Set direction for particle " + id + " at index " + particleIndex + " to (" + x + ", " + y + ", " + z + ")");
						} else {
							player.sendMessage("Only directional particles have a direction property");
						}
						break;

					case "offset":
						if (args.length < 7) {
							player.sendMessage("Usage: /skdragon particle offset <id> <index> <x> <y> <z>");
							break;
						}

						float x = Float.parseFloat(args[4]);
						float y = Float.parseFloat(args[5]);
						float z = Float.parseFloat(args[6]);

						particle.getParticleData().setOffset(x, y, z);
						player.sendMessage("Set offset for particle " + id + " at index " + particleIndex + " to (" + x + ", " + y + ", " + z + ")");
						break;

					default:
						player.sendMessage("Unknown particle property: " + particleProperty);
						break;
				}

			case "ui":
				if (args.length < 3) {
					player.sendMessage("Usage: /skdragon register <id> <effect>");
					break;
				}
				String type = args[2];
				if (!Utils.isValidEnum(ParticleEffect.class, type)) {
					player.sendMessage("The effect " + type + " doesn't exist");
					break;
				}

				EffectAPI.register(id, ParticleEffect.valueOf(type.toUpperCase()), null);
				player.sendMessage("Effect " + id + " registered.");
				break;

			default:
				player.sendMessage("Unknown sub-command: " + subCommand);
				break;
		}

		return true;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		List<String> completions = new ArrayList<>();
		List<String> commands = new ArrayList<>();

		if (args.length == 1) {
			commands.add("register");
			commands.add("unregister");
			commands.add("start");
			commands.add("stop");
			commands.add("effect");
			commands.add("particle");
			StringUtil.copyPartialMatches(args[0], commands, completions);
		} else if (args.length == 2) {
			if (args[0].equals("effect")) {
				commands.add("density");
				commands.add("radius");
				commands.add("extra");
			} else if (args[0].equals("particle")) {
				commands.add("color");
				commands.add("amount");
				commands.add("type");
				commands.add("speed");
				commands.add("direction");
				commands.add("offset");
			}
			StringUtil.copyPartialMatches(args[1], commands, completions);
		} else if (args.length == 3) {
			if (args[0].equals("start")) {
				commands.add("player");
				commands.add("location");
			} else if (args[0].equals("register")) {
				commands = Stream.of(ParticleEffect.values())
						.map(effect -> effect.toString().toLowerCase().replace("_", " "))
						.collect(Collectors.toList());
			}
			StringUtil.copyPartialMatches(args[2], commands, completions);
		} else if (args.length == 4) {
			if (args[0].equals("particle")) {
				Collections.addAll(commands, ParticleUtil.getAllNames());
			}
			StringUtil.copyPartialMatches(args[3], commands, completions);
		}

		Collections.sort(completions);
		return completions;
	}
}