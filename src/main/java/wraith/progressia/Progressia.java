package wraith.progressia;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wraith.progressia.registry.ItemRegistry;
import wraith.progressia.registry.SoundRegistry;
import wraith.progressia.utils.FileUtils;
import wraith.progressia.utils.Utils;

import java.util.HashMap;
import java.util.HashSet;

public class Progressia implements ModInitializer {

    public static HashMap<Integer, HashMap<String, HashSet<Integer>>> PART_AMOUNT = new HashMap<>();

    public static final String MOD_ID = "progressia";
    public static final String MOD_NAME = "Progressia";
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        FileUtils.saveFilesFromJar("configs/sword_parts", "sword_parts", false);
        PART_AMOUNT = Utils.getPartAmount("sword_parts");

        ItemRegistry.init();
        SoundRegistry.init();
        LOGGER.info("[" + MOD_NAME + "] has been initiated.");
    }

}
