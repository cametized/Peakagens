package claire.gens.client.mixin;

import claire.gens.ItemStuff;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractClientPlayer.class)
public class FovMixin extends Player {
    public FovMixin(Level level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    @WrapMethod(method = "getFieldOfViewModifier")
    public float ohyeah(boolean firstPerson, float effectScale, Operation<Float> original) {
        if (this.isScoping() && this.getUseItem().is(ItemStuff.spycicle) && firstPerson) {
            return 0.75f;
        }
        return original.call(firstPerson,effectScale);
    }

    @Unique
    public @Nullable GameType gameMode() {
        return null;
    }
}
