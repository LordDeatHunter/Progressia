package wraith.progressia.registry;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;
import wraith.progressia.Utils;

import java.util.HashMap;
import java.util.Map;

public class SoundRegistry {

    private SoundRegistry(){}

    private static final HashMap<String, SoundEvent> SOUNDS = new HashMap<>();

    public static SoundEvent getSound(String id) {
        return SOUNDS.getOrDefault(id, null);
    }

    private static void loadSounds() {

        SOUNDS.put("ambient.progressia.lifesteal", new SoundEvent(Utils.ID("ambient.progressia.lifesteal")));
        SOUNDS.put("ambient.progressia.wither", new SoundEvent(Utils.ID("ambient.progressia.wither")));

    }

    private static void registerSounds() {

        for (Map.Entry<String, SoundEvent> itemEntry : SOUNDS.entrySet()) {
            Registry.register(Registry.SOUND_EVENT, Utils.ID(itemEntry.getKey()), itemEntry.getValue());
        }

    }

    public static void init() {
        loadSounds();
        registerSounds();
    }

}
