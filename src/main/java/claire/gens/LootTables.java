package claire.gens;

import claire.gens.enchant.FloristEnchant;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

import java.util.List;

public class LootTables {
    public static void modify() {
        // discs added: grindrails, cleanup, disc1, test11, yag, colonize
        // discs not added: battle, menu5, revolvershowdown, treeahohess, flaxsong
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (BuiltInLootTables.ABANDONED_MINESHAFT.equals(key) && source.isBuiltin()) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.15f))
                        .add(LootItem.lootTableItem(ItemStuff.grindrails));
                tableBuilder.withPool(poolBuilder);
            } else if (List.of(BuiltInLootTables.TRIAL_CHAMBERS_CORRIDOR_POT,BuiltInLootTables.TRIAL_CHAMBERS_INTERSECTION_BARREL,BuiltInLootTables.TRIAL_CHAMBERS_CHAMBER_DISPENSER).equals(key) &&  source.isBuiltin()) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.75f))
                        .add(LootItem.lootTableItem(ItemStuff.colonize));
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
                        .when(LootItemRandomChanceCondition.randomChance(0.5f))
                        .add(LootItem.lootTableItem(Items.ENCHANTED_BOOK).apply(new SetEnchantmentsFunction.Builder().withEnchantment(registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath(FloristEnchant.MOD_ID, "florist"))), ConstantValue.exactly(1))));
                tableBuilder.withPool(poolBuilder);
            } else if (BuiltInLootTables.SIMPLE_DUNGEON.equals(key) &&  source.isBuiltin()) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.10f))
                        .add(LootItem.lootTableItem(ItemStuff.cloth));
                LootPool.Builder poolBuilder1 = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.35f))
                        .add(LootItem.lootTableItem(Items.ENCHANTED_BOOK).apply(new SetEnchantmentsFunction.Builder().withEnchantment(registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath(FloristEnchant.MOD_ID, "florist"))), ConstantValue.exactly(1))));
                tableBuilder.withPool(poolBuilder).withPool(poolBuilder1);
            } else if (BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_UNIQUE.equals(key) && source.isBuiltin()) {
                LootItemFunction lootItemFunction = new LootItemFunction() {
                    @Override
                    public MapCodec<? extends LootItemFunction> codec() {
                        return null;
                    }

                    @Override
                    public ItemStack apply(ItemStack itemStack, LootContext lootContext) {
                        ItemStack gamer = ItemStuff.gemList.get(lootContext.getLevel().getRandom().nextIntBetweenInclusive(0,ItemStuff.gemList.size()-1)).getDefaultInstance();
                        return itemStack.is(Items.HEAVY_CORE) ? gamer : itemStack;
                    }
                };
                tableBuilder.apply(lootItemFunction);
            } else if (List.of(BuiltInLootTables.BASTION_TREASURE,BuiltInLootTables.BASTION_BRIDGE,BuiltInLootTables.BASTION_OTHER,BuiltInLootTables.BASTION_HOGLIN_STABLE).contains(key) && source.isBuiltin()) {
                LootPool.Builder poolBuilder = BuiltInLootTables.BASTION_TREASURE.equals(key) ?
                        LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(LootItemRandomChanceCondition.randomChance(0.25f)).add(LootItem.lootTableItem(ItemStuff.yag))
                        : LootPool.lootPool();
                LootItemFunction lootItemFunction = new LootItemFunction() {
                    @Override
                    public MapCodec<? extends LootItemFunction> codec() {
                        return null;
                    }

                    @Override
                    public ItemStack apply(ItemStack itemStack, LootContext lootContext) {
                        ItemStack gamer = ItemStuff.gemList.get(lootContext.getLevel().getRandom().nextIntBetweenInclusive(0,ItemStuff.gemList.size()-1)).getDefaultInstance();
                        return itemStack.is(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE) ? gamer : itemStack;
                    }
                };
                tableBuilder.apply(lootItemFunction).withPool(poolBuilder);

            }
        });
    }
}