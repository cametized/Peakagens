package claire.gens;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MaceItem;
import net.minecraft.world.level.Level;
import net.minecraft.core.particles.ParticleTypes;

public class BloodMace extends MaceItem {
    public BloodMace(Properties properties) {
        super(properties);
    }
    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = attacker.level();
        boolean didHitEntity = !level.isClientSide();
        int combo = getComboStage(stack);

        if (didHitEntity) {
            combo++;
            //Peakagens.LOGGER.info("Hit registered. Combo = " + combo);
            if (combo >= 3) {
                stack.set(DataComponents.ITEM_MODEL, Identifier.fromNamespaceAndPath("peakagens","bloodierfinal"));
                setComboStage(stack, 3);

                if (attacker instanceof ServerPlayer player) {
                    level.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(),
                            SoundEvents.FIRECHARGE_USE, attacker.getSoundSource(), 12.0F, 0.8F);
                }
            } else if (combo == 2) {
                stack.set(DataComponents.ITEM_MODEL, Identifier.fromNamespaceAndPath("peakagens", "bloodier2"));
                setComboStage(stack, 2);
            } else {
                stack.set(DataComponents.ITEM_MODEL, Identifier.fromNamespaceAndPath("peakagens", "bloodier1"));
                setComboStage(stack, 1);
            }

            setComboStage(stack, combo);
        }
        if (combo == 0) {
            stack.set(DataComponents.ITEM_MODEL, Identifier.fromNamespaceAndPath("peakagens", "blood_mace"));
            setComboStage(stack, 0);
        }
        if (combo >= 3) {
            if (attacker instanceof ServerPlayer player) {
                level.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(),
                        SoundEvents.MACE_SMASH_GROUND_HEAVY, attacker.getSoundSource(), 3.0F, 1.0F);
                int particles = Math.round(target.getBbWidth() * target.getBbWidth() * target.getBbHeight()) * 14;
                player.level().sendParticles(ParticleTypes.FLAME, target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ(),particles, target.getBbWidth()/2, target.getBbHeight()/2, target.getBbWidth()/2, 0.0);
                player.level().sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ(), particles, target.getBbWidth()/2, target.getBbHeight()/2, target.getBbWidth()/2, 1.5);
                stack.set(DataComponents.ITEM_MODEL, Identifier.fromNamespaceAndPath("peakagens","blood_mace"));
                target.hurtServer((ServerLevel) level,player.damageSources().source(ResourceKey.create(Registries.DAMAGE_TYPE,Identifier.fromNamespaceAndPath(Peakagens.MOD_ID,"blood_mace"))),18);
                setComboStage(stack, 0);
            }
        }
        super.postHurtEnemy(stack, target, attacker);
    }
    private int getComboStage(ItemStack stack) {
        Identifier model = stack.get(DataComponents.ITEM_MODEL);
        if (model == null) return 0;
        if (model.getPath().equals("bloodier1")) return 1;
        if (model.getPath().equals("bloodier2")) return 2;
        if (model.getPath().equals("bloodierfinal")) return 3;
        return 0;
    }

    private void setComboStage(ItemStack stack, int combo) {}
}