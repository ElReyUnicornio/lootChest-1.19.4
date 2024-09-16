package com.eru.lootchest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.Block;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import java.util.Random;

public class ChestLootHandler {

    public static void registerEvent() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.isClient()) return ActionResult.PASS;
            BlockPos pos = hitResult.getBlockPos();

            if (world.getBlockEntity(pos) instanceof ChestBlockEntity chest) {
                // Extraer y verificar el NBT actual del cofre
                NbtCompound nbt = chest.createNbt();

                if (nbt.contains("used") && nbt.getBoolean("used")) {
                    return ActionResult.PASS;
                }

                if (!nbt.contains("used") || !nbt.getBoolean("used")) {
                    nbt.putBoolean("used", true);
                    chest.readNbt(nbt);
                }

                chest.markDirty();

                // Obtener el bioma
                RegistryEntry<Biome> biome = world.getBiome(pos);
                String biomeId = biome.getKey().get().getValue().toString();

                JsonArray lootTables;
                String chosenLootTable;

                try {
                    // Busca el bioma en la configuración
                    lootTables = BiomeLootMod.getLootTableConfig()
                            .get("biomes").getAsJsonObject().getAsJsonArray(biomeId);

                    if (lootTables == null) {
                        lootTables = BiomeLootMod.getLootTableConfig()
                                .get("biomes").getAsJsonObject().getAsJsonArray("default");
                    }

                    chosenLootTable = lootTables.get(new Random().nextInt(lootTables.size())).getAsString();

                    // Asignar la loot table al cofre
                    chest.setLootTable(new Identifier(chosenLootTable), new Random().nextLong());
                    chest.markDirty();

                    return ActionResult.PASS;
                } catch (Exception e) {
                    e.printStackTrace();
                    return ActionResult.FAIL;
                }
            }

            return ActionResult.PASS;
        });
    }
}
