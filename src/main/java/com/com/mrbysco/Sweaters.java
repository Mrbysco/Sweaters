package com.com.mrbysco;

import com.com.mrbysco.client.ClientHandler;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Sweaters.MOD_ID)
public class Sweaters {
	public static final String MOD_ID = "sweaters";
	public static final Logger LOGGER = LogUtils.getLogger();

	public Sweaters() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () ->
				new IExtensionPoint.DisplayTest(() -> "Trans Rights Are Human Rights", (remoteVersionString, networkBool) -> networkBool));

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			eventBus.addListener(ClientHandler::onClientSetup);
			eventBus.addListener(ClientHandler::registerLayerDefinitions);
			eventBus.addListener(ClientHandler::registerAdditionalLayers);
		});
	}
}
