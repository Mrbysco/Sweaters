package com.com.mrbysco.config;

import com.com.mrbysco.Sweaters;
import com.com.mrbysco.client.ClientHandler;
import com.com.mrbysco.client.helper.TextureHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigHandler {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	public static final File JSON_DIR = new File(FMLPaths.CONFIGDIR.get().toFile() + "/sweaters");

	public static final Map<MobType, SweaterInfo> SWEATER_ENTRIES = new HashMap<>();

	public static final List<ResourceLocation> HUMANOID_ENTITIES = List.of(
			new ResourceLocation("zombie"),
			new ResourceLocation("husk"),
			new ResourceLocation("skeleton"),
			new ResourceLocation("wither_skeleton"),
			new ResourceLocation("stray"),
			new ResourceLocation("husk"),
			new ResourceLocation("piglin"),
			new ResourceLocation("piglin_brute"),
			new ResourceLocation("zombified_piglin")
	);

	public static final List<ResourceLocation> PLAYER_ENTITIES = List.of(
			new ResourceLocation("player"),
			new ResourceLocation("player_mobs", "player_mob"),
			new ResourceLocation("statues", "player_statue")
	);

	public static final List<ResourceLocation> HUMANOID_EXTENDED_ENTITIES = List.of(
			new ResourceLocation("drowned")
	);
	public static final List<ResourceLocation> CREEPER_ENTITIES = List.of(
			new ResourceLocation("creeper")
	);
	public static final List<ResourceLocation> CHICKEN_ENTITIES = List.of(
			new ResourceLocation("chicken")/*,
			new ResourceLocation("turkeydevutil", "turkey")*/
	);
	public static final List<ResourceLocation> SLIME_ENTITIES = List.of(
			new ResourceLocation("slime"),
			new ResourceLocation("magma_cube")
	);
	public static final List<ResourceLocation> WOLF_ENTITIES = List.of(
			new ResourceLocation("wolf")
	);

	public static void initializeConfig() {
		final SweaterInfo humanoid = new SweaterInfo("humanoid", HUMANOID_ENTITIES, TextureHelper.HUMANOID_SWEATER_TEXTURES);
		final SweaterInfo player = new SweaterInfo("player", PLAYER_ENTITIES, List.of());
		final SweaterInfo humanoid_extended = new SweaterInfo("humanoid_extended", HUMANOID_EXTENDED_ENTITIES, TextureHelper.HUMANOID_SWEATER_TEXTURES);
		final SweaterInfo creeper = new SweaterInfo("creeper", CREEPER_ENTITIES, TextureHelper.CREEPER_SWEATER_TEXTURES);
		final SweaterInfo chicken = new SweaterInfo("chicken", CHICKEN_ENTITIES, TextureHelper.CHICKEN_SWEATER_TEXTURES);
		final SweaterInfo slime = new SweaterInfo("slime", SLIME_ENTITIES, TextureHelper.SLIME_SWEATER_TEXTURES);
		final SweaterInfo wolf = new SweaterInfo("wolf", WOLF_ENTITIES, TextureHelper.WOLF_SWEATER_TEXTURES);
		if (!JSON_DIR.exists()) {
			if (JSON_DIR.mkdir()) {
				List<SweaterInfo> sweaterList = new ArrayList<>();
				sweaterList.add(player);
				sweaterList.add(humanoid);
				sweaterList.add(humanoid_extended);
				sweaterList.add(creeper);
				sweaterList.add(chicken);
				sweaterList.add(slime);
				sweaterList.add(wolf);

				for (SweaterInfo info : sweaterList) {
					try (FileWriter writer = new FileWriter(new File(JSON_DIR, info.mobType() + ".json"))) {
						GSON.toJson(info, writer);
						writer.flush();
					} catch (IOException e) {
						Sweaters.LOGGER.trace("Exception writing to file: ", e);
					}
				}
			}
		} else {
			for (MobType type : MobType.values()) {
				if (type == MobType.NONE) continue;

				File file = new File(JSON_DIR, type.getMobType() + ".json");
				if (!file.exists()) {
					try (FileWriter writer = new FileWriter(file)) {
						switch (type) {
							case PLAYER -> GSON.toJson(player, writer);
							case HUMANOID -> GSON.toJson(humanoid, writer);
							case HUMANOID_EXTENDED -> GSON.toJson(humanoid_extended, writer);
							case CREEPER -> GSON.toJson(creeper, writer);
							case CHICKEN -> GSON.toJson(chicken, writer);
							case SLIME -> GSON.toJson(slime, writer);
							case WOLF -> GSON.toJson(wolf, writer);
						}
						writer.flush();
					} catch (IOException e) {
						Sweaters.LOGGER.trace("Exception writing to file: ", e);
					}
				}
			}
		}

		SWEATER_ENTRIES.clear();
		ClientHandler.LAYER_LOCATION_MAP.clear();

		File[] files = JSON_DIR.listFiles();
		if (files != null) {
			for (final File file : files) {
				final String fileName = file.getName();
				if (fileName.endsWith(".json")) {
					try (FileReader json = new FileReader(file)) {
						final SweaterInfo info = GSON.fromJson(json, SweaterInfo.class);
						if (info != null) {
							MobType type = MobType.getByName(info.mobType());
							if (type == MobType.NONE) {
								Sweaters.LOGGER.error("Invalid type {} for sweater info {}.", info.mobType(), fileName);
							} else {
								if (SWEATER_ENTRIES.containsKey(type)) {
									Sweaters.LOGGER.error("Duplicate mobType, a file using {} was already loaded. Ignoring {}", info.mobType(), fileName);
								} else {
									SWEATER_ENTRIES.put(type, info);
								}
							}
						} else {
							Sweaters.LOGGER.error("Could not load sweater info from {}.", fileName);
						}
					} catch (final Exception e) {
						Sweaters.LOGGER.error("Unable to load file {}. Please make sure it's a valid json.", fileName);
						Sweaters.LOGGER.trace("Exception: ", e);
					}
				} else {
					Sweaters.LOGGER.error("Found invalid file {} in the sweaters config folder. It must be a .json file!", fileName);
				}
			}
		}

		for (Map.Entry<MobType, SweaterInfo> entry : SWEATER_ENTRIES.entrySet()) {
			MobType mobType = entry.getKey();
			SweaterInfo info = entry.getValue();
			if (mobType != null) {
				for (ResourceLocation entityLocation : info.entities()) {
					if (!ClientHandler.LAYER_LOCATION_MAP.containsKey(entityLocation)) {
						ClientHandler.LAYER_LOCATION_MAP.put(entityLocation,
								new ClientHandler.LayerInfo(mobType, info.textures()));
					}
				}
			}
		}
	}
}