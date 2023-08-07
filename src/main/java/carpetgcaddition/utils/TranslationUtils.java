package carpetgcaddition.utils;

public abstract class TranslationUtils {
    public static String getRuleKey(String ruleName, String target) {
        return "carpet.rule.%s.%s".formatted(ruleName, target);
    }

    public static String getFullMessageKey(String key) {
        return "carpetgcaddition.message.%s".formatted(key);
    }
}
