package carpetgcaddition.translation;

import carpet.CarpetSettings;
import carpetgcaddition.delegate.Func;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Translator {
    private static Map<String, Func<Translator>> translatorCreators;
    private static Map<String, Translator> translators;

    private Map<String, String> translations;

    public Translator(Map<String, String> translations) {
        this.translations = translations;
    }

    public Map<String, String> getTranslations() {
        return translations;
    }

    public Optional<String> getTranslation(String key) {
        var t = this.translations.get(key);
        return t == null ? Optional.empty() : Optional.of(t);
    }

    public static void init() {
        translatorCreators = new HashMap<>();
        translators = new HashMap<>();
        translatorCreators.put("en_us", ENTranslator::create);
        translatorCreators.put("zh_cn", ZHCNTranslator::create);
    }

    public static Translator getTranslator(String lang) {
        var translator = translators.get(lang);

        if (translator != null) {
            return translator;
        }

        var creator = translatorCreators.get(lang);
        translator = creator != null ? creator.execute() : null;

        if (translator != null) {
            translators.put(lang, translator);
        }

        return translator;
    }

    public static Translator getTranslator() {
        var translator = getTranslator(CarpetSettings.language);
        if (translator == null) {
            translator = getTranslator("en_us");
        }
        return translator;
    }

    public static String translation(String key) {
        var t = getTranslator().getTranslation(key);
        if (t.isEmpty()) {
            t = getTranslator("en_us").getTranslation(key);
        }
        return t.orElse(null);
    }
}