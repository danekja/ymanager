package cz.zcu.yamanager.util.localization;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

public final class Message {

    private static Map<Language, ResourceBundle> BUNDLES;

    static {
        BUNDLES = new HashMap<>();

        config().addLanguage(Language.EN);
    }

    public interface Config {
        Config addLanguage(Language language);
    }

    public static Config config() {
        return new Config() {

            @Override
            public Config addLanguage(Language language) {
                if (!BUNDLES.containsKey(language)) {
                    BUNDLES.put(language, getBundle("Message", language.getLocale()));
                }
                return this;
            }
        };
    }

    public static String getString(Language language, String key) {
        if (!BUNDLES.containsKey(language)) return language.name()+" SOURCE MISSING";
        return BUNDLES.get(language).getString(key);
    }

    public static String getString(String key) {
        return getString(Language.EN, key);
    }
}
