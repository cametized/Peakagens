package claire.gens.armor;

import claire.gens.Peakagens;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

import static net.minecraft.world.item.equipment.ArmorMaterials.makeDefense;

public class CardboardBox {
    public static final int BASE_DURABILITY = 125;
    public static final ResourceKey<EquipmentAsset> BOX_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "cardboardbox"));
    public static final TagKey<Item> REPAIR_BOX = TagKey.create(BuiltInRegistries.ITEM.key(), Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "repair_box"));
    public static final ArmorMaterial INSTANCE = new ArmorMaterial(
            5,
            Map.of(
                    ArmorType.HELMET, 1,
                    ArmorType.CHESTPLATE, 8,
                    ArmorType.LEGGINGS, 6,
                    ArmorType.BOOTS, 3
            ),
            5,
            SoundEvents.ARMOR_EQUIP_GENERIC,
            0.0F,
            0.0F,
            REPAIR_BOX,
            BOX_KEY
    );

    public static final int BASE_DURABILITYMASK = 125;
    public static final ResourceKey<EquipmentAsset> MASK_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "mask"));
    public static final TagKey<Item> REPAIR_MASK = TagKey.create(BuiltInRegistries.ITEM.key(), Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "repair_mask"));
    public static final ArmorMaterial MASKINSTANCE = new ArmorMaterial(
            5,
            Map.of(
                    ArmorType.HELMET, 1,
                    ArmorType.CHESTPLATE, 8,
                    ArmorType.LEGGINGS, 6,
                    ArmorType.BOOTS, 3
            ),
            5,
            SoundEvents.ARMOR_EQUIP_GENERIC,
            0.0F,
            0.0F,
            REPAIR_MASK,
            MASK_KEY
    );

    public static final int BASE_DURABILITY1 = 33;
    public static final ResourceKey<EquipmentAsset> BOX_KEY1 = ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "iron"));
    public static final TagKey<Item> REPAIR_BOX1 = TagKey.create(BuiltInRegistries.ITEM.key(), Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "freaky"));
    public static final ArmorMaterial INSTANCE1 = new ArmorMaterial(
            BASE_DURABILITY1,
            Map.of(
                    ArmorType.HELMET, 3,
                    ArmorType.CHESTPLATE, 8,
                    ArmorType.LEGGINGS, 6,
                    ArmorType.BOOTS, 3
            ),
            10,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            2.0F,
            0.0F,
            REPAIR_BOX1,
            BOX_KEY1
    );
}
