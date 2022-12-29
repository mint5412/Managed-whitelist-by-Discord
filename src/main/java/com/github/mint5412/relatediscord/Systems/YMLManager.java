package com.github.mint5412.relatediscord.Systems;

import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class YMLManager extends YamlConfiguration {

    private final File file;
    public YMLManager(String filePath) {
        this.file = new File(filePath);
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (Exception e) {
                System.Error(e.getMessage());
            }
        }
        try {
            this.load(this.file);
        } catch (Exception e) {System.Error(e.getMessage());}
    }
    public void update(@NotNull String path, Object value) {
        this.set(path, value);
        try {
            this.save(this.file);
        } catch (IOException e) {
            System.Error(e.getMessage());
        }
    }
}
