package claire.gens;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ShearWheat {
    public static void register() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            if (player.isSpectator()) return InteractionResult.PASS;

            BlockPos pos = hitResult.getBlockPos();
            BlockState state = level.getBlockState(pos);
            ItemStack stack = player.getItemInHand(hand);

            if (state.is(Blocks.WHEAT) && state.getValue(CropBlock.AGE) == 7 && stack.is(Items.SHEARS)) {
                handleShearing(level, player, pos, state, stack, hand);
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.PASS;
        });
    }

    private static void handleShearing(Level level, Player player, BlockPos pos, BlockState state, ItemStack stack, net.minecraft.world.InteractionHand hand) {
        if (!level.isClientSide()) {
            Block.popResource(level, pos, new ItemStack(ItemStuff.rice, 2));
            level.setBlock(pos, state.setValue(CropBlock.AGE, 0), 3);
            stack.hurtAndBreak(1, player, hand);
            player.playSound(SoundEvents.CROP_BREAK, 1.0F, 1.0F);
        }
    }
}