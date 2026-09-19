package claire.gens.enchant;

import claire.gens.effect.EffectStuff;
import claire.gens.mixin.PlayerInvoker;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import java.util.List;
import java.util.Random;

public class FloristEnchant {
    public static final String MOD_ID = "peakagens";
    private static final TagKey<Enchantment> FLORIST =
            TagKey.create(Registries.ENCHANTMENT,
                    Identifier.fromNamespaceAndPath(MOD_ID, "florist"));
    public static final ResourceKey<Enchantment> FLORIST_KEY = ResourceKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath(MOD_ID, "florist"));

    private static final List<Block> FLOWERS = List.of(
            Blocks.DANDELION, Blocks.POPPY, Blocks.BLUE_ORCHID, Blocks.ALLIUM, Blocks.AZURE_BLUET, Blocks.RED_TULIP, Blocks.ORANGE_TULIP, Blocks.WHITE_TULIP, Blocks.PINK_TULIP, Blocks.OXEYE_DAISY, Blocks.CORNFLOWER, Blocks.LILY_OF_THE_VALLEY, Blocks.SHORT_GRASS, Blocks.TALL_GRASS, Blocks.ROSE_BUSH, Blocks.PEONY, Blocks.LILAC, Blocks.WILDFLOWERS
    ); // hell on earth bro

    private static final Random RANDOM = new Random();

    public static void heyooo(DamageSource damageSource, Entity entity, double size) {
        if (!(damageSource.getEntity() instanceof LivingEntity attacker)) {
            return;
        }
        var item = attacker.getMainHandItem();
        boolean hasFlorist = false;
        for (var entry : EnchantmentHelper.getEnchantmentsForCrafting(item).entrySet()) {

            Holder<Enchantment> enchantment = entry.getKey();
            int level = entry.getIntValue();

            if (level > 0 && enchantment.is(FLORIST)) {
                hasFlorist = true;
                break;
            }
        }
        if (hasFlorist) {
            BlockPos pos = entity.blockPosition();
            spawnFlowers(entity.level(), pos, size);
            var instance = new MobEffectInstance(EffectStuff.FLORAL, 5 * 60, 0, false, true, true);
            attacker.addEffect(instance);

            if (entity.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 35, 0.5, 0.5, 0.5, 0.1
                );
                serverLevel.sendParticles(ParticleTypes.COMPOSTER, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 25, 0.3, 0.3, 0.3, 0.02
                );
            }
            entity.level().playSound(
                    null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 2.0F, 1.0F
            );
        }
    }

    public static void init() {
        ServerLivingEntityEvents.AFTER_DAMAGE.register(((entity, source, baseDamageTaken, damageTaken, blocked) -> {
            if (source.getEntity() instanceof Player player) {
                if (((PlayerInvoker) player).peakagens$canCriticalAttack(entity)) {
                    heyooo(source,entity,1);
                }
            }
        }));
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            heyooo(damageSource,entity,5);
        });
    }

    private static void spawnFlowers(Level level, BlockPos center, double size) {
        for (int x = (int) -size; x <= size; x++) {
            for (int z = (int) -size; z <= size; z++) {
                if (RANDOM.nextFloat() > 0.4f) continue;
                BlockPos target = center.offset(x, 0, z);
                for (int y = (int) size; y >= -size; y--) {
                    BlockPos testPos = target.above(y);
                    if (level.getBlockState(testPos).isAir() // PEE PEE POO POO ASS MATH AAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHH
                            && (level.getBlockState(testPos.below()).is(Blocks.GRASS_BLOCK)
                            || level.getBlockState(testPos.below()).is(Blocks.DIRT))) {
                        Block randomFlower =
                                FLOWERS.get(RANDOM.nextInt(FLOWERS.size()));
                        level.setBlock(testPos,
                                randomFlower.defaultBlockState(),
                                3);
                        if (randomFlower instanceof DoublePlantBlock doublePlantBlock) {
                            level.setBlock(testPos.above(),
                                    doublePlantBlock.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER),
                                    3);
                        }
                        break;
                    }
                }
            }
        }
    }
}