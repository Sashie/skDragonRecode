/*
	This file is part of skDragon - A Skript addon

	Copyright (C) 2016 - 2024  Sashie

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

package me.sashie.skdragon;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import me.sashie.skdragon.commands.EffectCommand;
import me.sashie.skdragon.debug.SkriptNode;
import me.sashie.skdragon.util.versions.SkriptAdapter;
import me.sashie.skdragon.util.versions.V2_3;
import me.sashie.skdragon.util.versions.V2_4;
import me.sashie.skdragon.util.versions.V2_6;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public final class SkDragonRecode extends JavaPlugin {

	public final static Logger LOGGER = Bukkit.getLogger();
	private static SkDragonRecode INSTANCE;
	private static SkriptAdapter ADAPTER;
	private final static String CONFIG_VERSION = "1";

	@Override
	public void onEnable() {
		INSTANCE = this;

		Plugin skript = Bukkit.getServer().getPluginManager().getPlugin("Skript");
		if (skript != null && Skript.isAcceptRegistrations()) {
			try {
				SkriptAddon addonInstance = Skript.registerAddon(this);
				addonInstance.loadClasses("me.sashie.skdragon", "skript");
			} catch (IOException e) {
				error("Something abnormal happened...");
				e.printStackTrace();
			}

			if ((Skript.getVersion().getMajor() >= 3 || (Skript.getVersion().getMajor() == 2 && Skript.getVersion().getMinor() >= 6)))
				ADAPTER = new V2_6();
			else if ((Skript.getVersion().getMajor() == 2 && Skript.getVersion().getMinor() >= 4))
				ADAPTER = new V2_4();
			else
				ADAPTER = new V2_3();

			//Plugin skriptYaml = Bukkit.getServer().getPluginManager().getPlugin("skript-yaml");
			//if (skriptYaml != null) {
			//	SkriptYaml.registerTag(this, "particle", Particle.class, new ParticleRepresentedClass(), new ParticleConstructedClass());
			//	Bukkit.broadcastMessage("skript-yaml found, hooks enabled.");
			//}

			Metrics metrics = new Metrics(this, 1208);
			metrics.addCustomChart(new SimplePie("skript_version", () -> Skript.getVersion().toString()));

			boolean useCommands = true;

			File configFile = new File(getDataFolder(), "config.yml");
			if (configFile.exists()) {
				FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
				String configVersion = config.getString("Version");
				useCommands = config.getBoolean("Commands.Enabled", true);

				if (!CONFIG_VERSION.equals(configVersion)) {
					warn("New config found, updating file!");

					// Replace with new config file from jar and reload it
					this.saveResource("config.yml", true);
					config = YamlConfiguration.loadConfiguration(configFile);

					// Restore data
					config.set("Commands.Enabled", useCommands);

					// Save the new config
					try {
						config.save(configFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				this.saveResource("config.yml", false);
			}

			if (useCommands) {
				TabExecutor tabExecutor = new EffectCommand();
				PluginCommand command = this.getCommand("skdragon");

				assert command != null;
				command.setExecutor(tabExecutor);
				command.setTabCompleter(tabExecutor);
			} else {
				info("Command disabled");
			}
		} else {
			error("Unable to find Skript, or Skript is not accepting registrations.");
		}
	}

	@Override
	public void onDisable() {
		EffectAPI.unregisterAll();
	}

	public static SkDragonRecode getInstance() {
		if (INSTANCE == null) {
			throw new IllegalStateException();
		}
		return INSTANCE;
	}

	public static SkriptAdapter getSkriptAdapter() {
		return ADAPTER;
	}

	public static void info(String info) {
		LOGGER.info("[skDragon] " + info);
	}

	public static void warn(String warn) {
		LOGGER.warning("[skDragon] " + warn);
	}

	public static void warn(String warn, SkriptNode skriptNode) {
		LOGGER.warning("[skDragon] " + warn + (skriptNode != null ? " " + skriptNode : ""));
	}

	public static void error(String error) {
		LOGGER.severe("[skDragon] " + error);
	}

	public static void error(String error, SkriptNode skriptNode) {
		LOGGER.severe("[skDragon] " + error + (skriptNode != null ? " " + skriptNode : ""));
	}

	public static void message(final CommandSender commandSender, String message) {
		commandSender.sendMessage(message);
	}
}
