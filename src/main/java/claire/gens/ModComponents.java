package claire.gens;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

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

    public static final DataComponentType<String> AppliedGemName = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "applied_gem_name"),
            DataComponentType.<String>builder().persistent(Codec.STRING).build()
    );

    protected static void initialize() {
        //Peakagens.LOGGER.info("Registering {} components", Peakagens.MOD_ID);
        // Technically this method can stay empty, but some developers like to notify
        // the console, that certain parts of the mod have been successfully initialized
    }
}
