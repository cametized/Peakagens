package claire.gens.effect;

import claire.gens.Peakagens;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;

public class EffectStuff implements ModInitializer {
    public static final Holder<MobEffect> SATIETY =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "satiety"), new Satiety());

    public static final Holder<MobEffect> FLORAL =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "floral"), new Floral());

    public static final Holder<MobEffect> Electrified =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "electrified"), new Electrified());

    public static final Holder<MobEffect> Vulnerable =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "vulnerable"), new Vulnerable());

    public static final Holder<MobEffect> Short =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "short"), new Short());

    public static void initialize() {
    }

    @Override
    public void onInitialize() {
        // ...
    }
}