package claire.gens.client.mixin;

import claire.gens.ItemStuff;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class HideTag<T extends Entity> {

    @Inject(method = "shouldShowName", at = @At("HEAD"), cancellable = true)
    private void hideNametagWithArmor(T entity, double distanceToCameraSq, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof Player player) {
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ItemStuff.cardboardbox)) {
                cir.setReturnValue(false);
            }
            cir.setReturnValue(player.isCustomNameVisible() || player.shouldShowName());
        }
    }
}