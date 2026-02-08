package com.com.mrbysco.client.layer;

import com.com.mrbysco.client.ClientHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class SweaterLayer<S extends LivingEntityRenderState, M extends EntityModel<S>> extends RenderLayer<S, M> {
	private final M model;
	private final List<Identifier> layerLocations = new ArrayList<>();

	public SweaterLayer(RenderLayerParent<S, M> layerParent, Supplier<M> model, List<Identifier> layerLocations) {
		super(layerParent);
		this.model = model.get();
		this.layerLocations.clear();
		this.layerLocations.addAll(layerLocations);
	}

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight,
	                   S renderState, float yRot, float xRot) {
		final Random random = renderState.getRenderData(ClientHandler.SWEATER_RANDOM);
		if (random != null && random.nextBoolean() && !layerLocations.isEmpty()) {
			Identifier sweaterLocation = layerLocations.getFirst();
			if (layerLocations.size() > 1) {
				sweaterLocation = layerLocations.get(random.nextInt(layerLocations.size()));
			}
			if (renderState.isBaby) {
				poseStack.scale(0.6125F, 0.6125F, 0.6125F);
				poseStack.translate(0, 1.0625F, 0);
			}
			this.model.setupAnim(renderState);
			coloredCutoutModelCopyLayerRender(this.model, sweaterLocation, poseStack, nodeCollector,
					packedLight, renderState, -1, renderState.outlineColor);
		}
	}
}