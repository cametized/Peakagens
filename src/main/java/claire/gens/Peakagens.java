package claire.gens;

import claire.gens.effect.EffectStuff;
import claire.gens.recipes.AlchemyBottleRecipe;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;;
public class Peakagens implements ModInitializer {
	public static final String MOD_ID = "peakagens";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Peak inc.. -Cam");
		LOGGER.info("nuke the whole generation -LLEVC");

        ItemStuff.initialize();
		ModComponents.initialize();
		EffectStuff.initialize();
		ShearWheat.register();
		FloristEnchant.init();

		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(MOD_ID,"crafting_special_alchemy"), AlchemyBottleRecipe.SERIALIZER);
	}
}