package claire.gens.client;

import claire.gens.ModParticles;
import claire.gens.blahaj.block.BlahajBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.minecraft.client.particle.CritParticle;
import net.minecraft.client.particle.GlowParticle;

public class PeakagensClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		ParticleProviderRegistry.getInstance().register(ModParticles.spark, GlowParticle.ElectricSparkProvider::new);
		BlahajBlocks.registerClient();
	}
}