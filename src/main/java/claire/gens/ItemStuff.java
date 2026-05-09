package claire.gens;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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

    public static final Item strength = register(
            "strengthfrag",
            properties -> new FragmentItem(properties,new MobEffectInstance(MobEffects.STRENGTH,60*20,1),true),
            new Item.Properties()
    );
    public static final Item swiftness = register(
            "swiftnessfrag",
            properties -> new FragmentItem(properties,new MobEffectInstance(MobEffects.SPEED,60*20,1),true),
            new Item.Properties()
    );
    public static final Item haste = register(
            "hastefrag",
            properties -> new FragmentItem(properties,new MobEffectInstance(MobEffects.HASTE,60*20,1),false),
            new Item.Properties()
    );
    public static final Item jumpboos = register(
            "jumpfrag",
            properties -> new FragmentItem(properties,new MobEffectInstance(MobEffects.JUMP_BOOST,60*20,1),true),
            new Item.Properties()
    );
    public static final Item resist = register(
            "resistancefrag",
            properties -> new FragmentItem(properties,new MobEffectInstance(MobEffects.RESISTANCE,60*20,1),true),
            new Item.Properties()
    );
    public static final Item absorption = register(
            "extraheartfrag",
            properties -> new FragmentItem(properties,new MobEffectInstance(MobEffects.ABSORPTION,120*20,1),true),
            new Item.Properties()
    );
    public static final Item fireres = register(
            "fireresfrag",
            properties -> new FragmentItem(properties,new MobEffectInstance(MobEffects.FIRE_RESISTANCE,5*20,1),false),
            new Item.Properties()
    );
    public static final Item nightvision = register(
            "nightvisionfrag",
            properties -> new FragmentItem(properties,new MobEffectInstance(MobEffects.NIGHT_VISION,10*20,1),false),
            new Item.Properties()
    );
    public static final Item waterbreath = register(
            "waterbreathefrag",
            properties -> new FragmentItem(properties,new MobEffectInstance(MobEffects.WATER_BREATHING,5*20,1),false),
            new Item.Properties()
    );
    public static final Item invis = register(
            "invisfrag",
            properties -> new FragmentItem(properties,new MobEffectInstance(MobEffects.INVISIBILITY,60*20,1),true),
            new Item.Properties()
    );
    public static final Item lifesteal = register(
            "lifesteal",
            properties -> new FragmentItem(properties,FragmentType.Lifesteal,null),
            new Item.Properties()
    );

    public static void initialize() {}
}

