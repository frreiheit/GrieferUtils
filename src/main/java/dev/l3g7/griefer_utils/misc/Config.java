package dev.l3g7.griefer_utils.misc;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.l3g7.griefer_utils.util.IOUtil;
import net.minecraft.client.Minecraft;

import java.io.File;

public class Config {

    public static boolean has(String path) {
        if (path == null)
            return false;

        String[] parts = path.split("\\.");
        return resolve(parts).has(parts[parts.length - 1]);
    }

    public static JsonElement get(String path) {
        String[] parts = path.split("\\.");
        return resolve(parts).get(parts[parts.length - 1]);
    }

    public static void set(String path, JsonElement val) {
        String[] parts = path.split("\\.");
        resolve(parts).add(parts[parts.length - 1], val);
    }

    public static void set(String path, Boolean val) {
        set(path, val == null ? JsonNull.INSTANCE : new JsonPrimitive(val));
    }

    public static void set(String path, Integer val) {
        set(path, val == null ? JsonNull.INSTANCE : new JsonPrimitive(val));
    }

    public static void set(String path, Long val) {
        set(path, val == null ? JsonNull.INSTANCE : new JsonPrimitive(val));
    }

	public static void set(String path, Double val) {
		set(path, val == null ? JsonNull.INSTANCE : new JsonPrimitive(val));
	}

    public static void set(String path, String val) {
        set(path, val == null ? JsonNull.INSTANCE : new JsonPrimitive(val));
    }

    private static JsonObject resolve(String[] parts) {
        JsonObject o = loadConfig();
        for (int i = 0; i < parts.length - 1; i++) {
            if (!o.has(parts[i]) || !(o.get(parts[i]).isJsonObject()))
                o.add(parts[i], new JsonObject());
            o = o.get(parts[i]).getAsJsonObject();
        }
        return o;
    }

    private static final File configFile = new File(new File(Minecraft.getMinecraft().mcDataDir, "config"), "GrieferUtils.json");
    private static JsonObject config = null;

    public static void save() {
        if (config == null)
            config = new JsonObject();
        IOUtil.file(configFile).writeJson(config);
    }

    private static JsonObject loadConfig() {
        if (config == null) {
            IOUtil.file(configFile)
                    .readJsonObject(v -> config = v)
                    .orElse(t -> config = new JsonObject());
			patchConfig();
        }
        return config;
    }

	/**
	 * Resets Cooldown notification and OrbStats if config is under v1.11.2
	 */
	private static void patchConfig() {
		if (!config.has("version") || config.get("version").getAsString().equals("DEBUG"))
			return;

		String[] parts = config.get("version").getAsString().split("\\.");
		int minorVer = Integer.parseInt(parts[1]);
		if (minorVer > 11 || (parts.length == 3 && minorVer == 11 && Integer.parseInt(parts[2]) > 1))
			return;

		resolve("features.cooldown_notifications.end_dates".split("\\.")).remove("end_dates");
		resolve("modules.orb_stats.stats".split("\\.")).remove("stats");
	}

}