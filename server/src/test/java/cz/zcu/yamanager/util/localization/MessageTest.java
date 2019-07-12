package cz.zcu.yamanager.util.localization;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageTest {

    @AfterEach
    void tearDown() {
        Message.BUNDLES.clear();
    }

    @Test
    void getString_inCzech_true() {
        Message.config().addLanguage(Language.CZ);

        assertEquals("český jazyk", Message.getString(Language.CZ, "hello"));
    }

    @Test
    void getString_inEnglish_true() {
        Message.config().addLanguage(Language.EN);

        assertEquals("english language", Message.getString(Language.EN, "hello"));
    }

    @Test
    void getString_general_true() {
        assertEquals("english language", Message.getString("hello"));
    }

    @Test
    void getString_multiple_true() {
        Message.config()
                .addLanguage(Language.CZ)
                .addLanguage(Language.EN);

        assertEquals("český jazyk", Message.getString(Language.CZ, "hello"));
        assertEquals("english language", Message.getString(Language.EN, "hello"));
        assertEquals("english language", Message.getString("hello"));
    }

    @Test
    void getString_missingSource_true() {
        assertEquals("CZ SOURCE MISSING", Message.getString(Language.CZ, "hello"));
    }
}