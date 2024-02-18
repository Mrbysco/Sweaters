package com.com.mrbysco.client;

import com.com.mrbysco.Sweaters;
import com.com.mrbysco.client.helper.ModelHelper;
import com.com.mrbysco.client.helper.TextureHelper;
import com.com.mrbysco.client.layer.HumanoidSweaterLayer;
import com.com.mrbysco.client.layer.PlayerLikeSweaterLayer;
import com.com.mrbysco.client.layer.SweaterLayer;
import com.com.mrbysco.config.ConfigHandler;
import com.com.mrbysco.config.MobType;
import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ClientHandler {

	public static final ModelLayerLocation PLAYER_SWEATER_LAYER = new ModelLayerLocation(new ResourceLocation(Sweaters.MOD_ID, "player"), "sweater");
	public static final ModelLayerLocation PLAYER_SLIM_SWEATER_LAYER = new ModelLayerLocation(new ResourceLocation(Sweaters.MOD_ID, "player_slim"), "sweater");
	public static final ModelLayerLocation HUMANOID_SWEATER_LAYER = new ModelLayerLocation(new ResourceLocation(Sweaters.MOD_ID, "humanoid"), "sweater");
	public static final ModelLayerLocation HUMANOID_EXTENDED_SWEATER_LAYER = new ModelLayerLocation(new ResourceLocation(Sweaters.MOD_ID, "humanoid_extended"), "sweater");
	public static final ModelLayerLocation CREEPER_SWEATER_LAYER = new ModelLayerLocation(new ResourceLocation(Sweaters.MOD_ID, "creeper"), "sweater");
	public static final ModelLayerLocation CHICKEN_SWEATER_LAYER = new ModelLayerLocation(new ResourceLocation(Sweaters.MOD_ID, "chicken"), "sweater");
	public static final ModelLayerLocation SLIME_SWEATER_LAYER = new ModelLayerLocation(new ResourceLocation(Sweaters.MOD_ID, "slime"), "sweater");
	public static final ModelLayerLocation WOLF_SWEATER_LAYER = new ModelLayerLocation(new ResourceLocation(Sweaters.MOD_ID, "wolf"), "sweater");

	public static void onClientSetup(final FMLClientSetupEvent event) {
		ConfigHandler.initializeConfig();
	}

	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ClientHandler.PLAYER_SWEATER_LAYER, () ->
				LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0.25F), 64, 64));

		event.registerLayerDefinition(PLAYER_SWEATER_LAYER, () ->
				LayerDefinition.create(ModelHelper.createPlayerSweaterMesh(new CubeDeformation(0.25F), false), 64, 64));
		event.registerLayerDefinition(PLAYER_SLIM_SWEATER_LAYER, () ->
				LayerDefinition.create(ModelHelper.createPlayerSweaterMesh(new CubeDeformation(0.25F), true), 64, 64));

		event.registerLayerDefinition(ClientHandler.HUMANOID_SWEATER_LAYER, () ->
				ModelHelper.createHumanoidSweater(new CubeDeformation(0.25F)));
		event.registerLayerDefinition(ClientHandler.HUMANOID_EXTENDED_SWEATER_LAYER, () ->
				ModelHelper.createHumanoidSweater(new CubeDeformation(0.3F)));
		event.registerLayerDefinition(ClientHandler.CREEPER_SWEATER_LAYER, () ->
				CreeperModel.createBodyLayer(new CubeDeformation(0.25F)));
		event.registerLayerDefinition(ClientHandler.CHICKEN_SWEATER_LAYER, () ->
				ModelHelper.createChickenSweater(new CubeDeformation(0.25F)));
		event.registerLayerDefinition(ClientHandler.SLIME_SWEATER_LAYER, () ->
				ModelHelper.createSlimeSweater(new CubeDeformation(0.25F)));
		event.registerLayerDefinition(ClientHandler.WOLF_SWEATER_LAYER, () ->
				ModelHelper.createWolfSweater(new CubeDeformation(0.25F)));
	}

	public static final Map<ResourceLocation, LayerInfo> LAYER_LOCATION_MAP = new HashMap<>();

	public static void registerAdditionalLayers(EntityRenderersEvent.AddLayers event) {
		final EntityModelSet modelSet = event.getEntityModels();
		for (Map.Entry<ResourceLocation, LayerInfo> entry : LAYER_LOCATION_MAP.entrySet()) {
			ResourceLocation entityLocation = entry.getKey();
			LayerInfo info = entry.getValue();
			Optional<EntityType<?>> foundType = BuiltInRegistries.ENTITY_TYPE.getOptional(entityLocation);
			if(!foundType.isPresent()) {
				Sweaters.LOGGER.error("Ignoring {} as it doesn't exist", entityLocation);
			} else {
				EntityType<? extends LivingEntity> entityType = (EntityType<? extends LivingEntity>) foundType.get();
				EntityRenderer<? extends Entity> entityRenderer = event.getRenderer(entityType);
				if (entityRenderer instanceof LivingEntityRenderer<?, ?> livingEntityRenderer) {
					MobType type = info.type();
					switch (type) {
						case CREEPER -> {
							if (livingEntityRenderer instanceof CreeperRenderer renderer) {
								renderer.addLayer(new SweaterLayer(renderer, () -> new CreeperModel<>(
										modelSet.bakeLayer(type.getModelLayerLocation())), info.textures()));
							} else if (livingEntityRenderer.getModel() instanceof CreeperModel) {
								livingEntityRenderer.addLayer(new SweaterLayer(livingEntityRenderer, () -> new CreeperModel<>(
										modelSet.bakeLayer(type.getModelLayerLocation())), info.textures()));
							}
						}
						case CHICKEN -> {
							if (livingEntityRenderer instanceof ChickenRenderer renderer) {
								renderer.addLayer(new SweaterLayer(renderer, () -> new ChickenModel<>(
										modelSet.bakeLayer(type.getModelLayerLocation())), info.textures()));
							} else if (livingEntityRenderer.getModel() instanceof ChickenModel) {
								livingEntityRenderer.addLayer(new SweaterLayer(livingEntityRenderer, () -> new ChickenModel<>(
										modelSet.bakeLayer(type.getModelLayerLocation())), info.textures()));
							}
						}
						case SLIME -> {
							if (livingEntityRenderer instanceof SlimeRenderer renderer) {
								renderer.addLayer(new SweaterLayer(renderer, () -> new SlimeModel<>(
										modelSet.bakeLayer(type.getModelLayerLocation())), info.textures()));
							} else if (livingEntityRenderer.getModel() instanceof SlimeModel) {
								livingEntityRenderer.addLayer(new SweaterLayer(livingEntityRenderer, () -> new SlimeModel<>(
										modelSet.bakeLayer(type.getModelLayerLocation())), info.textures()));
							}
						}
						case WOLF -> {
							if (livingEntityRenderer instanceof WolfRenderer renderer) {
								renderer.addLayer(new SweaterLayer(renderer, () -> new WolfModel<>(
										modelSet.bakeLayer(type.getModelLayerLocation())), info.textures()));
							} else if (livingEntityRenderer.getModel() instanceof WolfModel) {
								livingEntityRenderer.addLayer(new SweaterLayer(livingEntityRenderer, () -> new WolfModel<>(
										modelSet.bakeLayer(type.getModelLayerLocation())), info.textures()));
							}
						}
						case PLAYER -> {
							if (entityLocation.toString().equals("minecraft:player")) {
								event.getSkins().forEach(s -> {
									LivingEntityRenderer<? extends Player, ? extends EntityModel<? extends Player>> playerEntityRenderer = event.getSkin(s);
									if (playerEntityRenderer instanceof PlayerRenderer playerRenderer) {
										playerRenderer.addLayer(new PlayerLikeSweaterLayer<>(playerRenderer, modelSet,
												TextureHelper.HUMANOID_SWEATER_TEXTURES, TextureHelper.SLIM_SWEATER_TEXTURES));
									}
								});
							} else {
								if (livingEntityRenderer != null) {
									if (livingEntityRenderer.getModel() instanceof PlayerModel) {
										if (livingEntityRenderer.getModel() instanceof PlayerModel) {
											if (livingEntityRenderer instanceof HumanoidMobRenderer renderer) {
												renderer.addLayer(new PlayerLikeSweaterLayer<>(renderer, modelSet,
														TextureHelper.HUMANOID_SWEATER_TEXTURES, TextureHelper.SLIM_SWEATER_TEXTURES));
											} else {
												LivingEntityRenderer<LivingEntity, PlayerModel<LivingEntity>> playerRenderer = (LivingEntityRenderer<LivingEntity, PlayerModel<LivingEntity>>) livingEntityRenderer;
												playerRenderer.addLayer(new PlayerLikeSweaterLayer<>(playerRenderer, modelSet,
														TextureHelper.HUMANOID_SWEATER_TEXTURES, TextureHelper.SLIM_SWEATER_TEXTURES));
											}
										}
									} else {
										Sweaters.LOGGER.error("Can't attach sweater layer to {} as it's model isn't an instance of HumanoidModel", entityLocation);
									}
								}
							}
						}
						case HUMANOID, HUMANOID_EXTENDED -> {
							if (livingEntityRenderer != null) {
								if (livingEntityRenderer.getModel() instanceof HumanoidModel) {
									livingEntityRenderer.addLayer(new HumanoidSweaterLayer(livingEntityRenderer, () -> new HumanoidModel<>(
											modelSet.bakeLayer(type.getModelLayerLocation())), info.textures()));
								} else {
									Sweaters.LOGGER.error("Can't attach sweater layer to {} as it's model isn't an instance of HumanoidModel", entityLocation);
								}
							}
						}
					}
				}
			}
		}
	}

	public record LayerInfo(MobType type, List<ResourceLocation> textures) {

	}
}
