package com.eru.lootchest.item;

import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;

public class CrystalSword extends SwordItem {
    public CrystalSword() {
        super(ToolMaterials.DIAMOND, 4, 2.4f, new Item.Settings());
    }
}
