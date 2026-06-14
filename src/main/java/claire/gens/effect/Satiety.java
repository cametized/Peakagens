package claire.gens.effect;

import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Satiety extends MobEffect {
    public Satiety() {
        super(MobEffectCategory.BENEFICIAL, 0x33f4ff);

        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, Identifier.parse("satiety"), 0.1D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.SNEAKING_SPEED, Identifier.parse("satiety"), 0.5D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.BLOCK_BREAK_SPEED, Identifier.parse("satiety"), 0.2D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return false;
    }
}
