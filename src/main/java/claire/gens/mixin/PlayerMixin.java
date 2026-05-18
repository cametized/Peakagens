package claire.gens.mixin;

import claire.gens.ItemStuff;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Shadow
    protected abstract DamageSource lambda$createAttackSource$0();

    @Inject(method = "getDisplayName", at = @At("RETURN"), cancellable = true)
    private void thatsWhatThePointOfthemaskIs(CallbackInfoReturnable<Component> cir) {
        Player player = (Player) (Object) this; // The answer is i have no idea
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ItemStuff.cardboardbox)) {
            cir.setReturnValue(Component.literal("Someone"));
        }
    }

    // nah, i'd win

    @WrapMethod(method = "isScoping")
    public boolean init(Operation<Boolean> original) {
        if (original.call()) return true;
        Player player = (Player) this.lambda$createAttackSource$0().getEntity(); // weird way of getting the player cuz i lowk dont feel like figuring it out normally lmao
        assert player != null;
        return player.isUsingItem() && player.getUseItem().is(ItemStuff.spycicle);
    }
}