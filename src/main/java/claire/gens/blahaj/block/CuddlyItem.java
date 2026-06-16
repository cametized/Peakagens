package claire.gens.blahaj.block;

import claire.gens.blahaj.BlahajDataComponentTypes;
import claire.gens.blahaj.OwnerComponent;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

public class CuddlyItem extends BlockItem {
    private final Component tooltip;

    public CuddlyItem(Block block, Item.Properties settings, String tooltip) {
        super(block, settings);
        this.tooltip = (tooltip == null) ? null : (Component)Component.translatable(tooltip).withStyle(ChatFormatting.GRAY);
    }

    public void onCraftedBy(ItemStack stack, Player player) {
        super.onCraftedBy(stack, player);
        if (player != null)
            stack.set(BlahajDataComponentTypes.OWNER, new OwnerComponent(player.getName()));
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        super.appendHoverText(stack, context, displayComponent, textConsumer, type);
        if (this.tooltip != null)
            textConsumer.accept(this.tooltip);
        stack.addToTooltip(BlahajDataComponentTypes.OWNER, context, displayComponent, textConsumer, type);
    }

    public static final Identifier MINING_SPEED_MODIFIER_ID = Identifier.fromNamespaceAndPath("blahaj", "base_attack_damage");

    public static ItemAttributeModifiers createAttributeModifiers() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.BLOCK_BREAK_SPEED, new AttributeModifier(MINING_SPEED_MODIFIER_ID, -3.0D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, -2.0D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.MAINHAND)
                .build();
    }
}
