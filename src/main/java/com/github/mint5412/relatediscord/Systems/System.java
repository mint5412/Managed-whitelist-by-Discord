package com.github.mint5412.relatediscord.Systems;

import com.github.mint5412.relatediscord.RelateDiscord;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class System {
    public static void Error(String message) {
        java.lang.System.out.println("Error: " + message);
        Bukkit.getServer().getPluginManager().disablePlugin(JavaPlugin.getPlugin(RelateDiscord.class));
    }
}
