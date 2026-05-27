package claire.gens.effect;

import claire.gens.ItemStuff;
import claire.gens.Peakagens;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

public class Vulnerable extends MobEffect {
    public Vulnerable() {
        super(MobEffectCategory.HARMFUL, 0xff6176);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplification) {
        DamageSource damageSource = serverLevel.damageSources().source(ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID,"vulnerability")));
        //mob.setInvulnerable(false);
        mob.hurtServer(serverLevel,damageSource,1f);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration <= amplifier;
    }
}
