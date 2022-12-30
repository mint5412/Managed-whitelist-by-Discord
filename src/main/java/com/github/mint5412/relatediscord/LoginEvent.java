package com.github.mint5412.relatediscord;

import com.github.mint5412.relatediscord.Systems.YMLManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Map;
import java.util.Random;

public class LoginEvent implements Listener {
    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if (player.isWhitelisted()) {
            Bukkit.getServer().reloadWhitelist();
            if (!player.isWhitelisted()) player.setWhitelisted(true);
            return;
        }
        YMLManager manager = new YMLManager("./PlayerTokens.yml");

        for (Map.Entry<String, Object> map : manager.getValues(false).entrySet()) {
            Object value = map.getValue();
            if (!(value instanceof String)) continue;
            String s = (String) value;
            // プレイヤーに対して既にトークンを発行しているか確認
            if (player.getUniqueId().toString().equals(s)) {
                // 発行していればトークンを表示してキック
                player.kickPlayer(String.valueOf(map.getKey()));
                return;
            }
        }

        //ランダムトークンの生成
        int token = new Random().nextInt(10000);
        // トークンに重複があれば再生成
        while (manager.contains(String.valueOf(token))) {
            token = new Random().nextInt(10000);
        }

        // トークンとプレイヤーの紐づけとプレイヤーのキック
        manager.update(String.valueOf(token), player.getUniqueId().toString());
        player.kickPlayer(String.valueOf(token));
    }
}
