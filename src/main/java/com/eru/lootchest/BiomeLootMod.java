package com.eru.lootchest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BiomeLootMod implements ModInitializer {
    private static final Path CONFIG_PATH = Paths.get("config/biome_loot.json");
    private static JsonObject lootTableConfig;

    @Override
    public void onInitialize() {
        loadConfiguration();
    }

    public static void loadConfiguration() {
        try {
            lootTableConfig = new Gson().fromJson(new FileReader(CONFIG_PATH.toFile()), JsonObject.class);
            System.out.println("Loaded Biome Loot configuration.");
            System.out.println(lootTableConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JsonObject getLootTableConfig() {
        return lootTableConfig;
    }
}
