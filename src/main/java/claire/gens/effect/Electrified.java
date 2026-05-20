package claire.gens.effect;

import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Electrified extends MobEffect {
    public Electrified() {
        super(MobEffectCategory.BENEFICIAL, 0x33f4ff);

        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, Identifier.parse("electrified_attack_damage"), 3d, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, Identifier.parse("electrified_armor_toughness"), 10d, AttributeModifier.Operation.ADD_VALUE);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return false;
    }
}
