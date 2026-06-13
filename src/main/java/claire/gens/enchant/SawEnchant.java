package claire.gens.enchant;

import claire.gens.Peakagens;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class SawEnchant {
    public static final String MOD_ID = "peakagens";
    public static final ResourceKey<Enchantment> SAW_KEY = ResourceKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath(MOD_ID, "saw"));

    public static void init() {
        Peakagens.LOGGER.info("Init Saw");
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            Peakagens.LOGGER.info("Init Break");
            if (world.isClientSide()) {return;}
            ItemStack stack = player.getMainHandItem();
            if (!(stack.get(DataComponents.TOOL) != null && Objects.requireNonNull(stack.get(DataComponents.TOOL)).isCorrectForDrops(state))) {return;}

            boolean hasSaw = stack.getEnchantments().entrySet().stream().anyMatch(entry -> entry.getKey()
                                    .unwrapKey()
                                    .map(key -> key.equals(SAW_KEY))
                                    .orElse(false)
                    );

            if (!hasSaw) {return;}

            Peakagens.LOGGER.info("Passed all checks");

            List<BlockPos> checked = new ArrayList<>();
            List<BlockPos> list = List.of(pos);
            List<BlockPos> listChange = new ArrayList<>();
            listChange.add(pos);
            while (!(list.size() == checked.size()) && checked.size() <= Objects.requireNonNull(world.getServer()).getGameRules().get(Peakagens.maxChainedBlocks)) {
                for (BlockPos next : list.stream().toList()) {
                    if (!checked.contains(next)) {
                        List<BlockPos> newList = Gimme((ServerLevel) world,next,(ServerPlayer) player);
                        for (BlockPos left : newList) {
                            if (!listChange.contains(left) && !checked.contains(left)) {
                                listChange.add(left);
                            }
                        }
                        checked.add(next);
                    }
                }
                Peakagens.LOGGER.info("Looped");
                list = listChange;
            }
            Peakagens.LOGGER.info("Destroying Now...");
            for (BlockPos next : list) {
                world.destroyBlock(next,true,player);
            }
            Peakagens.LOGGER.info("Destroyed");
        });
    }

    public static List<BlockPos> Gimme(ServerLevel level, BlockPos start, ServerPlayer Player) {
        List<BlockPos> list = new ArrayList<>();
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                    Peakagens.LOGGER.info(String.valueOf(x-1)+" "+String.valueOf(y-1)+" "+String.valueOf(z-1));
                    BlockPos yo = start.offset(x-1,y-1,z-1);
                    BlockState blockState = level.getBlockState(yo);
                    if (blockState.is(BlockTags.LOGS) || blockState.is(BlockTags.LEAVES)) {
                        list.add(yo);
                    }
                }
            }
        }
        return list;
    }
}