package claire.gens;

import claire.gens.effect.EffectStuff;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.system.ffm.mapping.Mapping;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class LifestealFragmentItem extends BlankFragmentItem {

    public LifestealFragmentItem(Properties properties) {
        super(properties,FragmentType.Lifesteal);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        LivingEntity player1;
        if (!player.isCrouching()) {
            player1 = Peakagens.findWhoImLookingAt(level, player);
        } else {
            player1 = player;
        }
        if (player1 != null) {
            player.getCooldowns().addCooldown(player.getItemInHand(hand),20*30);
            player1.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST,-1,0));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
