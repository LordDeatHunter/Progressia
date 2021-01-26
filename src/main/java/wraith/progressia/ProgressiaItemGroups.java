package wraith.progressia;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import wraith.progressia.registry.ItemRegistry;
import wraith.progressia.utils.Utils;

public class ProgressiaItemGroups {

    public static final ItemGroup WEAPONS = FabricItemGroupBuilder.create(Utils.ID("weapons")).icon(() -> new ItemStack(ItemRegistry.getItem("withered_blade"))).build();
    public static final ItemGroup MISC = FabricItemGroupBuilder.create(Utils.ID("misc")).icon(() -> new ItemStack(ItemRegistry.getItem("withered_soul"))).build();

}
