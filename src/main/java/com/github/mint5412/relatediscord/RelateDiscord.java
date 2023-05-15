package com.github.mint5412.relatediscord;

import com.github.mint5412.relatediscord.Systems.System;
import com.github.mint5412.relatediscord.Systems.YMLManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RelateDiscord extends JavaPlugin {

    private String token;
    private JDA bot;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getServer().getPluginManager().registerEvents(new LoginEvent(), this);
        LoadToken();
        if (this.token == null) return;
        try {
            this.bot = JDABuilder.createDefault(this.token)
                    .setRawEventsEnabled(true)
                    .addEventListeners(new ReceivedChat())
                    .setActivity(Activity.playing("サーバー監視"))
                    .build();
            this.bot.awaitReady();

            CommandData commandData = new CommandData("whitelist", "add whitelist by token.");
            commandData.addOption(OptionType.STRING, "token", "token shown first join.");

            this.bot.updateCommands()
                    .addCommands(commandData)
                    .queue();

            java.lang.System.out.println("add command completed!");
        } catch (Exception e) {
            System.Error(e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.bot.shutdownNow();
    }

    private void LoadToken() {
        YMLManager manager = new YMLManager("./BotToken.yml");
        if (!manager.getKeys(false).contains("token")) {
            manager.update("token", "token here");
            System.Error("token is not setting");
        } else this.token = manager.getString("token");
    }
}
