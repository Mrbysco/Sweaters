package com.com.mrbysco.client.layer;

import com.com.mrbysco.client.ClientHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerLikeSweaterLayer<T extends LivingEntity> extends AbstractSweaterLayer<T, PlayerModel<T>> {
	private final HumanoidModel<T> slimModel;
	private final HumanoidModel<T> model;
	private final List<ResourceLocation> layerLocations = new ArrayList<>();
	private final List<ResourceLocation> slimLayerLocations = new ArrayList<>();

	public PlayerLikeSweaterLayer(RenderLayerParent<T, PlayerModel<T>> layerParent,
								  EntityModelSet modelSet, List<ResourceLocation> layerLocations, List<ResourceLocation> slimLayerLocations) {
		super(layerParent);
		this.slimModel = new PlayerModel<>(modelSet.bakeLayer(ClientHandler.PLAYER_SLIM_SWEATER_LAYER), true);
		this.model = new PlayerModel<>(modelSet.bakeLayer(ClientHandler.PLAYER_SWEATER_LAYER), false);

		this.layerLocations.clear();
		this.layerLocations.addAll(layerLocations);
		this.slimLayerLocations.clear();
		this.slimLayerLocations.addAll(slimLayerLocations);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLightIn, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		final Random random = new Random(livingEntity.hashCode());
		if (random.nextBoolean()) {
			if (this.getParentModel().slim) {
				if (!slimLayerLocations.isEmpty()) {
					ResourceLocation sweaterLocation = slimLayerLocations.get(0);
					if (slimLayerLocations.size() > 1) {
						sweaterLocation = slimLayerLocations.get(random.nextInt(slimLayerLocations.size()));
					}
					coloredCutoutHumanoidModelCopyLayerRender(this.getParentModel(), this.slimModel, sweaterLocation, poseStack, bufferSource,
							packedLightIn, livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
							partialTicks, 1.0F, 1.0F, 1.0F);
				}
			} else {
				if (!layerLocations.isEmpty()) {
					ResourceLocation sweaterLocation = layerLocations.get(0);
					if (layerLocations.size() > 1) {
						sweaterLocation = layerLocations.get(random.nextInt(layerLocations.size()));
					}
					coloredCutoutHumanoidModelCopyLayerRender(this.getParentModel(), this.model, sweaterLocation, poseStack, bufferSource,
							packedLightIn, livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
							partialTicks, 1.0F, 1.0F, 1.0F);
				}
			}
		}
	}
}