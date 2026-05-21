package claire.gens.armor;

import claire.gens.Peakagens;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

public class Overpower {
    public static final int BASE_DURABILITY = 1824;

    public static final ResourceKey<EquipmentAsset> BOX_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "hidebox"));

    public static final TagKey<Item> REPAIR_BOX = TagKey.create(BuiltInRegistries.ITEM.key(), Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "repair_hide"));

    public static final ArmorMaterial INSTANCE = new ArmorMaterial(
            BASE_DURABILITY,
            Map.of(
                    ArmorType.HELMET, 2999,
                    ArmorType.CHESTPLATE, 8,
                    ArmorType.LEGGINGS, 6,
                    ArmorType.BOOTS, 3
            ),
            5,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            0.0F,
            0.0F,
            REPAIR_BOX,
            BOX_KEY
    );
}
