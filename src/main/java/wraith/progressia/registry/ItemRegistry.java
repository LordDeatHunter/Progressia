package wraith.progressia.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.registry.Registry;
import wraith.progressia.ProgressiaItemGroups;
import wraith.progressia.ProgressiaToolMaterial;
import wraith.progressia.item.BaseProgressiaSword;
import wraith.progressia.utils.Utils;
import wraith.progressia.item.BloodKatanaItem;
import wraith.progressia.item.WitherBladeItem;

import java.util.HashMap;
import java.util.Map;

public class ItemRegistry {

    private ItemRegistry(){}

    private static final HashMap<String, Item> ITEMS = new HashMap<>();

    public static Item getItem(String id) {
        return ITEMS.getOrDefault(id, Items.AIR);
    }

    private static void loadItems() {

        ITEMS.put("withered_soul", new Item(new FabricItemSettings().group(ProgressiaItemGroups.MISC)));
        ITEMS.put("withered_bone", new Item(new FabricItemSettings().group(ProgressiaItemGroups.MISC)));
        ITEMS.put("withered_blade", new WitherBladeItem(ProgressiaToolMaterial.WITHERED, 6, -2f, new FabricItemSettings().group(ProgressiaItemGroups.WEAPONS)));

        ITEMS.put("empty_vial", new Item(new FabricItemSettings().group(ProgressiaItemGroups.MISC)));
        ITEMS.put("blood_vial", new Item(new FabricItemSettings().group(ProgressiaItemGroups.MISC)));
        ITEMS.put("tortured_soul", new Item(new FabricItemSettings().group(ProgressiaItemGroups.MISC)));
        ITEMS.put("blood_katana", new BloodKatanaItem(ProgressiaToolMaterial.BLOODY, 4, -1.2f, new FabricItemSettings().group(ProgressiaItemGroups.WEAPONS)));

        ITEMS.put("base_progressia_sword", new BaseProgressiaSword(ToolMaterials.IRON, 2, -2f, new FabricItemSettings().group(ProgressiaItemGroups.WEAPONS)));

    }

    private static void registerItems() {

        for (Map.Entry<String, Item> itemEntry : ITEMS.entrySet()) {
            Registry.register(Registry.ITEM, Utils.ID(itemEntry.getKey()), itemEntry.getValue());
        }

    }

    public static void init() {
        loadItems();
        registerItems();
    }

}
