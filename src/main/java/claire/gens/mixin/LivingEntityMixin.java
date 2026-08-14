package claire.gens.mixin;

import claire.gens.ItemStuff;
import claire.gens.StormingFragment;
import claire.gens.effect.EffectStuff;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.CommonColors;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow
    public abstract @Nullable LivingEntity asLivingEntity();

    @Shadow
    public abstract boolean hasEffect(Holder<MobEffect> effect);

    @Shadow
    public abstract boolean addEffect(MobEffectInstance newEffect);

    @Shadow
    public abstract @Nullable MobEffectInstance getEffect(Holder<MobEffect> effect);

    @Unique public LivingEntity comboAttacker = null;
    @Unique public int comboCounter = 0;

    @WrapMethod(method = "hurtServer")
    public boolean init(ServerLevel level, DamageSource source, float damage, Operation<Boolean> original) {
        LivingEntity fuck = this.asLivingEntity();
        if (source.getEntity() instanceof LivingEntity livingEntity && source.is(DamageTypeTags.IS_PLAYER_ATTACK) && fuck != null) {
            if (livingEntity.hasEffect(EffectStuff.Electrified) && StormingFragment.isConductive(livingEntity.getWeaponItem()) >= 2) { //&& livingEntity.getItemInHand(InteractionHand.MAIN_HAND).is()
                //Peakagens.LOGGER.info("bitch");
                if (fuck != null) {
                    if (comboAttacker == livingEntity) {
                        comboCounter++;
                        if (comboAttacker instanceof ServerPlayer player && comboCounter <= 3) {
                            ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket = new ClientboundSetActionBarTextPacket(comboCounter == 3 ? Component.translatable("title.peakagens.combo_full").withColor(CommonColors.SOFT_YELLOW).withStyle(ChatFormatting.BOLD) : Component.translatable("title.peakagens.combo").append(Component.literal(" "+String.valueOf(Math.clamp(comboCounter,0,3))+"/3")).withColor(CommonColors.SOFT_YELLOW));
                            player.connection.send(clientboundSetActionBarTextPacket);
                        }
                        boolean balls = ((PlayerInvoker) comboAttacker).peakagens$canCriticalAttack(livingEntity);
                        //Peakagens.LOGGER.info(String.valueOf(balls));
                        if (balls) {
                            if (comboCounter > 3 && livingEntity.isHolding(ItemStuff.storming)) {
                                comboCounter = 0;
                                if (comboAttacker instanceof ServerPlayer player) {
                                    ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket = new ClientboundSetActionBarTextPacket(Component.translatable("title.peakagens.combo").append(Component.literal(" 0/3")).withColor(CommonColors.SOFT_YELLOW));
                                    player.connection.send(clientboundSetActionBarTextPacket);
                                }
                                damage = damage * 1.35f;
                                LightningBolt lightningBolt = new LightningBolt(EntityTypes.LIGHTNING_BOLT, level);
                                lightningBolt.setPos(Objects.requireNonNull(fuck).position());
                                lightningBolt.setVisualOnly(true);
                                level.addFreshEntity(lightningBolt);
                            }
                        }
                    } else {
                        comboAttacker = livingEntity;
                        comboCounter = 1;
                    }
                }
            }
            if (livingEntity.isHolding(ItemStuff.lifesteal) && fuck.hasEffect(MobEffects.HEALTH_BOOST)) {
                if (fuck.hasEffect(EffectStuff.Vulnerable)) {
                    MobEffectInstance mobEffectInstance = fuck.getEffect(EffectStuff.Vulnerable);
                    assert mobEffectInstance != null;
                    fuck.addEffect(new MobEffectInstance(EffectStuff.Vulnerable,Math.clamp(mobEffectInstance.getDuration() + Math.round(damage*2),10,30*20),mobEffectInstance.getAmplifier() + Math.round(damage*2/3)));
                } else {
                    fuck.addEffect(new MobEffectInstance(EffectStuff.Vulnerable,Math.clamp(Math.round(damage*2),10,10*20),Math.round(damage*2/3)));
                }
            }
            if (livingEntity.isHolding(ItemStuff.cloth) && livingEntity instanceof Player player) {
                InteractionHand interactionHand = player.getItemInHand(InteractionHand.MAIN_HAND).is(ItemStuff.cloth) ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
                player.setItemInHand(interactionHand,ItemStuff.blood_cloth.getDefaultInstance());
            }
        }
        return original.call(level, source, damage);
    }
}
