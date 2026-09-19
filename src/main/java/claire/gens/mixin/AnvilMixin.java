package claire.gens.mixin;

import claire.gens.FragmentItem;
import claire.gens.FragmentType;
import claire.gens.ItemStuff;
import claire.gens.ModComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.StringUtil;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Mixin(AnvilMenu.class)
public class AnvilMixin extends ItemCombinerMenu {
    @Shadow
    @Final
    private DataSlot cost;

    @Shadow
    private @Nullable String itemName;

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

    @Inject(method = "createResult", at = @At("RETURN"))
    public void init(CallbackInfo ci) {
        if (resultSlots.getItem(0).isEmpty()) {
            if (inputSlots.getItem(0).getItem() instanceof FragmentItem fragmentItem && inputSlots.getItem(1).getItem() instanceof FragmentItem fragmentItem1) {
                List<FragmentType> validTypes = List.of(FragmentType.PotionPassive,FragmentType.PotionUse,FragmentType.PotionUseAmplifier);
                if (validTypes.contains(fragmentItem.type) && validTypes.contains(fragmentItem1.type)) {
                    ItemStack itemStack = inputSlots.getItem(0);
                    ItemStack itemStack1 = inputSlots.getItem(1);
                    ItemStack result = itemStack.copy();
                    int price = 1;
                    int level = itemStack.getOrDefault(ModComponents.FragmentLevel, 0);
                    int level1 = itemStack1.getOrDefault(ModComponents.FragmentLevel, 0);
                    if (level == level1 && level+1 < 4) {
                        if (!Objects.equals(this.itemName, itemStack.getHoverName().toString()) && !StringUtil.isBlank(this.itemName)) {
                            if (itemStack.getItem().getName(itemStack) == itemStack1.getOrDefault(DataComponents.CUSTOM_NAME, Component.empty())) {
                                result.remove(DataComponents.CUSTOM_NAME);
                            } else {
                                result.set(DataComponents.CUSTOM_NAME, Component.literal(this.itemName));
                            }
                            price += 1;
                        }
                        result.set(DataComponents.ITEM_MODEL, Identifier.parse("peakagens:fragment" + String.valueOf(level+2)));
                        result.set(ModComponents.FragmentLevel, level+1);
                        this.resultSlots.setItem(0, result);
                        this.cost.set(price);
                    }
                }
            }
        }
    }

    @Unique
    public void createResult() {
    }
}
