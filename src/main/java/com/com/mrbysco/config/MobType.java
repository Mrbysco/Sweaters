package com.com.mrbysco.config;

import com.com.mrbysco.client.ClientHandler;
import net.minecraft.client.model.geom.ModelLayerLocation;
import org.jetbrains.annotations.Nullable;

public enum MobType {
	NONE("none", null),
	HUMANOID("humanoid", ClientHandler.HUMANOID_SWEATER_LAYER),
	HUMANOID_EXTENDED("humanoid_extended", ClientHandler.HUMANOID_EXTENDED_SWEATER_LAYER),
	PLAYER("player", ClientHandler.PLAYER_SWEATER_LAYER),
	CREEPER("creeper", ClientHandler.CREEPER_SWEATER_LAYER),
	CHICKEN("chicken", ClientHandler.CHICKEN_SWEATER_LAYER),
	SLIME("slime", ClientHandler.SLIME_SWEATER_LAYER),
	WOLF("wolf", ClientHandler.WOLF_SWEATER_LAYER);

	private final String name;
	private final ModelLayerLocation modelLayerLocation;

	MobType(String name, ModelLayerLocation modelLayerLocation) {
		this.name = name;
		this.modelLayerLocation = modelLayerLocation;
	}

	public String getMobType() {
		return name;
	}

	public ModelLayerLocation getModelLayerLocation() {
		return modelLayerLocation;
	}

	@Nullable
	public static MobType getByName(@Nullable String value) {
		for (MobType sweaterType : values()) {
			if (sweaterType.name.equals(value)) {
				return sweaterType;
			}
		}
		return NONE;
	}
}
