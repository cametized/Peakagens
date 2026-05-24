package claire.gens.sounds;

import claire.gens.Peakagens;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;

public class Jukebox {
    public static final ResourceKey<JukeboxSong> GRINDRAILS = ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "grindrails"));
    public static final ResourceKey<JukeboxSong> FLAXSONG = ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "flaxsong"));
    public static final ResourceKey<JukeboxSong> DISC1 = ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "disc1"));
    public static final ResourceKey<JukeboxSong> TEST11 = ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "test11"));
    public static final ResourceKey<JukeboxSong> CLEANUP = ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "cleanup"));
    public static final ResourceKey<JukeboxSong> YAG = ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "3pmyag"));
    public static final ResourceKey<JukeboxSong> COLONIZE = ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.fromNamespaceAndPath(Peakagens.MOD_ID, "colonize"));
}
