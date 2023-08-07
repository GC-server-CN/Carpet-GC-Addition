package carpetgcaddition.translation;

import carpetgcaddition.utils.TranslationUtils;

import java.util.Map;

class RuleBuilder {
    private String ruleName;
    private Map<String, String> map;

    private int extraCount;

    public RuleBuilder(String ruleName, Map<String, String> map) {
        this.ruleName =ruleName;
        this.map = map;
    }

    public RuleBuilder name(String str) {
        map.put(createKey("name"), str);
        return this;
    }

    public RuleBuilder desc(String str) {
        map.put(createKey("desc"), str);
        return this;
    }

    public RuleBuilder extra(String str) {
        map.put(createKey("extra.%d".formatted(extraCount)), str);
        extraCount++;
        return this;
    }

    private String createKey(String target) {
        return TranslationUtils.getRuleKey(ruleName, target);
    }
}
