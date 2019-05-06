package cz.zcu.yamanager.util.localization;

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

    public static Language get(String lang) {
        return valueOf(lang.toUpperCase());
    }
}
