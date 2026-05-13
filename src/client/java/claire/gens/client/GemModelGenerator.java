package claire.gens.client;

import claire.gens.ItemStuff;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.world.item.Item;
import claire.gens.ItemStuff;

import java.awt.*;
import java.util.List;

import static claire.gens.ItemStuff.*;

public class GemModelGenerator extends FabricModelProvider {
    public GemModelGenerator(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        List<Item> hey = List.of(place1,place2,place3,place4);

        for (int i = 0; i < hey.size(); i++) {
            //itemModelGenerators.generateItemWithTintedBaseLayer(hey.get(i), new Color(255, 255, 0).getRGB());
        }
    }
}
