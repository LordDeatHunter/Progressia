package wraith.progressia;

import java.util.HashSet;

public class ProgressFlags {

    private ProgressFlags(){}

    private static final HashSet<String> PROGRESS = new HashSet<>();

    public static boolean isUnlocked(String progress) {
        return PROGRESS.contains(progress);
    }

    public static void unlock(String progress) {
        PROGRESS.add(progress);
    }

    public static void forget(String progress) {
        PROGRESS.remove(progress);
    }

    public static int getProgressions() {
        return PROGRESS.size();
    }

}
