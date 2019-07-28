package org.danekja.ymanager.util.localization;

import java.util.Locale;

public enum  Language {

    EN  (Locale.ENGLISH),
    CZ  (new Locale("cs", "")),

    ;
    private Locale locale;
    Language(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public static Language getLanguageOrDefault(String lang, Language defaultLang) {
        try {
            return lang != null ? valueOf(lang.toUpperCase()) : defaultLang;
        } catch (IllegalArgumentException | NullPointerException e) {
            return defaultLang;
        }
    }

    public static Language getLanguage(String lang) {
        return getLanguageOrDefault(lang, EN);
    }
}
