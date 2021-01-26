package wraith.progressia.item;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class BaseProgressiaSword extends SwordItem {

    public BaseProgressiaSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

}
