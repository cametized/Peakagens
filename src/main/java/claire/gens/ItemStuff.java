package claire.gens;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ItemStuff {
    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        ResourceKey<@NotNull Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, name));
        T item = itemFactory.apply(settings.setId(itemKey));

        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        return item;
    }

    public static final Item RadicalRadio = register(
            "radical",
            Item::new,
            new Item.Properties()
    );

    public static final Item ricebowl = register(
            "rice_bowl",
            Item::new,
            new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationModifier(0.3f)
                    .build())
    );

    public static final Item friedegg = register(
            "fried_egg",
            Item::new,
            new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationModifier(0.5f)
                    .build())
    );

    public static final Item bacon = register(
            "bacon",
            Item::new,
            new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationModifier(0.2f)
                    .build())
    );

    public static final Item strength = register(
            "strength",
            properties -> new FragmentItem(properties,new MobEffectInstance(MobEffects.STRENGTH,60*20,0),true),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
    );
    public static final Item swiftness = register(
            "swiftness",
            properties -> new FragmentItem(properties,new MobEffectInstance(MobEffects.SPEED,60*20,0),true),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
    );
    public static final Item haste = register(
            "haste",
            properties -> new FragmentItem(properties,new MobEffectInstance(MobEffects.HASTE,5*20,0),false),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
    );
    public static final Item jumpboos = register(
            "jump",
            properties -> new FragmentItem(properties,new MobEffectInstance(MobEffects.JUMP_BOOST,60*20,0),true),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
    );
    public static final Item resist = register(
            "resistance",
            properties -> new FragmentItem(properties,new MobEffectInstance(MobEffects.RESISTANCE,60*20,0),true),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
    );
    public static final Item absorption = register(
            "extraheart",
            properties -> new FragmentItem(properties,new MobEffectInstance(MobEffects.ABSORPTION,120*20,0),true),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
    );
    public static final Item fireres = register(
            "fireres",
            properties -> new FragmentItem(properties,new MobEffectInstance(MobEffects.FIRE_RESISTANCE,5*20,0),false),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
    );
    public static final Item nightvision = register(
            "nightvision",
            properties -> new FragmentItem(properties,new MobEffectInstance(MobEffects.NIGHT_VISION,10*20,0),false),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
    );
    public static final Item waterbreath = register(
            "waterbreathe",
            properties -> new FragmentItem(properties,new MobEffectInstance(MobEffects.WATER_BREATHING,5*20,0),false),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
    );
    public static final Item invis = register(
            "invis",
            properties -> new FragmentItem(properties,new MobEffectInstance(MobEffects.INVISIBILITY,60*20,0),true),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
    );
    public static final Item lifesteal = register(
            "lifesteal",
            properties -> new FragmentItem(properties,FragmentType.Lifesteal,null),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
    );

    public static final ResourceKey<@NotNull CreativeModeTab> CUSTOM_CREATIVE_TAB_KEY = ResourceKey.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "creative_tab")
    );
    public static final CreativeModeTab CUSTOM_CREATIVE_TAB = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(ItemStuff.friedegg))
            .title(Component.translatable("creativeTab.peakagens"))
            .displayItems((params, output) -> {
                output.accept(ItemStuff.RadicalRadio);

                output.accept(ItemStuff.ricebowl);
                output.accept(ItemStuff.friedegg);
                output.accept(ItemStuff.bacon);

                output.accept(ItemStuff.strength);
                output.accept(ItemStuff.haste);
                output.accept(ItemStuff.swiftness);
                output.accept(ItemStuff.jumpboos);
                output.accept(ItemStuff.fireres);
                output.accept(ItemStuff.resist);
                output.accept(ItemStuff.absorption);
                output.accept(ItemStuff.nightvision);
                output.accept(ItemStuff.waterbreath);
                output.accept(ItemStuff.invis);
                output.accept(ItemStuff.lifesteal);
            })
            .build();

    public static void initialize() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CUSTOM_CREATIVE_TAB_KEY, CUSTOM_CREATIVE_TAB);
    }
}


