package claire.gens.mixin;

import claire.gens.Peakagens;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.EnderEyeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Objects;

@Mixin(EnderEyeItem.class)
public class EyeLockMixin {
    @WrapMethod(method = "useOn")
    public InteractionResult init(UseOnContext context, Operation<InteractionResult> original) {
        if (Objects.requireNonNull(context.getLevel().getServer()).getGameRules().get(Peakagens.literallyTheEnd)) {
            return original.call(context);
        }
        return InteractionResult.PASS;
    }
}
