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

    protected static void initialize() {
        //Peakagens.LOGGER.info("Registering {} components", Peakagens.MOD_ID);
        // Technically this method can stay empty, but some developers like to notify
        // the console, that certain parts of the mod have been successfully initialized
    }
}
