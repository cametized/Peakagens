package claire.gens;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class FragmentItem extends Item {
    FragmentType type;
    MobEffectInstance mobEffectInstance;

    public FragmentItem(Properties properties, FragmentType type1, @Nullable MobEffectInstance mobEffectInstance1) {
        super(properties);
        type = type1;
        mobEffectInstance = mobEffectInstance1;
    }

    public FragmentItem(Properties properties, MobEffectInstance mobEffectInstance1, boolean bool) {
        super(properties);
        type = bool ? FragmentType.PotionUse : FragmentType.PotionPassive;
        mobEffectInstance = mobEffectInstance1;
    }

    int tickCount = 0;

    @Override
    public void inventoryTick(final ItemStack itemStack, final ServerLevel level, final Entity owner, final @Nullable EquipmentSlot slot) {
        if (Objects.requireNonNull(owner.asLivingEntity()).isHolding(itemStack.getItem())) {
            if (type.equals(FragmentType.PotionPassive)) { // its its own separate if statement because we dont know if anything else will be needed in the future
                tickCount++;
                if (tickCount > 19) {
                    tickCount = 0;
                    Objects.requireNonNull(owner.asLivingEntity()).addEffect(mobEffectInstance);
                }
            }
        }
    }

    @Override
    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {
        if (type.equals(FragmentType.PotionUse)) {
            player.getCooldowns().addCooldown(this.getDefaultInstance(),mobEffectInstance.getDuration()*3);
            player.addEffect(mobEffectInstance.withScaledDuration(1.0F));
            return InteractionResult.SUCCESS;
        }
        return super.use(level,player,hand);
    }
}
