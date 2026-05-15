package claire.gens.recipes;

import claire.gens.AlchemyFragmentItem;
import claire.gens.ItemStuff;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RepairItemRecipe;
import net.minecraft.world.level.Level;

import javax.xml.crypto.Data;
import java.util.Optional;

public class AlchemyBottleRecipe extends CustomRecipe {
    public static final AlchemyBottleRecipe instance = new AlchemyBottleRecipe();
    public static final MapCodec<AlchemyBottleRecipe> MAP_CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, AlchemyBottleRecipe> STREAM_CODEC;
    public static final RecipeSerializer<AlchemyBottleRecipe> SERIALIZER;

    @Override
    public boolean matches(CraftingInput input, Level level) {
        boolean woo = ((input.items().getFirst().is(ItemStuff.alchemy) && input.items().getLast().is(Items.GLASS_BOTTLE)) || (input.items().getLast().is(ItemStuff.alchemy) && input.items().getFirst().is(Items.GLASS_BOTTLE)));
        ItemStack alchemy = (input.items().getFirst().is(ItemStuff.alchemy) && input.items().getLast().is(Items.GLASS_BOTTLE)) ? input.items().getFirst() : input.items().getLast();
        return input.ingredientCount() == 2 && woo && alchemy.getOrDefault(DataComponents.POTION_CONTENTS,PotionContents.EMPTY).hasEffects();
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        boolean hey = (input.items().getFirst().is(ItemStuff.alchemy) && input.items().getLast().is(Items.GLASS_BOTTLE));
        ItemStack alchemy = hey ? input.items().getFirst() : input.items().getLast();
        PotionContents potionContents = alchemy.getOrDefault(DataComponents.POTION_CONTENTS,PotionContents.EMPTY); //Component.translatable("item.peakagens.alchemy_potion")
        ItemStack potion = new ItemStack(Items.POTION);
        potion.set(DataComponents.POTION_CONTENTS,potionContents);
        potion.set(DataComponents.CUSTOM_NAME,Component.translatable("item.peakagens.alchemy_potion"));

        return potion;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return SERIALIZER;
    }

    static {
        MAP_CODEC = MapCodec.unit(instance);
        STREAM_CODEC = StreamCodec.unit(instance);
        SERIALIZER = new RecipeSerializer(MAP_CODEC, STREAM_CODEC);
    }
}
