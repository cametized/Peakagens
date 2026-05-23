package claire.gens.sounds;

import claire.gens.Peakagens;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class SoundClass {
    private SoundClass() {
        // private empty constructor to avoid accidental instantiation
    }

    public static final SoundEvent FRAGMENTUSED = registerSound("fragmentused");

    public static final SoundEvent GRINDRAILS = registerSound("grindrails");
    public static final SoundEvent FLAXSONG = registerSound("flaxsong");
    public static final SoundEvent DISC1 = registerSound("disc1");
    public static final SoundEvent TEST11 = registerSound("test11");
    public static final SoundEvent CLEANUP = registerSound("cleanup");
    public static final SoundEvent YAG = registerSound("3pmyag");

    private static SoundEvent registerSound(String id) {
        Identifier identifier = Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, id);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, identifier, SoundEvent.createVariableRangeEvent(identifier));
    }

    public static void initialize() {
        Peakagens.LOGGER.info("Registering " + Peakagens.MOD_ID + " Sounds");
    }
}
