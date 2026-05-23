package claire.gens.sounds;

import claire.gens.Peakagens;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;

public class Jukebox {
    public static final ResourceKey<JukeboxSong> GRINDRAILS = ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "grindrails"));
    public static final ResourceKey<JukeboxSong> FLAXSONG = ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "flaxsong"));
}
