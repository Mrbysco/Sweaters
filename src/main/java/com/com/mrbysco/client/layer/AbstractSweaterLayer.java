package com.com.mrbysco.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractSweaterLayer<S extends LivingEntityRenderState, M extends EntityModel<S>> extends RenderLayer<S, M> {
	public AbstractSweaterLayer(RenderLayerParent<S, M> renderLayerParent) {
		super(renderLayerParent);
	}

	protected static <S extends HumanoidRenderState> void coloredCutoutHumanoidModelCopyLayerRender(
			HumanoidModel<S> model, HumanoidModel<S> model2,
			ResourceLocation sweaterLocation, PoseStack poseStack,
			MultiBufferSource bufferSource,
			int packedLightIn, S renderState,
			int color
	) {

		if (!renderState.isInvisible) {
			model.copyPropertiesTo(model2);
			model2.setupAnim(renderState);
			model2.head.copyFrom(model.head);
			model2.hat.copyFrom(model.hat);
			model2.body.copyFrom(model.body);
			model2.rightArm.copyFrom(model.rightArm);
			model2.leftArm.copyFrom(model.leftArm);
			model2.rightLeg.copyFrom(model.rightLeg);
			model2.leftLeg.copyFrom(model.leftLeg);
			renderColoredCutoutModel(model2, sweaterLocation, poseStack, bufferSource, packedLightIn, renderState, color);
		}
	}
}