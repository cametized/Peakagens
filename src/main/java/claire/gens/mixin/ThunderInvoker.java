package claire.gens.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ServerLevel.class)
public interface ThunderInvoker {
    @Invoker("findLightningTargetAround")
    BlockPos peakagens$findLightningTargetAround(final BlockPos blockPos);
}
