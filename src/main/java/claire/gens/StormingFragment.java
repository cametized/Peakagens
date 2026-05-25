package claire.gens;

import claire.gens.effect.EffectStuff;
import claire.gens.mixin.PlayerInvoker;
import claire.gens.mixin.ThunderInvoker;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.List;
import java.util.Objects;

public class StormingFragment extends BlankFragmentItem {
    public StormingFragment(Properties properties) {
        super(properties,FragmentType.Storm);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.canHaveWeather() && player.isCrouching()) {
            if (level.isRaining() && !level.isClientSide()) {
                Objects.requireNonNull(level.getServer()).setWeatherParameters(0, ServerLevel.RAIN_DELAY.sample(level.getRandom()),false,false);
            } else if (!level.isClientSide())  {
                Objects.requireNonNull(level.getServer()).setWeatherParameters(0, ServerLevel.RAIN_DURATION.sample(level.getRandom()),true,false);
            }
            return InteractionResult.SUCCESS;
        } else if (level.canHaveWeather()) {
            if (level instanceof ServerLevel serverLevel) {
                LevelChunk chunk = level.getChunk(player.chunkPosition().x()+1-level.getRandom().nextInt(3),player.chunkPosition().z()+1-level.getRandom().nextInt(3));
                BlockPos blockPos;

                int amount = 0;
                List<EquipmentSlot> equipmentSlotList = List.of(EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.LEGS,EquipmentSlot.FEET,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND);
                for (int i = 0; i < equipmentSlotList.size(); i++) {
                    if (player.getItemBySlot(equipmentSlotList.get(i)).isValidRepairItem(Items.COPPER_INGOT.getDefaultInstance())) {
                        amount++;
                    }
                }

                if (amount > 3) {
                    blockPos = player.blockPosition();
                    player.addEffect(new MobEffectInstance(EffectStuff.Electrified,20*180));
                } else if (Peakagens.findWhoImLookingAt(level,player,16) != null) {
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
