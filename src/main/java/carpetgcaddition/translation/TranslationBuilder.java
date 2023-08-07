package carpetgcaddition.translation;

import java.util.Map;

class TranslationBuilder {
    private Map<String, String> map;

    public TranslationBuilder(Map<String, String> map) {
        this.map = map;
    }

    public RuleBuilder rule(String ruleName) {
        return new RuleBuilder(ruleName, map);
    }

    public void message(String key, String value) {
        map.put(key, value);
    }
}
