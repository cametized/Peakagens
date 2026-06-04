package claire.gens.effect;

import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Short extends MobEffect {
    public Short() {
        super(MobEffectCategory.BENEFICIAL, 0xffffff);

        this.addAttributeModifier(Attributes.SCALE, Identifier.parse("short"), -0.8D, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return false;
    }
}
