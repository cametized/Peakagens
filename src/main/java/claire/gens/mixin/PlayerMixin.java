package claire.gens.mixin;

import claire.gens.ItemStuff;
import claire.gens.Peakagens;
import claire.gens.effect.EffectStuff;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.NameAndId;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Shadow
    protected abstract DamageSource lambda$createAttackSource$0();

    @Shadow
    public abstract NameAndId nameAndId();

    @Inject(method = "getDisplayName", at = @At("RETURN"), cancellable = true)
    private void thatsWhatThePointOfthemaskIs(CallbackInfoReturnable<Component> cir) {
        Player player = (Player) (Object) this; // The answer is i have no idea
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ItemStuff.cardboardbox)) {
            cir.setReturnValue(Component.literal("§kHerobrineGamer")); // guys your name is obfuscated cause of them and no one will know #Lore #Awesome
        }
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ItemStuff.hidebox)) {
            cir.setReturnValue(Component.literal("§kHerobrineGamer")); // guys your name is obfuscated cause of them and no one will know #Lore #Awesome
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

    @Unique
    public LivingEntity comboAttacker = null;
    @Unique
    public int comboCounter = 0;

    @Inject(at = @At("HEAD"), method = "hurtServer")
    public void init(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        if (source.getEntity() instanceof LivingEntity livingEntity && source.is(DamageTypeTags.IS_PLAYER_ATTACK)) {
            if (livingEntity.isHolding(ItemStuff.lifesteal)) {
                if (livingEntity.hasEffect(MobEffects.ABSORPTION)) {
                    MobEffectInstance wsg = livingEntity.getEffect(MobEffects.ABSORPTION);
                    assert wsg != null;
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION,wsg.getDuration()+900,wsg.getAmplifier()+1));
                } else {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION,90*20,0));
                }
            } else if (livingEntity.isHolding(ItemStuff.storming) && livingEntity.hasEffect(EffectStuff.Electrified)) {
                if (comboAttacker == livingEntity) {
                    comboCounter++;
                    if (comboCounter >= 3 && ((PlayerInvoker) livingEntity).peakagens$canCriticalAttack(source.getDirectEntity())) {
                        comboCounter = 0;
                        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,level);
                        lightningBolt.setPos(Objects.requireNonNull(level.getEntity(this.nameAndId().id())).position());
                        level.addFreshEntity(lightningBolt);
                    }
                } else {
                    comboAttacker = livingEntity;
                    comboCounter = 1;
                }
            }
        }
        Peakagens.LOGGER.info("what");
    }
}