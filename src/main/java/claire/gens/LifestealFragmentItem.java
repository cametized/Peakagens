package claire.gens;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.lwjgl.system.ffm.mapping.Mapping;

import java.util.Iterator;
import java.util.List;

public class LifestealFragmentItem extends BlankFragmentItem {

    public LifestealFragmentItem(Properties properties) {
        super(properties,FragmentType.Lifesteal);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        Player player1 = null;
        if (!player.isCrouching()) {
            boolean found = false;
            Iterator<Player> plrList = level.getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 10, 10, 10)).iterator();
            while (plrList.hasNext() && !found) {
                Player cool = plrList.next();
                if (cool.isLookingAtMe(player, 0.025, true, false, new double[]{player.getEyeY()}) && cool != player) { // to fix later: add priority for ppl the player is looking at more
                    found = true;
                    player1 = cool;
                }
            }
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
