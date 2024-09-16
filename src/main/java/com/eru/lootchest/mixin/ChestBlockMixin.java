package com.eru.lootchest.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlock.class)
public class ChestBlockMixin {
    @Inject(method = "onPlaced", at = @At("HEAD"))
    public void onPlacedCustom(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        //se verifica si el jugador está en modo supervicencia y si lo está se agrega el NBT used al cofre
        if (!world.isClient && placer instanceof PlayerEntity && !((PlayerEntity) placer).isCreative() && !placer.isSpectator()) {
            BlockEntity block = world.getBlockEntity(pos);

            if (block instanceof ChestBlockEntity) {
                NbtCompound nbt = block.createNbt();
                nbt.putBoolean("used", true);
                block.readNbt(nbt);
                block.markDirty();
            }
        }
    }
}
