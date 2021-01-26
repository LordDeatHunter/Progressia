package wraith.progressia.mixin;

import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wraith.progressia.Progressia;
import wraith.progressia.utils.Utils;

@Mixin(ModelLoader.class)
public class ModelLoaderMixin {

    @Inject(method = "loadModelFromJson", at = @At("HEAD"), cancellable = true)
    public void loadModelFromJson(Identifier id, CallbackInfoReturnable<JsonUnbakedModel> cir) {
        if (!id.getNamespace().equals(Progressia.MOD_ID)) {
            return;
        }
        String path = id.getPath().split("/")[1];
        String[] segments = path.split("_");
        if (segments[0].charAt(0) != 't' || segments[1].charAt(0) != 'v' || segments.length < 3) {
            return;
        }
        String json = Utils.createModelJson(id.getPath(), "minecraft:item/handheld");
        JsonUnbakedModel model = JsonUnbakedModel.deserialize(json);
        model.id = id.toString();
        cir.setReturnValue(model);
        cir.cancel();
    }

}
