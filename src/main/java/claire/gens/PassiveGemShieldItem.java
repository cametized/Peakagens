package claire.gens;

import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class PassiveGemShieldItem extends ShieldItem {
    public FragmentType type = FragmentType.PotionPassive;

    public PassiveGemShieldItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        String hey = itemStack.getOrDefault(ModComponents.AppliedGemName, "item.peakagens.none");
        if (hey.equals("item.peakagens.none")) {
            builder.accept(Component.translatable(hey));
        } else {
            builder.accept(Component.translatable("item.peakagens.frag_" + itemStack.getOrDefault(ModComponents.FragmentLevel, 0)).append(Component.translatable(hey)));
        }
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
    }
}
