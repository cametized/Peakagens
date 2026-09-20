package claire.gens;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public class ModComponents {
    public static final DataComponentType<Integer> FragmentLevel = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "fragment_level"),
            DataComponentType.<Integer>builder().persistent(Codec.INT).build()
    );

    public static final DataComponentType<Float> GenericFloat = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "generic_float"),
            DataComponentType.<Float>builder().persistent(Codec.FLOAT).build()
    );

    public static final DataComponentType<Boolean> hideName = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "hide_name"),
            DataComponentType.<Boolean>builder().persistent(Codec.BOOL).build()
    );

    protected static void initialize() {
        //Peakagens.LOGGER.info("Registering {} components", Peakagens.MOD_ID);
        // Technically this method can stay empty, but some developers like to notify
        // the console, that certain parts of the mod have been successfully initialized
        ItemTooltipCallback.EVENT.register((stack, context, type, tooltip) -> {
            if (stack.getOrDefault(hideName,false)) {
                tooltip.add(1,Component.translatable("item.peakagens.hide_name").withStyle(ChatFormatting.GRAY));
            }
        });
    }
}
