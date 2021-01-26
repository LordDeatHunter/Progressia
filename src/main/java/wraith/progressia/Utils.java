package wraith.progressia;

import net.minecraft.util.Identifier;

import java.util.Random;

public class Utils {

    private Utils(){}

    public static final Random random = new Random();
    public static int getRandomIntInRange(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }


    public static Identifier ID(String path) {
        return new Identifier(Progressia.MOD_ID, path);
    }

}
