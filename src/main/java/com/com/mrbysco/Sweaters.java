package com.com.mrbysco;

import com.com.mrbysco.client.ClientHandler;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(value = Sweaters.MOD_ID, dist = Dist.CLIENT)
public class Sweaters {
	public static final String MOD_ID = "sweaters";
	public static final Logger LOGGER = LogUtils.getLogger();

	public Sweaters(IEventBus eventBus) {
		eventBus.addListener(ClientHandler::onClientSetup);
		eventBus.addListener(ClientHandler::registerCustomRenderData);
		eventBus.addListener(ClientHandler::registerLayerDefinitions);
		eventBus.addListener(ClientHandler::registerAdditionalLayers);
	}

	public static ResourceLocation modLoc(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}