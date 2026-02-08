package com.com.mrbysco.client.layer;

import com.com.mrbysco.client.ClientHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.PlayerModelType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerLikeSweaterLayer<M extends PlayerModel> extends AbstractSweaterLayer<AvatarRenderState, M> {
	private final PlayerModel slimModel;
	private final PlayerModel model;
	private final List<Identifier> layerLocations = new ArrayList<>();
	private final List<Identifier> slimLayerLocations = new ArrayList<>();

	public PlayerLikeSweaterLayer(RenderLayerParent<AvatarRenderState, M> layerParent,
	                              EntityModelSet modelSet, List<Identifier> layerLocations, List<Identifier> slimLayerLocations) {
		super(layerParent);
		this.slimModel = new PlayerModel(modelSet.bakeLayer(ClientHandler.PLAYER_SLIM_SWEATER_LAYER), true);
		this.model = new PlayerModel(modelSet.bakeLayer(ClientHandler.PLAYER_SWEATER_LAYER), false);

		this.layerLocations.clear();
		this.layerLocations.addAll(layerLocations);
		this.slimLayerLocations.clear();
		this.slimLayerLocations.addAll(slimLayerLocations);
	}

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight,
	                   AvatarRenderState renderState, float yRot, float xRot) {
		final Random random = renderState.getRenderData(ClientHandler.SWEATER_RANDOM);
		if (random != null && random.nextBoolean()) {
			if (renderState.skin.model() == PlayerModelType.SLIM) {
				if (!slimLayerLocations.isEmpty()) {
					Identifier sweaterLocation = slimLayerLocations.getFirst();
					if (slimLayerLocations.size() > 1) {
						sweaterLocation = slimLayerLocations.get(random.nextInt(slimLayerLocations.size()));
					}
					coloredCutoutModelCopyLayerRender(this.model, sweaterLocation, poseStack, nodeCollector,
							packedLight, renderState, -1, renderState.outlineColor);
				}
			} else {
				if (!layerLocations.isEmpty()) {
					Identifier sweaterLocation = layerLocations.getFirst();
					if (layerLocations.size() > 1) {
						sweaterLocation = layerLocations.get(random.nextInt(layerLocations.size()));
					}
					coloredCutoutModelCopyLayerRender(this.model, sweaterLocation, poseStack, nodeCollector,
							packedLight, renderState, -1, renderState.outlineColor);
				}
			}
		}
	}
}