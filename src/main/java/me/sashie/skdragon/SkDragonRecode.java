package me.sashie.skdragon;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAPIException;
import ch.njol.skript.SkriptAddon;
import me.sashie.skdragon.debug.SkriptNode;
import me.sashie.skdragon.util.versions.SkriptAdapter;
import me.sashie.skdragon.util.versions.V2_3;
import me.sashie.skdragon.util.versions.V2_4;
import me.sashie.skdragon.util.versions.V2_6;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Logger;

public final class SkDragonRecode extends JavaPlugin {

    public final static Logger LOGGER = Bukkit.getLogger();

    private static SkDragonRecode instance;

    private static SkriptAdapter adapter;


    public SkDragonRecode() {
        if (instance == null) {
            instance = this;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void onEnable() {
        Plugin skript = Bukkit.getServer().getPluginManager().getPlugin("Skript");
        if (skript != null) {
            if (Skript.isAcceptRegistrations()) {
                try {
                    SkriptAddon addonInstance = Skript.registerAddon(this);
                    addonInstance.loadClasses("me.sashie.skdragon", "skript");
                } catch (SkriptAPIException e) {
                    error("Somehow you loaded skDragon after Skript has already finished registering addons, which Skript does not allow! Did you load this using a plugin manager?");
                } catch (IOException e) {
                    e.printStackTrace();
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

            } else {
                Bukkit.getPluginManager().disablePlugin(this);
                error("Skript is not accepting registrations.");
            }
        } else {
            Bukkit.getPluginManager().disablePlugin(this);
            error("Skript not found, plugin disabled.");
        }

    }

    @Override
    public void onDisable() {

    }

    public static SkDragonRecode getInstance() {
        if (instance == null) {
            throw new IllegalStateException();
        }
        return instance;
    }

    public static SkriptAdapter getSkriptAdapter() {
        return adapter;
    }

    public static void warn(String error, SkriptNode skriptNode) {
        LOGGER.warning("[skDragon] " + error + " " + skriptNode.toString());
    }

    public static void error(String error) {
        LOGGER.severe("[skDragon] " + error);
    }

    public static void error(String error, SkriptNode skriptNode) {
        LOGGER.severe("[skDragon] " + error + " " + skriptNode.toString());
    }

    public static void message(final CommandSender commandSender, String message) {
        commandSender.sendMessage(message);
    }
}
