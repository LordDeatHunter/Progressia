package wraith.progressia;

import com.google.common.base.Charsets;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resource.Resource;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;
import wraith.progressia.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class ItemBakedModel implements BakedModel, UnbakedModel, FabricBakedModel {

    private static final HashMap<String, FabricBakedModel> PART_MODELS = new HashMap<>();

    @Override
    public void emitItemQuads(ItemStack itemStack, Supplier<Random> supplier, RenderContext context) {

        int type = 1;
        int headVariant = 1;
        int bindingVariant = 1;
        int handleVariant = 1;
        String head = "blade";
        String binding = "guard";
        String handle = "hilt";

        CompoundTag partTag = itemStack.getSubTag("Parts");
        if (partTag != null) {
            type = partTag.getInt("Type");

            headVariant = partTag.getInt("HeadVariant");
            bindingVariant = partTag.getInt("BindingVariant");
            handleVariant = partTag.getInt("HandleVariant");

            head = partTag.getString("Head");
            binding = partTag.getString("Binding");
            handle = partTag.getString("Handle");
        }
        String weaponType = "sword";

        head = "t" + type + "_v" + headVariant + "_" + weaponType + "_" + head;
        binding = "t" + type + "_v" + bindingVariant + "_" + weaponType + "_" + binding;
        handle = "t" + type + "_v" + handleVariant + "_" + weaponType + "_" + handle;

        PART_MODELS.get(head).emitItemQuads(null, null, context);
        PART_MODELS.get(binding).emitItemQuads(null, null, context);
        PART_MODELS.get(handle).emitItemQuads(null, null, context);
    }

    public static ModelTransformation loadTransformFromJson(Identifier location) {
        try {
            return JsonUnbakedModel.deserialize(getReaderForResource(location)).getTransformations();
        } catch (IOException exception) {
            Progressia.LOGGER.warn("Can't load resource " + location);
            exception.printStackTrace();
            return null;
        }
    }

    public static Reader getReaderForResource(Identifier location) throws IOException {
        Identifier file = new Identifier(location.getNamespace(), location.getPath() + ".json");
        Resource resource = MinecraftClient.getInstance().getResourceManager().getResource(file);
        return new BufferedReader(new InputStreamReader(resource.getInputStream(), Charsets.UTF_8));
    }

    @Override
    public @Nullable BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        if (PART_MODELS.isEmpty()) {
            for(String id : ProgressiaClient.RENDERING_PARTS) {
                PART_MODELS.put(id, (FabricBakedModel) loader.bake(Utils.inventoryModelID(id), ModelRotation.X0_Y0));
            }
        }
        return this;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return Collections.emptyList();
    }

    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
        return Collections.emptyList();
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext renderContext) {}

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return Collections.emptyList();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean hasDepth() {
        return false;
    }

    @Override
    public boolean isSideLit() {
        return false;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public Sprite getSprite() {
        return MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(new Identifier("block/cobblestone"));
    }

    @Override
    public ModelTransformation getTransformation() {
        return loadTransformFromJson(new Identifier("minecraft:models/item/handheld"));
    }

    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

}
