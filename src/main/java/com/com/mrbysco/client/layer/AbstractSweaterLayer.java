package com.com.mrbysco.client.layer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public abstract class AbstractSweaterLayer<S extends LivingEntityRenderState, M extends EntityModel<S>> extends RenderLayer<S, M> {
	public AbstractSweaterLayer(RenderLayerParent<S, M> renderLayerParent) {
		super(renderLayerParent);
	}
}