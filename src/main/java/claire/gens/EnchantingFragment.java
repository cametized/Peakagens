package claire.gens;

import net.fabricmc.fabric.impl.item.EnchantmentUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class EnchantingFragment extends BlankFragmentItem {
    public EnchantingFragment(Properties properties) {
        super(properties,FragmentType.Enchanting);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) { // this is 2 hours of work and research btw LMFAO
        LivingEntity player1;
        player1 = player.isCrouching() ? player : Peakagens.findWhoImLookingAt(level, player);
        if (player1 != null) {
            boolean found = false;

            List<EquipmentSlot> equipmentSlotList = List.of(EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.LEGS,EquipmentSlot.FEET,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND);
            for (EquipmentSlot current : equipmentSlotList) {
                ItemStack hey = player1.getItemBySlot(current);
                ItemEnchantments itemEnchantments = hey.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
                if (!itemEnchantments.isEmpty()) {
                    found = true;
                    List<Holder<Enchantment>> yes = new ArrayList<>(itemEnchantments.keySet());
                    int lvl = itemEnchantments.getLevel(yes.getFirst());
                    ItemStack book = EnchantmentHelper.createBook(new EnchantmentInstance(yes.getFirst(), 1));
                    ItemEntity itemEntity = new ItemEntity(level, player1.getX(), player1.getY(), player1.getZ(), book);
                    itemEntity.setPickUpDelay(player1 == player ? 10 : (20*5));
                    level.addFreshEntity(itemEntity);

                    ItemEnchantments.Mutable yosup = new ItemEnchantments.Mutable(itemEnchantments);
                    yosup.set(yes.getFirst(), lvl - 1);
                    hey.set(DataComponents.ENCHANTMENTS, yosup.toImmutable());
                }
            }
            if (found) {
                player.getCooldowns().addCooldown(player.getItemInHand(hand),player==player1 ? 10 : 3*60*20);
            }
            return found ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }
}
