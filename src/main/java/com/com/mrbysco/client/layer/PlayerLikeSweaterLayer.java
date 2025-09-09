package com.com.mrbysco.client.layer;

import com.com.mrbysco.client.ClientHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerLikeSweaterLayer<M extends PlayerModel> extends AbstractSweaterLayer<PlayerRenderState, M> {
	private final PlayerModel slimModel;
	private final PlayerModel model;
	private final List<ResourceLocation> layerLocations = new ArrayList<>();
	private final List<ResourceLocation> slimLayerLocations = new ArrayList<>();

	public PlayerLikeSweaterLayer(RenderLayerParent<PlayerRenderState, M> layerParent,
	                              EntityModelSet modelSet, List<ResourceLocation> layerLocations, List<ResourceLocation> slimLayerLocations) {
		super(layerParent);
		this.slimModel = new PlayerModel(modelSet.bakeLayer(ClientHandler.PLAYER_SLIM_SWEATER_LAYER), true);
		this.model = new PlayerModel(modelSet.bakeLayer(ClientHandler.PLAYER_SWEATER_LAYER), false);

		this.layerLocations.clear();
		this.layerLocations.addAll(layerLocations);
		this.slimLayerLocations.clear();
		this.slimLayerLocations.addAll(slimLayerLocations);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, PlayerRenderState renderState, float yRot, float xRot) {
		final Random random = renderState.getRenderData(ClientHandler.SWEATER_RANDOM);
		if (random != null && random.nextBoolean()) {
			if (renderState.skin.model() == PlayerSkin.Model.SLIM) {
				if (!slimLayerLocations.isEmpty()) {
					ResourceLocation sweaterLocation = slimLayerLocations.getFirst();
					if (slimLayerLocations.size() > 1) {
						sweaterLocation = slimLayerLocations.get(random.nextInt(slimLayerLocations.size()));
					}
					coloredCutoutHumanoidModelCopyLayerRender(this.getParentModel(), this.slimModel, sweaterLocation, poseStack, bufferSource,
							packedLight, renderState, -1);
				}
			} else {
				if (!layerLocations.isEmpty()) {
					ResourceLocation sweaterLocation = layerLocations.getFirst();
					if (layerLocations.size() > 1) {
						sweaterLocation = layerLocations.get(random.nextInt(layerLocations.size()));
					}
					coloredCutoutHumanoidModelCopyLayerRender(this.getParentModel(), this.model, sweaterLocation, poseStack, bufferSource,
							packedLight, renderState, -1);
				}
			}
		}
	}
}