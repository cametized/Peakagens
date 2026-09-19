package claire.gens;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public class ModGamerules {
    public static final GameRule<Boolean> literallyTheEnd = GameRuleBuilder
            .forBoolean(true) // Default value declaration
            .category(GameRuleCategory.MISC)
            .buildAndRegister(Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "literally_the_end"));
    public static final GameRule<Integer> maxChainedBlocks = GameRuleBuilder
            .forInteger(72) // Default value declaration
            .category(GameRuleCategory.PLAYER)
            .buildAndRegister(Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "max_chained_blocks"));
    public static final GameRule<Boolean> gemReplacesUpgrades = GameRuleBuilder.forBoolean(true).category(GameRuleCategory.DROPS).buildAndRegister(Identifier.fromNamespaceAndPath(Peakagens.MOD_ID,"gem_replaces_upgrades"));
    public static final GameRule<Boolean> gemReplacesCores = GameRuleBuilder.forBoolean(true).category(GameRuleCategory.DROPS).buildAndRegister(Identifier.fromNamespaceAndPath(Peakagens.MOD_ID,"gem_replaces_cores"));
    public static final GameRule<Integer> gemReplaceChance = GameRuleBuilder.forInteger(100).category(GameRuleCategory.DROPS).buildAndRegister(Identifier.fromNamespaceAndPath(Peakagens.MOD_ID,"gem_replace_chance"));
    public static void initialize() {}
}
