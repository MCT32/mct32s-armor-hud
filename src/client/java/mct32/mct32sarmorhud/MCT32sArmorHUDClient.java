package mct32.mct32sarmorhud;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;

public class MCT32sArmorHUDClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		AutoConfig.register(ArmorHUDConfig.class, Toml4jConfigSerializer::new);

		ArmorHUDConfig config = AutoConfig.getConfigHolder(ArmorHUDConfig.class).getConfig();

		HudRenderCallback.EVENT.register((context, timeDelta) -> {
			final int[] offset = {0};
			MinecraftClient.getInstance().player.getArmorItems().forEach(itemStack -> {
				context.drawItem(itemStack, config.gap_x + offset[0], context.getScaledWindowHeight() - 16 - config.gap_y);
				context.drawItemInSlot(MinecraftClient.getInstance().textRenderer, itemStack, config.gap_x + offset[0], context.getScaledWindowHeight() - 16 - config.gap_y);

                offset[0] = offset[0] + config.offset;
			});
		});
	}
}