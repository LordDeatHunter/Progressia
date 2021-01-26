package wraith.progressia;

import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelVariantProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import org.jetbrains.annotations.Nullable;
import wraith.progressia.utils.Utils;

public class ItemModelProvider implements ModelVariantProvider {

    @Override
    public @Nullable UnbakedModel loadModelVariant(ModelIdentifier modelId, ModelProviderContext context) {
        if(modelId.getNamespace().equals(Progressia.MOD_ID) && Utils.isGeneratedWeapon(modelId.getPath())) {
            return new ItemBakedModel();
        }
        return null;
    }

}
