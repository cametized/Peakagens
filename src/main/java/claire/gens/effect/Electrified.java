package claire.gens.effect;

import claire.gens.ModParticles;
import claire.gens.Peakagens;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;

import java.util.List;

public class Electrified extends MobEffect {
    public Electrified() {
        super(MobEffectCategory.BENEFICIAL, 0x33f4ff, ModParticles.spark);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplification) {
        List<EquipmentSlot> equipmentSlotList = List.of(EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.LEGS,EquipmentSlot.FEET);
        TagKey<Item> conductiveTag = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID,"conductive"));
        TagKey<Item> ultraconductiveTag = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID,"ultraconductive"));
        for (int i = 0; i < equipmentSlotList.size(); i++) {
            if (mob.getItemBySlot(equipmentSlotList.get(i)).is(conductiveTag)) {
                //you can ignore this for now, im putting code here later
            }
        }
        return super.applyEffectTick(serverLevel, mob, amplification);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return false;
    }
}
