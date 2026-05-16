package claire.gens.mixin;

import claire.gens.ItemStuff;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class HideChat {
    @Inject(method = "getDisplayName", at = @At("RETURN"), cancellable = true)
    private void thatsWhatThePointOfthemaskIs(CallbackInfoReturnable<Component> cir) {
        Player player = (Player) (Object) this; // The answer is i have no idea
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ItemStuff.cardboardbox)) {
            cir.setReturnValue(Component.literal("Someone"));
        }
    }
}