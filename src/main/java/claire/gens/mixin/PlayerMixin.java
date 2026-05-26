package claire.gens.mixin;

import claire.gens.ItemStuff;
import claire.gens.ModParticles;
import claire.gens.Peakagens;
import claire.gens.StormingFragment;
import claire.gens.effect.EffectStuff;
import claire.gens.sounds.SoundClass;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.sun.jna.platform.win32.WinBase;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.NameAndId;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Shadow
    protected abstract DamageSource lambda$createAttackSource$0();

    @Shadow
    public abstract NameAndId nameAndId();

    @Shadow
    protected abstract boolean cannotAttack(Entity entity);

    @Shadow
    public abstract float getAttackStrengthScale(float a);

    @Shadow
    protected abstract boolean canCriticalAttack(Entity entity);

    @Shadow
    protected abstract boolean isSweepAttack(boolean fullStrengthAttack, boolean criticalAttack, boolean knockbackAttack);

    @Shadow
    public int experienceLevel;

    @Shadow
    public abstract ItemStack getWeaponItem();

    @Inject(method = "getDisplayName", at = @At("RETURN"), cancellable = true)
    private void thatsWhatThePointOfthemaskIs(CallbackInfoReturnable<Component> cir) {
        Player player = (Player) (Object) this; // The answer is i have no idea
        ItemStack itemStack = player.getItemBySlot(EquipmentSlot.HEAD);
        if (itemStack.is(ItemStuff.cardboardbox) || itemStack.is(ItemStuff.hidebox)) {
            if (itemStack.has(DataComponents.CUSTOM_NAME)) {
                cir.setReturnValue(itemStack.get(DataComponents.CUSTOM_NAME));
            } else {
                cir.setReturnValue(Component.literal("§kHerobrine")); // guys your name is obfuscated cause of them and no one will know #Lore #Awesome
            }
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

    @Inject(at = @At("HEAD"), method = "attack")
    public void woop(Entity entity, CallbackInfo ci) {
        Player fuck = (Player) this.lambda$createAttackSource$0().getEntity();
        if (!this.cannotAttack(entity) && fuck.hasEffect(EffectStuff.Electrified) && StormingFragment.isConductive(this.getWeaponItem()) >= 1) { //&& fuck.getItemInHand(InteractionHand.MAIN_HAND).is(TagKey.create(Registries.ITEM,Identifier.fromNamespaceAndPath(Peakagens.MOD_ID,"conductive")))
            if (getAttackStrengthScale(0.5f) > 0.9f) {
                boolean crit = this.canCriticalAttack(entity);
                boolean knockback = fuck.isSprinting();
                boolean sweep = this.isSweepAttack(true,crit,knockback);

                if (sweep) {
                    entity.level().playSound(null,entity.blockPosition(),SoundClass.smallElectricity,SoundSource.PLAYERS);
                    sparkSwing(entity,fuck);
                } else if (crit || knockback) {
                    entity.level().playSound(null,entity.blockPosition(),SoundClass.zap,SoundSource.PLAYERS);
                    if (entity.level() instanceof ServerLevel serverLevel) {
                        Vec3 hey = entity.position();
                        AABB sub = entity.getBoundingBox();
                        serverLevel.sendParticles(ModParticles.spark,hey.x,hey.y,hey.z, (int) (32*entity.getBoundingBox().getSize()),sub.getXsize()*0.9,sub.getYsize()*0.9,sub.getZsize()*0.9,1.0f);
                    }
                }

            }
        }
    }

    @Inject(at = @At("TAIL"), method = "hurtServer")
    public void init(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        if (source.getEntity() instanceof LivingEntity livingEntity && source.is(DamageTypeTags.IS_PLAYER_ATTACK)) {

            if (livingEntity.isHolding(ItemStuff.lifesteal)) {
                if (livingEntity.hasEffect(MobEffects.ABSORPTION)) {
                    MobEffectInstance wsg = livingEntity.getEffect(MobEffects.ABSORPTION);
                    assert wsg != null;
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, wsg.getDuration() + 900, wsg.getAmplifier() + 1));
                } else {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 90 * 20, 0));
                }
            }
        }
        //Peakagens.LOGGER.info("what");
    }

    @Unique
    private static int maxSparkParticles = 12;
    @Unique
    public int sparkParticles = 0;
    @Unique
    public boolean sparkSwinging = false;
    @Unique
    public Vec3 mineentity = Vec3.ZERO;
    @Unique
    public Vec3 minefuck = Vec3.ZERO;
    @Unique
    public Player minefuck1 = null;
    @Unique
    public float rotation = 0;

    @Unique
    public void sparkSwing(Entity entity, Player fuck) {
        if (sparkParticles < maxSparkParticles) {
            sparkParticles = maxSparkParticles;
            minefuck = fuck.position();
            minefuck1 = fuck;
            mineentity = entity.position();
            sparkSwinging = true;
            rotation = (float) (minefuck1.getRotationVector().y / (180 / Math.PI));
            //Peakagens.LOGGER.info(String.valueOf(rotation));
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void woah(CallbackInfo ci) {
        if (sparkSwinging && mineentity != null && minefuck != null && sparkParticles > 0) {
            if (minefuck1.level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < Math.ceil((double) sparkParticles*9/20); i++) {
                    if (sparkSwinging) {
                        Vec3 mayn = mineentity.subtract(minefuck);
                        double sup = Math.clamp(mayn.length() / 2, 0, 2);
                        Vec3 hey = minefuck.add(0, minefuck1.getBoundingBox().getYsize() / 2, 0).add(mayn.normalize().multiply(sup, sup, sup));
                        Vec3 soap = new Vec3(((double) sparkParticles / maxSparkParticles) * -4 + 2, 0, 0);
                        Vec3 what = hey.yRot((float) (rotation)).add(soap.x, 0, soap.length()-soap.lengthSqr()).yRot((float) (-rotation));
                        //Peakagens.LOGGER.info(String.valueOf(what));

                        serverLevel.sendParticles(ModParticles.spark, what.x, what.y, what.z, 2, 0.1, 0.1, 0.1, 0.05f);
                        sparkParticles--;
                        if (sparkParticles <= 0) {
                            sparkSwinging = false;
                        }
                    }
                }
            }
        }
    }
}