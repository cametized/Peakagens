package claire.gens;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Peakagens implements ModInitializer {
	public static final String MOD_ID = "peakagens";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Peak inc.. -Cam");

        ItemStuff.initialize();
	}
}