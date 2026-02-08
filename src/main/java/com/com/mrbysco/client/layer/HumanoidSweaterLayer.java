package com.com.mrbysco.client.layer;

import com.com.mrbysco.client.ClientHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class HumanoidSweaterLayer<T extends HumanoidRenderState, M extends HumanoidModel<T>> extends AbstractSweaterLayer<T, M> {
	private final M model;
	private final List<Identifier> layerLocations = new ArrayList<>();

	public HumanoidSweaterLayer(RenderLayerParent<T, M> layerParent, Supplier<M> model, List<Identifier> layerLocations) {
		super(layerParent);
		this.model = model.get();
		this.layerLocations.clear();
		this.layerLocations.addAll(layerLocations);
	}

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight,
	                   T renderState, float yRot, float xRot) {
		final Random random = renderState.getRenderData(ClientHandler.SWEATER_RANDOM);
		if (random != null && random.nextBoolean() && !layerLocations.isEmpty()) {
			Identifier sweaterLocation = layerLocations.getFirst();
			if (layerLocations.size() > 1) {
				sweaterLocation = layerLocations.get(random.nextInt(layerLocations.size()));
			}
			this.model.setupAnim(renderState);
			coloredCutoutModelCopyLayerRender(this.model, sweaterLocation, poseStack, nodeCollector,
					packedLight, renderState, -1, renderState.outlineColor);
		}
	}
}