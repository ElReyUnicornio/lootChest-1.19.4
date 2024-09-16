package com.eru.lootchest;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ReloadCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(
                    LiteralArgumentBuilder.<ServerCommandSource>literal("chestloot")
                            .then(LiteralArgumentBuilder.<ServerCommandSource>literal("reload")
                                    .executes(context -> {
                                        BiomeLootMod.loadConfiguration();  // Recargar el archivo JSON
                                        context.getSource().sendFeedback(Text.of("Chest loot configuration reloaded."), false);
                                        return Command.SINGLE_SUCCESS;
                                    }))
            );
        });
    }
}