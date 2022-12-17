package com.com.mrbysco.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class SweaterLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	private final M model;
	private final List<ResourceLocation> layerLocations = new ArrayList<>();

	public SweaterLayer(RenderLayerParent<T, M> layerParent, Supplier<M> model, List<ResourceLocation> layerLocations) {
		super(layerParent);
		this.model = model.get();
		this.layerLocations.clear();
		this.layerLocations.addAll(layerLocations);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLightIn, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		final Random random = new Random(livingEntity.hashCode());
		if (random.nextBoolean() && !layerLocations.isEmpty()) {
			ResourceLocation sweaterLocation = layerLocations.get(0);
			if (layerLocations.size() > 1) {
				sweaterLocation = layerLocations.get(random.nextInt(layerLocations.size()));
			}
			coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, sweaterLocation, poseStack, bufferSource,
					packedLightIn, livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
					partialTicks, 1.0F, 1.0F, 1.0F);
		}
	}

}