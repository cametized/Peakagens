package claire.gens.enchant;

import claire.gens.Peakagens;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class SawEnchant {
    public static final String MOD_ID = "peakagens";
    public static final ResourceKey<Enchantment> SAW_KEY = ResourceKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath(MOD_ID, "saw"));
    private static final int MAX_LOGS = 72;

    public static void init() {
        Peakagens.LOGGER.info("Init Saw");
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            Peakagens.LOGGER.info("Init Break");
            if (world.isClientSide()) {return;}
            if (!state.is(BlockTags.LOGS)) {return;}
            ItemStack stack = player.getMainHandItem();
            if (!(stack.getItem() instanceof AxeItem)) {return;}

            boolean hasSaw = stack.getEnchantments().entrySet().stream().anyMatch(entry -> entry.getKey()
                                    .unwrapKey()
                                    .map(key -> key.equals(SAW_KEY))
                                    .orElse(false)
                    );

            if (!hasSaw) {return;}
            KillYuu((ServerLevel) world, pos, (ServerPlayer) player);
        });
    }

    private static void KillYuu(ServerLevel level, BlockPos start, ServerPlayer player) {
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x == 0 && y == 0 && z == 0) continue;
                    queue.add(start.offset(x, y, z));
                }
            }
        }

        while (!queue.isEmpty() && visited.size() < MAX_LOGS) {
            BlockPos current = queue.poll();

            if (!visited.add(current)) continue;
            if (!level.getBlockState(current).is(BlockTags.LOGS)) continue;

            level.destroyBlock(current, true, player);

            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        queue.add(current.offset(x, y, z));
                    }
                }
            }
        }
    }
    }