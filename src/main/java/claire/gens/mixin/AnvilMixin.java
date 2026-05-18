package claire.gens.mixin;

import claire.gens.ItemStuff;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.Set;

@Mixin(AnvilMenu.class)
public class AnvilMixin extends ItemCombinerMenu {
    public AnvilMixin(@Nullable MenuType<?> menuType, int containerId, Inventory inventory, ContainerLevelAccess access, ItemCombinerMenuSlotDefinition itemInputSlots) {
        super(menuType, containerId, inventory, access, itemInputSlots);
    }

    @Unique
    public boolean getHasEnchanting() {
        return player.getInventory().hasAnyOf(Set.of(ItemStuff.enchanting));
    }

    @ModifyConstant(method = "createResult", constant = @Constant(intValue = 40))
    public int maxLevelSet(int constant) {
        return getHasEnchanting() ? Integer.MAX_VALUE : constant;
    }
    @ModifyConstant(method = "createResult", constant = @Constant(intValue = 39))
    public int beforeMaxLevelSet(int constant) {
        return getHasEnchanting() ? Integer.MAX_VALUE-1 : constant;
    }

    // ignore these
    @Unique
    protected void onTake(Player player, ItemStack carried) {
    }

    @Unique
    protected boolean isValidBlock(BlockState state) {
        return false;
    }

    @Unique
    public void createResult() {
    }
}
