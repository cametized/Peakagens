package claire.gens.mixin;

import claire.gens.effect.EffectStuff;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class Calmness {
    @Inject(method = "completeUsingItem", at = @At("HEAD"))
    private void applyFoodEffect(CallbackInfo ci) {
        // FUCK YOU FUCK YOU FUCK YOU FUCK YOU FUCK YOU FUCK YOU FUCK YOU FUCK YOU FUCK YOU FUCK YOU FUCK YOU
        LivingEntity entity = (LivingEntity) (Object) this;
        Level level = entity.level();
        ItemStack stack = entity.getItemInHand(entity.getUsedItemHand());

        if (!level.isClientSide() && entity instanceof Player player) {
            if (stack.has(DataComponents.FOOD)) {
                player.addEffect(new MobEffectInstance(
                        EffectStuff.CALMNESS,
                        2400, // 2 minutes
                        0
                ));
            }
        }
    }
}