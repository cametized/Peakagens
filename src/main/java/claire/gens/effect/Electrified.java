package claire.gens.effect;

import claire.gens.ModParticles;
import claire.gens.Peakagens;
import claire.gens.StormingFragment;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

public class Electrified extends MobEffect {
    public Electrified() {
        super(MobEffectCategory.BENEFICIAL, 0x33f4ff, ModParticles.spark);
    }

    public static Identifier armorToughnessIdentifier = Identifier.fromNamespaceAndPath(Peakagens.MOD_ID,"electrified_armor_toughness");
    public static Identifier armorIdentifier = Identifier.fromNamespaceAndPath(Peakagens.MOD_ID,"electrified_armor");
    public static Identifier damageIdentifier = Identifier.fromNamespaceAndPath(Peakagens.MOD_ID,"electrified_attack_damage");

    @Unique
    public static AttributeInstance gay(AttributeMap attributeMap, Holder<Attribute> attribute, Identifier identifier, int amount) {
        AttributeInstance balls = attributeMap.getInstance(attribute);
        if (balls != null) {
            if (!attributeMap.hasModifier(attribute,identifier)) {
                AttributeModifier attMod = new AttributeModifier(identifier,amount, AttributeModifier.Operation.ADD_VALUE);
                balls.addPermanentModifier(attMod);
            } else if (attributeMap.hasModifier(attribute,identifier) && attributeMap.getValue(attribute)-attributeMap.getBaseValue(attribute) != amount) {
                if (balls.removeModifier(identifier)) {
                    AttributeModifier attMod = new AttributeModifier(identifier,amount, AttributeModifier.Operation.ADD_VALUE);
                    balls.addPermanentModifier(attMod);
                }
            }
        }
        return balls;
    }

    @Unique
    public static AttributeInstance say(AttributeMap attributeMap, Holder<Attribute> attribute, Identifier identifier) {
        AttributeInstance balls = attributeMap.getInstance(attribute);
        if (balls != null) {
            if (attributeMap.hasModifier(attribute,identifier)) {
                balls.removeModifier(identifier);
            }
        }
        return balls;
    }

    @Unique
    public static List<Integer> bro(LivingEntity mob) {
        List<EquipmentSlot> equipmentSlotList = List.of(EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.LEGS,EquipmentSlot.FEET);
        List<EquipmentSlot> equipmentSlotList1 = List.of(EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND);
        int amount = 0;
        int amount1 = 0;
        for (EquipmentSlot equipmentSlot : equipmentSlotList) {
            if (StormingFragment.isConductive(mob.getItemBySlot(equipmentSlot)) > 0) {
                //you can ignore this for now, im putting code here later
                amount += StormingFragment.isConductive(mob.getItemBySlot(equipmentSlot));
            }
        }
        for (EquipmentSlot equipmentSlot : equipmentSlotList1) {
            if (StormingFragment.isConductive(mob.getItemBySlot(equipmentSlot)) > 0) {
                //you can ignore this for now, im putting code here later
                amount1 += StormingFragment.isConductive(mob.getItemBySlot(equipmentSlot));
            }
        }
        return List.of(amount,amount1);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplification) {
        List<Integer> amount = bro(mob);
        AttributeInstance strength = gay(mob.getAttributes(),Attributes.ATTACK_DAMAGE,damageIdentifier,Math.round((float) amount.getFirst() /2f)+amount.getLast());
        AttributeInstance armor = gay(mob.getAttributes(),Attributes.ARMOR,armorIdentifier,amount.getFirst()*2);
        AttributeInstance armorToughness = gay(mob.getAttributes(),Attributes.ARMOR_TOUGHNESS,armorToughnessIdentifier,amount.getFirst());
        mob.getAttributes().apply(List.of(strength.pack(),armor.pack(),armorToughness.pack()));
        return super.applyEffectTick(serverLevel, mob, amplification);
    }

    @Override
    public void onEffectRemoved(MobEffectInstance effectInstance, LivingEntity entity) {
        //super.onEffectRemoved(effectInstance, entity);
        AttributeInstance strength = say(entity.getAttributes(),Attributes.ATTACK_DAMAGE,damageIdentifier);
        AttributeInstance armor = say(entity.getAttributes(),Attributes.ARMOR,armorIdentifier);
        AttributeInstance armorToughness = say(entity.getAttributes(),Attributes.ARMOR_TOUGHNESS,armorToughnessIdentifier);
        entity.getAttributes().apply(List.of(strength.pack(),armor.pack(),armorToughness.pack()));
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return Math.round((float) duration / 20f) == (float) duration / 20f;
    }
}
