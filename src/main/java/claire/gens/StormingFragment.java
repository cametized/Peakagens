package claire.gens;

import claire.gens.effect.EffectStuff;
import claire.gens.mixin.PlayerInvoker;
import claire.gens.mixin.ThunderInvoker;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Repairable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.Objects;

public class StormingFragment extends BlankFragmentItem {
    public StormingFragment(Properties properties) {
        super(properties,FragmentType.Storm);
    }

    @Unique public static int isConductive(ItemStack item) {
        int amount = 0;
        Repairable repairable = item.get(DataComponents.REPAIRABLE);
        if (repairable != null) {
            for (int i = 0; i < repairable.items().size(); i++) {
                if (repairable.items().get(i).is(ItemStuff.conductiveTag)) {
                    amount++;
                    if (repairable.items().get(i).is(ItemStuff.ultraConductiveTag)) {
                        amount++;
                    }
                    break;
                }
            }
        }
        return amount;
    }

    @Unique public static int hey(Player player) {
        int amount = 0;
        List<EquipmentSlot> equipmentSlotList = List.of(EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.LEGS,EquipmentSlot.FEET,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND);
        for (EquipmentSlot equipmentSlot : equipmentSlotList) {
            amount = amount + isConductive(player.getItemBySlot(equipmentSlot));
        }
        return amount;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        LivingEntity boo = Peakagens.findWhoImLookingAt(level,player);
        int amount = hey(player);

        if (level.canHaveWeather() && player.isCrouching() && boo == null) {
            if (level.isRaining() && !level.isClientSide()) {
                Objects.requireNonNull(level.getServer()).setWeatherParameters(0, ServerLevel.RAIN_DELAY.sample(level.getRandom()),false,false);
            } else if (!level.isClientSide())  {
                Objects.requireNonNull(level.getServer()).setWeatherParameters(0, ServerLevel.RAIN_DURATION.sample(level.getRandom()),true,false);
            }
            player.getCooldowns().addCooldown(player.getItemInHand(hand),20);
            return InteractionResult.SUCCESS;
        } else if (level.canHaveWeather()) {
            if (level instanceof ServerLevel serverLevel) {
                LevelChunk chunk = level.getChunk(player.chunkPosition().x()+1-level.getRandom().nextInt(3),player.chunkPosition().z()+1-level.getRandom().nextInt(3));
                BlockPos blockPos;

                if (amount > 4 && !(player.isCrouching() && boo != null)) {
                    blockPos = player.blockPosition();
                } else if (boo != null) {
                    blockPos = Objects.requireNonNull(Peakagens.findWhoImLookingAt(level, player,48)).blockPosition();
                } else {
                    blockPos = ((ThunderInvoker) serverLevel).peakagens$findLightningTargetAround(((ThunderInvoker) serverLevel).peakagens$findLightningTargetAround(serverLevel.getBlockRandomPos(chunk.getPos().getMinBlockX(),0,chunk.getPos().getMinBlockZ(),15)));
                }

                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,level);
                lightningBolt.setPos(blockPos.getBottomCenter());
                serverLevel.addFreshEntity(lightningBolt);
            }
            player.getCooldowns().addCooldown(player.getItemInHand(hand),10*20);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
