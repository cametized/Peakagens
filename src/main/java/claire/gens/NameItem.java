package claire.gens;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class NameItem extends Item {
    Component component;
    public NameItem(Properties properties, Component component1) {
        super(properties);
        component = component1;
    }

    @Override
    public Component getName(ItemStack itemStack) {
        return itemStack.has(DataComponents.CUSTOM_NAME) ? itemStack.getOrDefault(DataComponents.CUSTOM_NAME,Component.empty()) : component;
    }
}
