package claire.gens.mixin;

import claire.gens.ItemStuff;
import claire.gens.effect.EffectStuff;
import claire.gens.sounds.SoundClass;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(Entity.class)
public class EntityMixin {
    @Shadow
    protected UUID uuid;

    @WrapMethod(method = "thunderHit")
    public void init(ServerLevel level, LightningBolt lightningBolt, Operation<Void> original) {
        Entity myself = level.getEntity(this.uuid);
        if (myself instanceof Player player) {
            if (player.isHolding(ItemStuff.storming)) {
                player.addEffect(new MobEffectInstance(EffectStuff.Electrified,180*20));
            }
            if (player.hasEffect(EffectStuff.Electrified)) {
                level.playSound(null,player.blockPosition(), SoundClass.electricity, SoundSource.PLAYERS);
                return;
            }
        }
        original.call(level,lightningBolt);
    }
}
