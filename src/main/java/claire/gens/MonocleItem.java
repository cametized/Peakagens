package claire.gens;

import net.fabricmc.fabric.impl.item.EnchantmentUtil;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Unique;

import java.util.Objects;

public class MonocleItem extends SpyglassItem {
    public static final float ZOOM_FOV_MODIFIER = 0.75F;

    public MonocleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {
        player.awardStat(Stats.ITEM_USED.get(this));
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(final ItemStack itemStack, final Level level, final LivingEntity entity) {
        return itemStack;
    }

    public int initialSpotted = 24000;
    public LivingEntity target = null;
    public float progress = 0f;
    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int ticksRemaining) {
        ItemEnchantments itemEnchantments = itemStack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        if (EnchantmentHelper.hasTag(itemStack, TagKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath("peakagens","winvis")))) {
            if (!livingEntity.hasEffect(MobEffects.INVISIBILITY)) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY,20*5));
            } else {
                MobEffectInstance mobEffectInstance = livingEntity.getEffect(MobEffects.INVISIBILITY);
                assert mobEffectInstance != null;
                if (!(mobEffectInstance.getDuration() > 20 * 5 || mobEffectInstance.getAmplifier() > 0)) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY,20*5));
                }
            }
            if (((double) ticksRemaining/this.getUseDuration(itemStack,livingEntity))*20 == Math.round(((double) ticksRemaining/this.getUseDuration(itemStack,livingEntity))*20)) {
                itemStack.hurtAndBreak(1,livingEntity,livingEntity.getUsedItemHand());
            }
        } else if (EnchantmentHelper.hasTag(itemStack, TagKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath("peakagens","wshine"))) && livingEntity instanceof Player player && !level.isClientSide()) {
            LivingEntity hey = Peakagens.findWhoImLookingAt(level, player, 14);
            if (hey != null) {
                hey.igniteForTicks(80); // later imma try to make this do more damage prolly
                if (((double) ticksRemaining/this.getUseDuration(itemStack,livingEntity))*20 == Math.round(((double) ticksRemaining/this.getUseDuration(itemStack,livingEntity))*20)) {
                    itemStack.hurtAndBreak(1,player,player.getUsedItemHand());
                }
                if (((double) ticksRemaining/this.getUseDuration(itemStack,livingEntity))*150 == Math.round(((double) ticksRemaining/this.getUseDuration(itemStack,livingEntity))*150)) {
                    hey.hurtServer((ServerLevel) level,livingEntity.damageSources().source(ResourceKey.create(Registries.DAMAGE_TYPE,Identifier.fromNamespaceAndPath(Peakagens.MOD_ID,"shiningfire"))),1f);
                }
            }
        } else if (EnchantmentHelper.hasTag(itemStack, TagKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath("peakagens","wblink"))) && livingEntity instanceof Player player) {
            LivingEntity hey = Peakagens.findWhoImLookingAt(level, player, 16);
            Vec3 heynow = (target != null ? target.position().subtract(player.position()).subtract(0,player.getBoundingBox().getYsize()/2,0).multiply(1.0/target.getBoundingBox().getXsize(),1.0/target.getBoundingBox().getYsize(),1.0/target.getBoundingBox().getZsize()).normalize() : Vec3.ZERO);
            double dot = player.getViewVector(1.0f).normalize().dot(heynow);
            if ((target != null) && ((hey == target) || (target.position().subtract(player.position()).length() <= 16f && dot > 0.75f))) {
                float thenumber = player.fallDistance > 4 ? (float) (20f / (player.fallDistance / 2f)) : 20f;
                progress = Math.clamp((initialSpotted-ticksRemaining)/thenumber,0f,1f);
                //Peakagens.LOGGER.info(String.valueOf(progress));
                if (ticksRemaining < initialSpotted-thenumber) {
                    Vec3 auh = target.position().subtract(target.getLookAngle().multiply(3f, 3f, 3f));
                    player.teleportTo(auh.x, auh.y, auh.z);
                    player.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
                    level.playSound(null,auh.x,auh.y,auh.z, SoundEvents.PLAYER_TELEPORT, SoundSource.PLAYERS);
                    itemStack.hurtAndBreak(4,player,player.getUsedItemHand());
                    player.getCooldowns().addCooldown(itemStack,20*20);
                    player.resetFallDistance();
                    player.stopUsingItem();
                }
            } else {
                target = hey;
                initialSpotted = ticksRemaining;
                progress = 0f;
            }
            itemStack.set(ModComponents.GenericFloat,progress);
        }
    }

    @Override
    public boolean releaseUsing(final ItemStack itemStack, final Level level, final LivingEntity entity, final int remainingTime) {
        target = null;
        initialSpotted = 24000;
        progress = 0f;
        itemStack.set(ModComponents.GenericFloat,0f);
        return true;
    }
}
