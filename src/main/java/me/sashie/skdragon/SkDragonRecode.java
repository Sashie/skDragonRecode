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
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Logger;

public final class SkDragonRecode extends JavaPlugin {

    public final static Logger LOGGER = Bukkit.getLogger();
    private static SkDragonRecode INSTANCE;
    private static SkriptAdapter adapter;

    @Override
    public void onEnable() {
        INSTANCE = this;

        Plugin skript = Bukkit.getServer().getPluginManager().getPlugin("Skript");
        if (skript != null && Skript.isAcceptRegistrations()) {
            try {
                SkriptAddon addonInstance = Skript.registerAddon(this);
                addonInstance.loadClasses("me.sashie.skdragon", "skript");
            } catch (IOException e) {
                error("Something unnormal happened...");
                e.printStackTrace();
            }

        } else {
            error("Unable to find Skript, or Skript is not accepting registrations.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        if ((Skript.getVersion().getMajor() >= 3 || (Skript.getVersion().getMajor() == 2 && Skript.getVersion().getMinor() >= 6)))
            adapter = new V2_6();
        else if ((Skript.getVersion().getMajor() == 2 && Skript.getVersion().getMinor() >= 4))
            adapter = new V2_4();
        else
            adapter = new V2_3();

        //Plugin skriptYaml = Bukkit.getServer().getPluginManager().getPlugin("skript-yaml");
        //if (skriptYaml != null) {
        //	SkriptYaml.registerTag(this, "particle", Particle.class, new ParticleRepresentedClass(), new ParticleConstructedClass());
        //	Bukkit.broadcastMessage("skript-yaml found, hooks enabled.");
        //}

        Metrics metrics = new Metrics(this, 1208);
        metrics.addCustomChart(new SimplePie("skript_version", () -> Skript.getVersion().toString()));


        TabExecutor tabExecutor = new EffectCommand();
        this.getCommand("skdragon").setExecutor(tabExecutor);
        this.getCommand("skdragon").setTabCompleter(tabExecutor);
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
        return adapter;
    }

    public static void warn(String error, SkriptNode skriptNode) {
        LOGGER.warning("[skDragon] " + error + (skriptNode != null ? " " + skriptNode : ""));
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
