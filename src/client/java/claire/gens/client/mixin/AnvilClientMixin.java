package claire.gens.client.mixin;

import claire.gens.ItemStuff;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.Set;

@Mixin(AnvilScreen.class)
public class AnvilClientMixin extends ItemCombinerScreen<AnvilMenu> {
    @Shadow
    @Final
    private Player player;

    public AnvilClientMixin(AnvilMenu menu, Inventory inventory, Component title, Identifier menuResource) {
        super(menu, inventory, title, menuResource);
    }

    @ModifyConstant(method = "extractLabels",constant = @Constant(intValue = 40))
    public int init(int constant) {
        if (player.getInventory().hasAnyOf(Set.of(ItemStuff.enchanting))) {
            return Integer.MAX_VALUE;
        }
        return constant;
    }

    @Unique
    protected void extractErrorIcon(GuiGraphicsExtractor graphics, int xo, int yo) {
    }
}
