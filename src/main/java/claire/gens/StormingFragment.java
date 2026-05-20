package claire.gens;

import claire.gens.mixin.ThunderInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.Objects;

public class StormingFragment extends BlankFragmentItem {
    public StormingFragment(Properties properties) {
        super(properties,FragmentType.Storm);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.canHaveWeather() && player.isCrouching()) {
            if (level.isRaining()) {
                Objects.requireNonNull(level.getServer()).setWeatherParameters(0, ServerLevel.RAIN_DELAY.sample(level.getRandom()),false,false);
            } else  {
                Objects.requireNonNull(level.getServer()).setWeatherParameters(0, ServerLevel.RAIN_DURATION.sample(level.getRandom()),true,false);
            }
            return InteractionResult.SUCCESS;
        } else if (level.canHaveWeather()) {
            if (level instanceof ServerLevel serverLevel) {
                LevelChunk chunk = level.getChunk(player.chunkPosition().x()+1-level.getRandom().nextInt(3),player.chunkPosition().z()+1-level.getRandom().nextInt(3));
                BlockPos blockPos = ((ThunderInvoker) serverLevel).peakagens$findLightningTargetAround(((ThunderInvoker) serverLevel).peakagens$findLightningTargetAround(serverLevel.getBlockRandomPos(chunk.getPos().getMinBlockX(),0,chunk.getPos().getMinBlockZ(),15)));

                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,level);
                lightningBolt.setPos(blockPos.getBottomCenter());
                serverLevel.addFreshEntity(lightningBolt);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
