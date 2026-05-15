package claire.gens;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.alchemy.PotionContents;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;
import claire.gens.CloakMaterial;

public class ItemStuff {
    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        ResourceKey<@NotNull Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, name));
        T item = itemFactory.apply(settings.setId(itemKey));

        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        return item;
    }

    public static final ToolMaterial angels_material = new ToolMaterial(
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            1024,
            5.0F,
            2F,
            22,
            ToolMaterial.GOLD.repairItems()
    );

    public static final ToolMaterial cloakermat = new ToolMaterial(
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            455,
            5.0F,
            1.5F,
            22,
            CloakMaterial.REPAIRS_CLOAKER
    );

    public static final Item RadicalRadio = register(
            "radical",
            Item::new,
            new Item.Properties()
    );

    public static final Item wilted_alloy = register(
            "wiltedalloy",
            Item::new,
            new Item.Properties()
    );

    public static final Item angelsword = register(
            "angels_longsword",
            Item::new,
            new Item.Properties().sword(angels_material,6.5f,-2f)
    );

    public static final Item cloaker_mask = register(
            "cloaker_hood",
            Item::new,
            new Item.Properties().humanoidArmor(CloakMaterial.INSTANCE, ArmorType.HELMET)
                    .durability(ArmorType.HELMET.getDurability(CloakMaterial.BASE_DURABILITY))
    );
    public static final Item cloaker_robe = register(
            "cloaker_robe",
            Item::new,
            new Item.Properties().humanoidArmor(CloakMaterial.INSTANCE, ArmorType.CHESTPLATE)
                    .durability(ArmorType.HELMET.getDurability(CloakMaterial.BASE_DURABILITY))
    );
    public static final Item cloaker_boots = register(
            "cloaker_boots",
            Item::new,
            new Item.Properties().humanoidArmor(CloakMaterial.INSTANCE, ArmorType.BOOTS)
                    .durability(ArmorType.HELMET.getDurability(CloakMaterial.BASE_DURABILITY))
    );

    public static final Item spycicle = register(
            "spycicle",
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
            properties -> new FragmentItem(properties,true),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
                    .component(DataComponents.POTION_CONTENTS,FragmentItem.createPotionOf(new MobEffectInstance(MobEffects.STRENGTH,60*20,0)))
    );
    public static final Item swiftness = register(
            "swiftness",
            properties -> new FragmentItem(properties,true),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
                    .component(DataComponents.POTION_CONTENTS,FragmentItem.createPotionOf(new MobEffectInstance(MobEffects.SPEED,60*20,0)))
    );
    public static final Item haste = register(
            "haste",
            properties -> new FragmentItem(properties,false),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
                    .component(DataComponents.POTION_CONTENTS,FragmentItem.createPotionOf(new MobEffectInstance(MobEffects.HASTE,5*20,0)))
    );
    public static final Item jumpboos = register(
            "jump",
            properties -> new FragmentItem(properties,true),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
                    .component(DataComponents.POTION_CONTENTS,FragmentItem.createPotionOf(new MobEffectInstance(MobEffects.JUMP_BOOST,60*20,0)))
    );
    public static final Item resist = register(
            "resistance",
            properties -> new FragmentItem(properties,true),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
                    .component(DataComponents.POTION_CONTENTS,FragmentItem.createPotionOf(new MobEffectInstance(MobEffects.RESISTANCE,60*20,0)))
    );
    public static final Item absorption = register(
            "extraheart",
            properties -> new FragmentItem(properties,true),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
                    .component(DataComponents.POTION_CONTENTS,FragmentItem.createPotionOf(new MobEffectInstance(MobEffects.ABSORPTION,120*20,0)))
    );
    public static final Item fireres = register(
            "fireres",
            properties -> new FragmentItem(properties,false),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
                    .component(DataComponents.POTION_CONTENTS,FragmentItem.createPotionOf(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,5*20,0)))
    );
    public static final Item nightvision = register(
            "nightvision",
            properties -> new FragmentItem(properties,false),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
                    .component(DataComponents.POTION_CONTENTS,FragmentItem.createPotionOf(new MobEffectInstance(MobEffects.NIGHT_VISION,10*20,0)))
    );
    public static final Item waterbreath = register(
            "waterbreathe",
            properties -> new FragmentItem(properties,false),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
                    .component(DataComponents.POTION_CONTENTS,FragmentItem.createPotionOf(new MobEffectInstance(MobEffects.WATER_BREATHING,5*20,0)))
    );
    public static final Item invis = register(
            "invis",
            properties -> new FragmentItem(properties,true),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
                    .component(DataComponents.POTION_CONTENTS,FragmentItem.createPotionOf(new MobEffectInstance(MobEffects.INVISIBILITY,60*20,0)))
    );
    public static final Item alchemy = register(
            "alchemy_fragment",
            AlchemyFragmentItem::new,
            new Item.Properties().stacksTo(1).component(DataComponents.POTION_CONTENTS,PotionContents.EMPTY)
    );
    public static final Item lifesteal = register(
            "lifesteal",
            properties -> new FragmentItem(properties,FragmentType.Lifesteal),
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
                output.accept(ItemStuff.cloaker_mask);
                output.accept(ItemStuff.angelsword);
                output.accept(ItemStuff.wilted_alloy);
                output.accept(ItemStuff.spycicle);

                output.accept(ItemStuff.ricebowl);
                output.accept(ItemStuff.friedegg);
                output.accept(ItemStuff.bacon);

                List<Item> hey = List.of(strength,haste,swiftness,jumpboos,fireres,resist,absorption,nightvision,waterbreath,invis);
                for (int i = 0; i < hey.size()-1; i++) {
                    ItemStack yo = new ItemStack(hey.get(i));
                    ItemStack so = yo.copy();
                    ItemStack bro = yo.copy();
                    ItemStack ho = yo.copy();

                    yo.set(DataComponents.ITEM_MODEL,Identifier.parse("peakagens:fragment1"));
                    so.set(DataComponents.ITEM_MODEL,Identifier.parse("peakagens:fragment2"));
                    bro.set(DataComponents.ITEM_MODEL,Identifier.parse("peakagens:fragment3"));
                    ho.set(DataComponents.ITEM_MODEL,Identifier.parse("peakagens:fragment4"));

                    so.set(ModComponents.FragmentLevel,1);
                    bro.set(ModComponents.FragmentLevel,2);
                    ho.set(ModComponents.FragmentLevel,3);

                    output.accept(yo);
                    output.accept(so);
                    output.accept(bro);
                    output.accept(ho);
                }

                output.accept(alchemy);
            })
            .build();

    public static void initialize() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CUSTOM_CREATIVE_TAB_KEY, CUSTOM_CREATIVE_TAB);
    }
}


