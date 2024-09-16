package com.eru.lootchest;

import com.eru.lootchest.item.CrystalSword;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LootChest implements ModInitializer {
	public static final String MOD_ID = "loot-chest";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Item CRYSTAL_SWORD = new CrystalSword();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		//registra la espada
		Item crystal_sword = Registry.register(Registries.ITEM, new Identifier(MOD_ID, "crystal_sword"), CRYSTAL_SWORD);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
			entries.add(crystal_sword);
		});

		//registra el mod
		BiomeLootMod.loadConfiguration();
		ReloadCommand.register();
		ChestLootHandler.registerEvent();
    }
}