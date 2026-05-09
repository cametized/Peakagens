package claire.gens;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ItemStuff {
    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        ResourceKey<@NotNull Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, name));
        T item = itemFactory.apply(settings.setId(itemKey));

        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        return item;
    }
    public static final Item RadicalRadio = register("radical", Item::new, new Item.Properties());

    public static final Item strength = register("strengthfrag", Item::new, new Item.Properties());
    public static final Item swiftness = register("swiftnessfrag", Item::new, new Item.Properties());
    public static final Item haste = register("hastefrag", Item::new, new Item.Properties());
    public static final Item jumpboos = register("jumpfrag", Item::new, new Item.Properties());
    public static final Item resist = register("resistancefrag", Item::new, new Item.Properties());
    public static final Item absorption = register("extraheartfrag", Item::new, new Item.Properties());
    public static final Item fireres = register("fireresfrag", Item::new, new Item.Properties());
    public static final Item nightvision = register("nightvisionfrag", Item::new, new Item.Properties());
    public static final Item waterbreath = register("waterbreathefrag", Item::new, new Item.Properties());
    public static final Item invis = register("invisfrag", Item::new, new Item.Properties());

    public static void initialize() {}
}

