package com.eru.lootchest.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlockEntity.class)
public abstract class ChestBlockEntityMixin {

	private boolean used = false;

	@Inject(method = "readNbt", at = @At("HEAD"))
	private void readCustomNbt(NbtCompound nbt, CallbackInfo info) {
		if (nbt.contains("used")) {
			this.used = nbt.getBoolean("used");
		}
	}

	@Inject(method = "writeNbt", at = @At("HEAD"))
	private void writeCustomNbt(NbtCompound nbt, CallbackInfo info) {
		nbt.putBoolean("used", this.used);
	}
}