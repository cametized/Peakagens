package claire.gens;

import net.fabricmc.fabric.impl.item.EnchantmentUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
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

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int ticksRemaining) {
        ItemEnchantments itemEnchantments = itemStack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        if (EnchantmentHelper.hasTag(itemStack, TagKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath("peakagens","winvis")))) {
            if (!livingEntity.hasEffect(MobEffects.INVISIBILITY)) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY,20*5));
            } else {
                MobEffectInstance mobEffectInstance = livingEntity.getEffect(MobEffects.INVISIBILITY);
                if (!(mobEffectInstance.getDuration() > 20*5 || mobEffectInstance.getAmplifier() > 0)) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY,20*5));
                }
            }
        }
    }

    @Override
    public boolean releaseUsing(final ItemStack itemStack, final Level level, final LivingEntity entity, final int remainingTime) {
        return true;
    }
}
