package earth.terrarium.heracles.client;

import com.google.common.base.Charsets;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import earth.terrarium.heracles.Heracles;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.Util;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;

import static com.mojang.text2speech.Narrator.LOGGER;

public class DisplayNameMappings {
    public static final Object2ObjectMap<String, String> MAPPING = new Object2ObjectOpenHashMap<>();

    public static void load() {
        Util.ioPool().execute(DisplayNameMappings::loadSync);
    }

    public static synchronized void loadSync() {
        BufferedReader reader = null;
        try {
            var path = Heracles.getConfigPath().resolve(Heracles.MOD_ID).resolve("group_mappings.json");
            if (Files.isRegularFile(path)) {
                reader = new BufferedReader(new InputStreamReader(Files.newInputStream(path), Charsets.UTF_8));
                loadData(new GsonBuilder().setPrettyPrinting().create().fromJson(reader, JsonObject.class));
            }
        } catch (Exception exception) {
            LOGGER.error("Exception reading group mappings", exception);
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    public static void loadData(JsonObject object) {
        var map = MAPPING;
        map.clear();
        try {
            object.entrySet().forEach(entry -> map.put(entry.getKey(), entry.getValue().getAsString()));
        } catch (Exception ignored) {}
    }
}
