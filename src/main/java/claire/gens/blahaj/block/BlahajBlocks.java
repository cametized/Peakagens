package claire.gens.blahaj.block;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlahajBlocks {
    public static final Identifier BLAHAJ_ID = Identifier.fromNamespaceAndPath("blahaj", "blue_shark");

    public static final Identifier BREAD_ID = Identifier.fromNamespaceAndPath("blahaj", "bread");

    public static Block BLAHAJ_BLOCK;

    public static Block BREAD_BLOCK;

    public static List<Block> BLOCKS = new ArrayList<>();

    public static List<Item> ITEMS = new ArrayList<>();

    public static void register() {
        BLAHAJ_BLOCK = registerCuddlyBlockAndItem(BLAHAJ_ID, "block.blahaj.blue_shark.tooltip");
        BREAD_BLOCK = registerCuddlyBlockAndItem(BREAD_ID, null);
    }

    public static Block registerCuddlyBlockAndItem(Identifier id, String tooltip) {
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id);
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);
        Block block = (Block)Registry.register(BuiltInRegistries.BLOCK, id, new CuddlyBlock(BlockBehaviour.Properties.ofFullCopy((BlockBehaviour)Blocks.WHITE_WOOL).setId(blockKey)));
        Item item = (Item)Registry.register(BuiltInRegistries.ITEM, id, new CuddlyItem(block, (new Item.Properties())
                .setId(itemKey)
                .useBlockDescriptionPrefix()
                .stacksTo(1)
                .attributes(CuddlyItem.createAttributeModifiers())
                .equippableUnswappable(EquipmentSlot.HEAD), tooltip));
        BLOCKS.add(block);
        ITEMS.add(item);
        return block;
    } // nuke the whole generation

    public static void registerClient() {}
}