package com.com.mrbysco.config;

import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Objects;

public final class SweaterInfo {
	private String mobType;
	private List<ResourceLocation> entities;
	private List<ResourceLocation> textures;

	public SweaterInfo(String mobType, List<ResourceLocation> entities, List<ResourceLocation> textures) {
		this.mobType = mobType;
		this.entities = entities;
		this.textures = textures;
	}

	public String mobType() {
		return mobType;
	}

	public List<ResourceLocation> entities() {
		return entities;
	}

	public List<ResourceLocation> textures() {
		return textures;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (SweaterInfo) obj;
		return Objects.equals(this.mobType, that.mobType) &&
				Objects.equals(this.entities, that.entities) &&
				Objects.equals(this.textures, that.textures);
	}

	@Override
	public int hashCode() {
		return Objects.hash(mobType, entities, textures);
	}

	@Override
	public String toString() {
		return "SweaterInfo[" +
				"mobType=" + mobType + ", " +
				"entities=" + entities + ", " +
				"textures=" + textures + ']';
	}

}