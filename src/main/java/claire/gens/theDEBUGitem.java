package claire.gens;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpyglassItem;
import net.minecraft.world.level.Level;

public class theDEBUGitem extends SpyglassItem {

    public theDEBUGitem(Properties properties) {
        super(properties);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int ticksRemaining) {
        if (livingEntity instanceof Player player) {
            LivingEntity hey = Peakagens.findWhoImLookingAt(level, player, 16);
            if (hey != null) {
                hey.addEffect(new MobEffectInstance(MobEffects.GLOWING, 2));
            }
        }
    }
}
