package wraith.progressia;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wraith.progressia.registry.ItemRegistry;
import wraith.progressia.registry.SoundRegistry;

public class Progressia implements ModInitializer {

    public static final String MOD_ID = "progressia";
    public static final String MOD_NAME = "Progressia";
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        ItemRegistry.init();
        SoundRegistry.init();
        LOGGER.info("[" + MOD_NAME + "] has been initiated.");
    }

}
