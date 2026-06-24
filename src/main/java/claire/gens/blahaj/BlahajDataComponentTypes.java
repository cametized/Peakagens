package claire.gens.blahaj;

import java.util.function.UnaryOperator;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public class BlahajDataComponentTypes {
    public static final DataComponentType<OwnerComponent> OWNER;

    static {
        OWNER = register("owner", builder -> builder.persistent(OwnerComponent.CODEC).networkSynchronized(OwnerComponent.PACKET_CODEC).cacheEncoding());
    }

    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Identifier.fromNamespaceAndPath("blahaj", id), (builderOperator.apply(DataComponentType.builder())).build());
    }

    public static void register() {}
}
