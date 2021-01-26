package wraith.progressia.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.network.packet.s2c.play.PlaySoundIdS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import wraith.progressia.utils.Utils;

public class BloodKatanaItem extends SwordItem {

    public BloodKatanaItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHit(stack, target, attacker);
        if (attacker.getHealth() < attacker.getMaxHealth() && Utils.getRandomIntInRange(1, 100) <= 20) {
            attacker.heal(1);
            if (attacker instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity)attacker).networkHandler.sendPacket(new PlaySoundIdS2CPacket(Utils.ID("ambient.progressia.lifesteal"), SoundCategory.AMBIENT, attacker.getPos(), 1f, 1f));
            }
        }
        return true;
    }

}
