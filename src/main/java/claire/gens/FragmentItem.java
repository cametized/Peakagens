package claire.gens;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class FragmentItem extends BlankFragmentItem {
    //MobEffectInstance mobEffectInstance;

    public FragmentItem(Properties properties, FragmentType type1) {
        super(properties, type1);
        //mobEffectInstance = mobEffectInstance1;
    }

    public FragmentItem(Properties properties, boolean bool) {
        super(properties, bool ? FragmentType.PotionUse : FragmentType.PotionPassive);
        //mobEffectInstance = mobEffectInstance1;
    }

    int tickCount = 0;

    @Override
    public void inventoryTick(final ItemStack itemStack, final ServerLevel level, final Entity owner, final @Nullable EquipmentSlot slot) {
        Integer gey = itemStack.getOrDefault(ModComponents.FragmentLevel, 0);
        Identifier identifier = Identifier.tryParse("peakagens:fragment" + String.valueOf(gey + 1));

        if (identifier != itemStack.get(DataComponents.ITEM_MODEL)) {
            //Peakagens.LOGGER.info(itemStack.getItem().toString());
            itemStack.set(DataComponents.ITEM_MODEL, identifier);
        }

        if (Objects.requireNonNull(owner.asLivingEntity()).isHolding(itemStack.getItem())) {
            if (type.equals(FragmentType.PotionPassive)) { // its its own separate if statement because we dont know if anything else will be needed in the future
                tickCount++;
                if (tickCount > 19) {
                    tickCount = 0;
                    PotionContents gay = itemStack.getOrDefault(DataComponents.POTION_CONTENTS,PotionContents.EMPTY);
                    Iterator<MobEffectInstance> sup = gay.customEffects().iterator();
                    while (sup.hasNext()) {
                        MobEffectInstance lesbian = sup.next();
                        MobEffectInstance hey = new MobEffectInstance(lesbian.getEffect(),lesbian.getDuration(),gey);
                        Objects.requireNonNull(owner.asLivingEntity()).addEffect(hey.withScaledDuration(1.0f));
                    }
                }
            }
        }
    }

    @Override
    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {
        if (type.equals(FragmentType.PotionUse) || type.equals(FragmentType.PotionUseAmplifier)) {
            ItemStack itemStack = player.getItemInHand(hand);
            PotionContents gay = itemStack.getOrDefault(DataComponents.POTION_CONTENTS,PotionContents.EMPTY);
            Iterator<MobEffectInstance> sup = gay.getAllEffects().iterator();
            float cooldown = 1f;
            while (sup.hasNext()) {
                MobEffectInstance lesbian = sup.next();
                cooldown = cooldown+ (float) lesbian.getDuration()*3;
                if (type.equals(FragmentType.PotionUse)) {
                    player.addEffect(lesbian.withScaledDuration(1.0f*(itemStack.getOrDefault(ModComponents.FragmentLevel, 0)+1)));
                } else if (type.equals(FragmentType.PotionUseAmplifier)) {
                    MobEffectInstance gesbian = new MobEffectInstance(lesbian.getEffect(),lesbian.getDuration(),itemStack.getOrDefault(ModComponents.FragmentLevel, 0));
                    player.addEffect(gesbian);
                }
            }
            player.getCooldowns().addCooldown(this.getDefaultInstance(),Math.round(cooldown));
            return InteractionResult.SUCCESS;
        }
        return super.use(level,player,hand);
    }

    @Override
    public Component getName(ItemStack itemStack) {
        return Component.translatable("item.peakagens.frag_"+itemStack.getOrDefault(ModComponents.FragmentLevel, 0)).append(super.getName(itemStack));
    }

    public static PotionContents createPotionOf(MobEffectInstance mobEffectInstance1) {
        PotionContents gaming = new PotionContents(Optional.empty(),Optional.empty(), List.of(mobEffectInstance1),Optional.empty());
        return gaming;
    }

}
