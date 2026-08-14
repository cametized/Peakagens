package claire.gens;

import claire.gens.effect.EffectStuff;
import claire.gens.enchant.FloristEnchant;
import claire.gens.enchant.SawEnchant;
import claire.gens.recipes.AlchemyBottleRecipe;
import claire.gens.sounds.SoundClass;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Iterator;

public class Peakagens implements ModInitializer {
	public static final String MOD_ID = "peakagens";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final GameRule<Boolean> literallyTheEnd = GameRuleBuilder
			.forBoolean(true) // Default value declaration
			.category(GameRuleCategory.MISC)
			.buildAndRegister(Identifier.fromNamespaceAndPath(MOD_ID, "literally_the_end"));
	public static final GameRule<Integer> maxChainedBlocks = GameRuleBuilder
			.forInteger(72) // Default value declaration
			.category(GameRuleCategory.PLAYER)
			.buildAndRegister(Identifier.fromNamespaceAndPath(MOD_ID, "max_chained_blocks"));

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Peak inc.. -Cam");
		LOGGER.info("nuke the whole generation -LLEVC");

        ItemStuff.initialize();
		BlockStuff.initialize();
		ModComponents.initialize();
		EffectStuff.initialize();
		ShearWheat.register();
		FloristEnchant.init();
		SawEnchant.init();
		LootTables.modify();
		SoundClass.initialize();
		ModParticles.initialize();

		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(MOD_ID,"crafting_special_alchemy"), AlchemyBottleRecipe.SERIALIZER);
	}

	public static @Nullable LivingEntity findWhoImLookingAt(Level level, Player player, double size) {
		LivingEntity player1 = null;
		double foundDot = 0;
		Iterator<LivingEntity> plrList = level.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(player.position(), size, size, size)).iterator();
		while (plrList.hasNext()) {
			LivingEntity cool = plrList.next();
			AABB woah = cool.getBoundingBox();
			Vec3 heynow = cool.position().subtract(player.position().add(0,player.getBoundingBox().getYsize()/2,0)).multiply(1/woah.getXsize(),1/woah.getYsize(),1/woah.getZsize());
			double dot = player.getViewVector(1.0f).multiply(1/woah.getXsize(),1/woah.getYsize(),1/woah.getZsize()).normalize().dot(heynow.normalize());

			if (cool != player) {
				//Peakagens.LOGGER.info(cool.getName().getString());
				//Peakagens.LOGGER.info(String.valueOf(dot));
				//Peakagens.LOGGER.info(String.valueOf(heynow.y));
				if (dot >= 0.99f && dot > foundDot) {
					foundDot = dot;
					player1 = cool;
				}
			}
		}
		return player1;
	}

	public static @Nullable LivingEntity findWhoImLookingAt(Level level, Player player) {
		return findWhoImLookingAt(level,player,16);
	}
}
