package claire.gens;

import claire.gens.effect.EffectStuff;
import claire.gens.recipes.AlchemyBottleRecipe;
import claire.gens.sounds.SoundClass;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Iterator;

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
		LootTables.modify();
		SoundClass.initialize();

		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(MOD_ID,"crafting_special_alchemy"), AlchemyBottleRecipe.SERIALIZER);
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
			entries.accept(ItemStuff.disc1);
			entries.accept(ItemStuff.test11);
			entries.accept(ItemStuff.cleanup);
			entries.accept(ItemStuff.yag);
			entries.accept(ItemStuff.grindrails);
			entries.accept(ItemStuff.colonize);
			entries.accept(ItemStuff.flaxsong);
		});
	}

	public static @Nullable Player findWhoImLookingAt(Level level, Player player) {
		Player player1 = null;
		double foundDot = 0;
		Iterator<Player> plrList = level.getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 16, 16, 16)).iterator();
		while (plrList.hasNext()) {
			Player cool = plrList.next();
			Vec3 heynow = cool.position().subtract(player.position()).subtract(0,player.getBoundingBox().getYsize()/2,0).multiply(1.0/cool.getBoundingBox().getXsize(),1.0/cool.getBoundingBox().getYsize(),1.0/cool.getBoundingBox().getZsize()).normalize();
			double dot = player.getViewVector(1.0f).normalize().dot(heynow);

			//Peakagens.LOGGER.info(String.valueOf(dot));

			if (dot >= 0.99f && cool != player && dot > foundDot) {
				foundDot = dot;
				player1 = cool;
			}
		}
		return player1;
	}
}
