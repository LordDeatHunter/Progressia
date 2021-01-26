package wraith.progressia.mixin;

import net.minecraft.resource.NamespaceResourceManager;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceImpl;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wraith.progressia.Progressia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Mixin(NamespaceResourceManager.class)
public class NamespaceResourceManagerMixin {

    @Inject(method = "getResource(Lnet/minecraft/util/Identifier;)Lnet/minecraft/resource/Resource;", at = @At("HEAD"), cancellable = true)
    public void getResource(Identifier id, CallbackInfoReturnable<Resource> cir) {

        String[] segments = id.getPath().split("/");
        String path = segments[segments.length - 1];

        if (!id.getNamespace().equals(Progressia.MOD_ID) || !path.endsWith(".png")) {
            return;
        }

        File texture = new File("config/progressia/sword_parts/" + path);
        File metadata = new File("config/progressia/sword_parts/" + path + ".mcmeta");
        if (texture.exists()) {
            try {
                cir.setReturnValue(new ResourceImpl(Progressia.MOD_ID, id, new FileInputStream(texture), metadata.exists() ? new FileInputStream(metadata) : null));
                cir.cancel();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

}
