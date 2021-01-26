package wraith.progressia;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import wraith.progressia.utils.Utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ProgressiaClient implements ClientModInitializer {

    public static final HashSet<String> SWORD_PARTS = new HashSet<String>(){{
        add("hilt");
        add("guard");
        add("blade");
    }};
    public static final HashSet<String> RENDERING_PARTS = new HashSet<>();

    @Override
    public void onInitializeClient() {
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            for (Map.Entry<Integer, HashMap<String, HashSet<Integer>>> entry : Progressia.PART_AMOUNT.entrySet()) {
                for (String part : SWORD_PARTS) {
                    for (int variant : entry.getValue().get(part)) {
                        String id;

                        id = "t" + entry.getKey() + "_v" + variant + "_sword_" + part;
                        RENDERING_PARTS.add(id);
                        out.accept(Utils.inventoryModelID(id));
                    }
                }
            }
        });

        ModelLoadingRegistry.INSTANCE.registerVariantProvider(rm -> new ItemModelProvider());
    }

}
