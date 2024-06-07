package com.com.mrbysco;

import com.com.mrbysco.client.ClientHandler;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(Sweaters.MOD_ID)
public class Sweaters {
	public static final String MOD_ID = "sweaters";
	public static final Logger LOGGER = LogUtils.getLogger();

	public Sweaters(IEventBus eventBus, Dist dist) {
		if (dist.isClient()) {
			eventBus.addListener(ClientHandler::onClientSetup);
			eventBus.addListener(ClientHandler::registerLayerDefinitions);
			eventBus.addListener(ClientHandler::registerAdditionalLayers);
		}
	}
}