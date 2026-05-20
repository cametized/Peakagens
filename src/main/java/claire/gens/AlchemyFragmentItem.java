package claire.gens;

import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class AlchemyFragmentItem extends BlankFragmentItem {
    //public FragmentType type = FragmentType.Alchemy;

    public AlchemyFragmentItem(Properties properties) {
        super(properties,FragmentType.Alchemy);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!player.getItemInHand(hand).getOrDefault(DataComponents.POTION_CONTENTS,PotionContents.EMPTY).hasEffects()) {
            boolean soolean = false;
            if (!player.getActiveEffects().isEmpty()) {
                soolean = true;
                List<MobEffectInstance> balls = new ArrayList<>(player.getActiveEffects());
                PotionContents potionContents = new PotionContents(Optional.empty(),Optional.empty(),balls,Optional.empty());
                player.removeAllEffects();
                player.getItemInHand(hand).set(DataComponents.POTION_CONTENTS,potionContents);
                player.getItemInHand(hand).set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE,true);
            }
            player.getCooldowns().addCooldown(player.getItemInHand(hand),10);
            return soolean ? InteractionResult.SUCCESS : InteractionResult.PASS;
        } else {
            int cooldown = 1;
            PotionContents potionContents = player.getItemInHand(hand).get(DataComponents.POTION_CONTENTS);
            assert potionContents != null;
            Iterator<MobEffectInstance> consequences = potionContents.getAllEffects().iterator();
            while (consequences.hasNext()) {
                MobEffectInstance consequence = consequences.next();

                if (player.getEffect(consequence.getEffect()) != null) {
                    MobEffectInstance already = player.getEffect(consequence.getEffect());
                    assert already != null;
                    MobEffectInstance combined = combineEffects(consequence,already);
                    player.addEffect(combined);
                } else {
                    player.addEffect(consequence.withScaledDuration(1.0f));
                }
                cooldown = cooldown+(consequence.getDuration() * (consequence.getAmplifier()+2)/2);
            }
            if (player.isCrouching() && player.fallDistance < 0.5 && !player.isFallFlying()) {
                AreaEffectCloud areaEffectCloud = new AreaEffectCloud(level,player.position().x,player.position().y,player.position().z);
                areaEffectCloud.setDuration(20*30);
                areaEffectCloud.setRadius(3.0f);
                areaEffectCloud.setRadiusOnUse(-0.5f);
                areaEffectCloud.setWaitTime(10);
                areaEffectCloud.setRadiusPerTick(-areaEffectCloud.getRadius() / areaEffectCloud.getDuration());
                areaEffectCloud.setPotionContents(potionContents);
                level.addFreshEntity(areaEffectCloud);
            }
            player.getCooldowns().addCooldown(player.getItemInHand(hand),cooldown);
            return InteractionResult.SUCCESS;
        }
    }

    int myownticks = 0;

    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        boolean mybooleaan = true;
        PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS,PotionContents.EMPTY);
        if (potionContents.hasEffects() && owner instanceof LivingEntity livingEntity && livingEntity.isHolding(itemStack.getItem())) {
            if (owner instanceof Player player) {
                mybooleaan = !player.getCooldowns().isOnCooldown(itemStack);
            }

            if (mybooleaan && potionContents.hasEffects()) {
                myownticks++;

                if (myownticks > 19) {
                    myownticks = 0;
                    Iterator<MobEffectInstance> theIterator = potionContents.getAllEffects().iterator();
                    while (theIterator.hasNext()) {
                        boolean coolean = true;
                        MobEffectInstance og = theIterator.next();
                        MobEffectInstance supbitch = new MobEffectInstance(og.getEffect(),20*5,0);

                        if (livingEntity.hasEffect(supbitch.getEffect())) {
                            MobEffectInstance mobEffectInstance = livingEntity.getEffect(supbitch.getEffect());
                            assert mobEffectInstance != null;
                            if (mobEffectInstance.getAmplifier() > supbitch.getAmplifier() || mobEffectInstance.getDuration() > supbitch.getDuration()) {
                                coolean = false;
                            }
                        }

                        if (coolean) {
                            livingEntity.addEffect(supbitch.withScaledDuration(1.0f));
                        }
                    }
                }

            }
        }
    }

    @Override
    public @Nullable ItemStackTemplate getCraftingRemainder(ItemStack stack) {
        if (stack.getOrDefault(DataComponents.POTION_CONTENTS,PotionContents.EMPTY).hasEffects()) {
            return new ItemStackTemplate(ItemStuff.alchemy);
        }
        return super.getCraftingRemainder(stack);
    }

    public MobEffectInstance combineEffects(MobEffectInstance consequence, MobEffectInstance already) {
        int lvl = (consequence.getAmplifier() <= already.getAmplifier() ? already : consequence).getAmplifier();
        int dur = consequence.getDuration() + already.getDuration();
        MobEffectInstance combined = new MobEffectInstance(consequence.getEffect(),dur,lvl);
        return combined;
    }
}
