package claire.gens;

import net.fabricmc.api.ModInitializer;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static claire.gens.ItemStuff.CUSTOM_CREATIVE_TAB;
import static claire.gens.ItemStuff.CUSTOM_CREATIVE_TAB_KEY;

public class Peakagens implements ModInitializer {
	public static final String MOD_ID = "peakagens";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Peak inc.. -Cam");

        ItemStuff.initialize();
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CUSTOM_CREATIVE_TAB_KEY, CUSTOM_CREATIVE_TAB);
	}
}