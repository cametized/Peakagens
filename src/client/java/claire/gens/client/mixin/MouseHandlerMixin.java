package claire.gens.client.mixin;

import claire.gens.ItemStuff;
import claire.gens.ModComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @ModifyVariable(method = "turnPlayer", at = @At(value = "STORE"), name = "sensitivityMod")
    public double hey(double sensitivityMod) {
        return (this.minecraft.player != null && (this.minecraft.player.isScoping() && this.minecraft.player.getUseItem().is(ItemStuff.spycicle) && this.minecraft.options.getCameraType().isFirstPerson())) ? sensitivityMod*8.0*0.75*(2.0f-(this.minecraft.player.getUseItem().getOrDefault(ModComponents.GenericFloat,0.0f) > 0f ? (this.minecraft.player.getUseItem().getOrDefault(ModComponents.GenericFloat,0.0f)*2.0f) : 1.0f)) : sensitivityMod;
    }
}
