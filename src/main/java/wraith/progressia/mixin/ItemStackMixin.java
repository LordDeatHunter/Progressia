package wraith.progressia.mixin;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wraith.progressia.registry.ItemRegistry;
import wraith.progressia.utils.Utils;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "<init>(Lnet/minecraft/item/ItemConvertible;I)V", at = @At("TAIL"))
    public void constructor(ItemConvertible item, int count, CallbackInfo ci) {
        if (item != null && item.asItem() == ItemRegistry.getItem("base_progressia_sword")) {
            Utils.addRandomWeaponNBT(((ItemStack)(Object)this));
        }
    }


}
