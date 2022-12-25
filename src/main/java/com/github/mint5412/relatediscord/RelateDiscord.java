package com.github.mint5412.relatediscord;

import com.github.mint5412.relatediscord.Systems.System;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class RelateDiscord extends JavaPlugin {

    private String token;
    private JDA bot;
    @Override
    public void onEnable() {
        // Plugin startup logic
        LoadToken();
        try {
            this.bot = JDABuilder.createDefault(this.token)
                    .setRawEventsEnabled(true)
                    .setActivity(Activity.playing("サーバーを監視中"))
                    .build()
            ;
        } catch (Exception e) {
            System.Error(e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void LoadToken() {
        File tokenFile = new File("./BotToken.yml");
        System.Error("token file is failed");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(tokenFile);
        this.token = config.getString("token");
        if (this.token == null) System.Error("token is not setting");
    }

    public JDA getBot() {return this.bot;}
}
