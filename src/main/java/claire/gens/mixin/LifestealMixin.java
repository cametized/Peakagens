package claire.gens.mixin;

import claire.gens.ItemStuff;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
//@Environment(EnvType.SERVER)
public class LifestealMixin {
    @WrapMethod(method = "hurtServer")
    public boolean init(ServerLevel level, DamageSource source, float damage, Operation<Boolean> original) {
        if (source.getEntity() instanceof LivingEntity livingEntity && !source.isDirect() && source.is(DamageTypeTags.IS_PLAYER_ATTACK)) {
            if (livingEntity.isHolding(ItemStuff.lifesteal)) {
                if (livingEntity.hasEffect(MobEffects.ABSORPTION)) {
                    MobEffectInstance wsg = livingEntity.getEffect(MobEffects.ABSORPTION);
                    assert wsg != null;
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION,wsg.getDuration()+900,wsg.getAmplifier()+1));
                } else {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION,90*20,0));
                }
            }
        }
        return original.call(level, source, damage);
    }
}
