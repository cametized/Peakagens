package claire.gens.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Consumer;

// i added this back, dont kill it this time :/
@Mixin(PotionContents.class)
public class GemTooltipMixin {
    @WrapMethod(method = "addToTooltip")
    private void init(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter components, Operation<Void> original) {
        // This code is injected into the start of MinecraftServer.loadLevel()V
        Identifier gay = components.get(DataComponents.ITEM_MODEL);
        if (gay.equals(Identifier.fromNamespaceAndPath("peakagens","fragment1")) || gay.equals(Identifier.fromNamespaceAndPath("peakagens","fragment2")) || gay.equals(Identifier.fromNamespaceAndPath("peakagens","fragment3")) || gay.equals(Identifier.fromNamespaceAndPath("peakagens","fragment4"))) {
            return;
        }
        original.call(context,consumer,flag,components);
    }
}
