package claire.gens.blahaj;

import com.mojang.serialization.Codec;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

public class OwnerComponent implements TooltipProvider {
    public static final Codec<OwnerComponent> CODEC = ComponentSerialization.CODEC.xmap(OwnerComponent::new, OwnerComponent::getOwnerName);

    public static final StreamCodec<RegistryFriendlyByteBuf, OwnerComponent> PACKET_CODEC = ComponentSerialization.STREAM_CODEC.map(OwnerComponent::new, OwnerComponent::getOwnerName);

    final Component ownerName;

    public OwnerComponent(Component ownerName) {
        this.ownerName = ownerName;
    }

    private Component getOwnerName() {
        return this.ownerName;
    }

    public void addToTooltip(Item.TooltipContext context, Consumer<Component> textConsumer, TooltipFlag type, DataComponentGetter components) {
        OwnerComponent owner = (OwnerComponent)components.get(BlahajDataComponentTypes.OWNER);
        if (owner != null) {
            Component customName = (Component)components.get(DataComponents.CUSTOM_NAME);
            if (customName == null) {
                textConsumer.accept(Component.translatable("tooltip.blahaj.owner.craft", new Object[] { owner.getOwnerName() }).withStyle(ChatFormatting.GRAY));
            } else {
                textConsumer.accept(Component.translatable("tooltip.blahaj.owner.rename", new Object[] { customName, owner.getOwnerName() }).withStyle(ChatFormatting.GRAY));
            }
        }
    }
}
