package claire.gens;

import claire.gens.armor.CardboardBox;
import claire.gens.effect.EffectStuff;
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
import net.minecraft.world.item.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.alchemy.PotionContents;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

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

    public static final Item RadicalRadio = register(
            "radical",
            Item::new,
            new Item.Properties().component(
            DataComponents.LORE,
                    new ItemLore(List.of(
                    Component.literal("wait a minute whys he here").withColor(0x61dfff)
                    ))
                            )
    );

    public static final Item ThisItemDoesNothingAndItsForShow = register(
            "godsgem",
            Item::new,
            new Item.Properties().component(
                    DataComponents.LORE,
                    new ItemLore(List.of(
                            Component.literal("You think this actually does something? Lmao nah, it's just for show. Entitorium is Cam's Lore Character.").withColor(0xa561ff)
                    ))
            )
    );

    public static final Item wilted_alloy = register(
            "wiltedalloy",
            Item::new,
            new Item.Properties()
    );

    public static final Item pitchfork = register(
            "pitchfork", // this is like supposed to be an early game trident but i cannot bother to go thru the trouble of coding this
            Item::new,
            new Item.Properties()
    );

    //public static final Item angelsword = register(
          //  "angels_longsword",
           // Item::new,
           // new Item.Properties().sword(angels_material, 3.5f, -2.5f)
   // ); yeah

    public static final Item cardboardbox = register(
            "cardboardbox",
            Item::new,
            new Item.Properties().humanoidArmor(CardboardBox.INSTANCE, ArmorType.HELMET)
                    .durability(ArmorType.HELMET.getDurability(CardboardBox.BASE_DURABILITY))
    );


    public static final Item spycicle = register(
            "monocle",
            MonocleItem::new,
            new Item.Properties().stacksTo(1)
    );

    public static final Item ricebowl = register(
            "rice_bowl",
            Item::new,
            new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build(),
                    Consumable.builder().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(EffectStuff.CALMNESS,2400))).build()
            )
    );

    public static final Item rice = register(
            "rice",
            Item::new,
            new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(4).saturationModifier(0.15f).build()
            )
    );

    public static final Item friedegg = register(
            "fried_egg",
            Item::new,
            new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(9).saturationModifier(1.5f).build(),
                    Consumable.builder().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(EffectStuff.CALMNESS,2400))).build()
            )
    );

    public static final Item bacon = register(
            "bacon",
            Item::new,
            new Item.Properties().food(
                            new FoodProperties.Builder().nutrition(3).saturationModifier(0.2f).build(),
                            Consumable.builder().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(EffectStuff.CALMNESS,2400))).build()
                    )
    );

    //Gems
    public static final Item strength = register(
            "strength",
            properties -> new FragmentItem(properties,true),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
                    .component(DataComponents.POTION_CONTENTS,FragmentItem.createPotionOf(new MobEffectInstance(MobEffects.STRENGTH,20*20,0)))
    );
    public static final Item swiftness = register(
            "swiftness",
            properties -> new FragmentItem(properties,true),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
                    .component(DataComponents.POTION_CONTENTS,FragmentItem.createPotionOf(new MobEffectInstance(MobEffects.SPEED,30*20,0)))
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
                    .component(DataComponents.POTION_CONTENTS,FragmentItem.createPotionOf(new MobEffectInstance(MobEffects.JUMP_BOOST,15*20,0)))
    );
    public static final Item resist = register(
            "resistance",
            properties -> new FragmentItem(properties,true),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
                    .component(DataComponents.POTION_CONTENTS,FragmentItem.createPotionOf(new MobEffectInstance(MobEffects.RESISTANCE,30*20,0)))
    );
    public static final Item absorption = register(
            "extraheart",
            properties -> new FragmentItem(properties,FragmentType.PotionUseAmplifier),
            new Item.Properties().component(ModComponents.FragmentLevel,0).stacksTo(1)
                    .component(DataComponents.POTION_CONTENTS,FragmentItem.createPotionOf(new MobEffectInstance(MobEffects.ABSORPTION,60*20,0)))
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
                    .component(DataComponents.POTION_CONTENTS,FragmentItem.createPotionOf(new MobEffectInstance(MobEffects.INVISIBILITY,20*20,0)))
    );

    //Fragments
    public static final Item alchemy = register(
            "alchemy_fragment",
            AlchemyFragmentItem::new,
            new Item.Properties().stacksTo(1).component(DataComponents.POTION_CONTENTS,PotionContents.EMPTY)
    );
    public static final Item enchanting = register(
            "enchantment_fragment",
            EnchantingFragment::new,
            new Item.Properties().stacksTo(1)
    );
    public static final Item lifesteal = register(
            "lifesteal_fragment",
            LifestealFragmentItem::new,
            new Item.Properties().stacksTo(1)
    );
    public static final Item storming = register(
            "storm_fragment",
            StormingFragment::new,
            new Item.Properties().stacksTo(1)
    );

    //Creative Tabs
    public static final ResourceKey<@NotNull CreativeModeTab> CUSTOM_CREATIVE_TAB_KEY = ResourceKey.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "creative_tab")
    );
    public static final CreativeModeTab CUSTOM_CREATIVE_TAB = FabricCreativeModeTab.builder() // hi :)
            .icon(() -> new ItemStack(friedegg))
            .title(Component.translatable("creativeTab.peakagens"))
            .displayItems((params, output) -> {
                output.accept(RadicalRadio);
                output.accept(ThisItemDoesNothingAndItsForShow);
                output.accept(alchemy);
                output.accept(enchanting);
                output.accept(lifesteal);
                output.accept(storming);
                output.accept(cardboardbox);
                //output.accept(angelsword);
                output.accept(wilted_alloy);
                output.accept(spycicle);

                output.accept(ricebowl);
                output.accept(rice);
                output.accept(friedegg);
                output.accept(bacon);

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
            })
            .build();

    public static void initialize() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CUSTOM_CREATIVE_TAB_KEY, CUSTOM_CREATIVE_TAB);
    }
}


