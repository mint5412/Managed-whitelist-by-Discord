package com.github.mint5412.relatediscord;

import com.github.mint5412.relatediscord.Systems.YMLManager;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.UUID;

public class ReceivedChat extends ListenerAdapter {


    @Override
    public void onSlashCommand(SlashCommandEvent e) {
        if (!e.getName().equals("whitelist")) return;

        List<OptionMapping> option = e.getOptionsByName("token");
        if (option.isEmpty()) return;

        YMLManager manager = new YMLManager("./PlayerTokens.yml");
        String path = option.get(0).getAsString();

        if (!manager.contains(path)) {
            e.reply(path + "は有効なトークンではありません。")
                    .setEphemeral(true)
                    .queue();
            return;
        }
        String uid = manager.getString(path);
        if (uid == null) {
            e.reply("プレイヤーを取得できませんでした。")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(UUID.fromString(uid));
        player.setWhitelisted(true);

        manager.update(path, null);
        e.reply("ホワイトリストへの追加に成功しました!")
                .setEphemeral(true)
                .queue();
        e.getChannel().sendMessage("ようこそ" + player.getName() + "！").queue();

    }
}
