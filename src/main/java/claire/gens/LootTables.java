package claire.gens;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

public class LootTables {
    public static void modify() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (BuiltInLootTables.ABANDONED_MINESHAFT.equals(key) && source.isBuiltin()) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.15f))
                        .add(LootItem.lootTableItem(ItemStuff.grindrails));
                tableBuilder.withPool(poolBuilder);
            } else if (BuiltInLootTables.BASTION_TREASURE.equals(key) &&  source.isBuiltin()) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.25f))
                        .add(LootItem.lootTableItem(ItemStuff.yag));
                tableBuilder.withPool(poolBuilder);
            } else if (BuiltInLootTables.IGLOO_CHEST.equals(key) &&  source.isBuiltin()) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.75f))
                        .add(LootItem.lootTableItem(ItemStuff.cleanup));
                tableBuilder.withPool(poolBuilder);
            } else if (BuiltInLootTables.NETHER_BRIDGE.equals(key) &&  source.isBuiltin()) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.50f))
                        .add(LootItem.lootTableItem(ItemStuff.disc1));
                tableBuilder.withPool(poolBuilder);
            } else if (BuiltInLootTables.PILLAGER_OUTPOST.equals(key) &&  source.isBuiltin()) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.65f))
                        .add(LootItem.lootTableItem(ItemStuff.test11));
                tableBuilder.withPool(poolBuilder);
            } else if (BuiltInLootTables.SHIPWRECK_TREASURE.equals(key) &&  source.isBuiltin()) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.35f))
                        .add(LootItem.lootTableItem(Items.ENCHANTED_BOOK));
                tableBuilder.withPool(poolBuilder);
            }
        });
    }
}