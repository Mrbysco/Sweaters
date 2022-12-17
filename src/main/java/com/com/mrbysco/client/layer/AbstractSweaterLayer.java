package com.com.mrbysco.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public abstract class AbstractSweaterLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	public AbstractSweaterLayer(RenderLayerParent<T, M> renderLayerParent) {
		super(renderLayerParent);
	}

	protected static <T extends LivingEntity> void coloredCutoutHumanoidModelCopyLayerRender(
			HumanoidModel<T> model, HumanoidModel<T> model2,
			ResourceLocation sweaterLocation, PoseStack poseStack,
			MultiBufferSource bufferSource,
			int packedLightIn, T livingEntity,
			float limbSwing, float limbSwingAmount,
			float ageInTicks, float netHeadYaw,
			float headPitch, float partialTicks,
			float red, float blue, float green) {

		if (!livingEntity.isInvisible()) {
			model.copyPropertiesTo(model2);
			model2.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
			model2.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			model2.head.copyFrom(model.head);
			model2.hat.copyFrom(model.hat);
			model2.body.copyFrom(model.body);
			model2.rightArm.copyFrom(model.rightArm);
			model2.leftArm.copyFrom(model.leftArm);
			model2.rightLeg.copyFrom(model.rightLeg);
			model2.leftLeg.copyFrom(model.leftLeg);
			renderColoredCutoutModel(model2, sweaterLocation, poseStack, bufferSource, packedLightIn, livingEntity, red, blue, green);
		}
	}
}
