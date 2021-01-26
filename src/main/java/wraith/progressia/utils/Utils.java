package wraith.progressia.utils;

import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import wraith.progressia.Progressia;

import java.io.File;
import java.util.*;

public class Utils {

    private Utils(){}

    public static final Random random = new Random();
    public static int getRandomIntInRange(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }


    public static Identifier ID(String path) {
        return new Identifier(Progressia.MOD_ID, path);
    }

    public static boolean isGeneratedWeapon(String path) {
        return path.startsWith("base_progressia_");
    }

    public static ModelIdentifier inventoryModelID(String id) {
        return new ModelIdentifier(ID(id), "inventory");
    }

    public static HashMap<Integer, HashMap<String, HashSet<Integer>>> getPartAmount(String partFolder) {
        HashMap<Integer, HashMap<String, HashSet<Integer>>> map = new HashMap<>();
        File[] files = FileUtils.getFiles("config/progressia/" + partFolder + "/");
        if (files == null) {
            return map;
        }
        for (File file : files) {
            String[] segments = file.getName().split("/");
            String name = segments[segments.length - 1];
            segments = name.split("_");
            int type = Integer.parseInt(segments[0].substring(1));
            int variant = Integer.parseInt(segments[1].substring(1));
            if (!map.containsKey(type)) {
                map.put(type, new HashMap<>());
            }
            String part = segments[segments.length - 1].split("\\.")[0];
            if (!map.get(type).containsKey(part)) {
                map.get(type).put(part, new HashSet<>());
            }
            map.get(type).get(part).add(variant);
        }
        return map;
    }

    public static void addRandomWeaponNBT(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateSubTag("Parts");
        HashMap<Integer, HashMap<String, HashSet<Integer>>> parts = Progressia.PART_AMOUNT;
        int type = getRandomIntInRange(1, parts.size() - 1);
        int variant = getRandomIntFromHashSet(parts.get(type).get("blade"));
        tag.putInt("Type", type);
        tag.putInt("HeadVariant", variant);

        variant = getRandomIntFromHashSet(parts.get(type).get("guard"));
        tag.putInt("BindingVariant", variant);

        variant = getRandomIntFromHashSet(parts.get(type).get("hilt"));
        tag.putInt("HandleVariant", variant);

        tag.putString("Head", "blade");
        tag.putString("Binding", "guard");
        tag.putString("Handle", "hilt");
    }

    public static int getRandomIntFromHashSet(HashSet<Integer> set) {
        ArrayList<Integer> list = new ArrayList<>(set);
        Collections.sort(list);
        return list.get(getRandomIntInRange(0, list.size() - 1));
    }

    public static String createModelJson(String path, String parent) {
        String[] segments = path.split("/");
        path = segments[segments.length - 1];
        return "{\n" +
                "  \"parent\": \"" + parent + "\",\n" +
                "  \"textures\": {\n" +
                "    \"layer0\": \"" + Progressia.MOD_ID + ":item/" + path + "\"\n" +
                "  }\n" +
                "}";
    }

}
