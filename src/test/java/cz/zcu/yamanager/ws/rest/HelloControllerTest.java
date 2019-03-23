package cz.zcu.yamanager.ws.rest;

import cz.zcu.yamanager.util.localization.Language;
import cz.zcu.yamanager.util.localization.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloControllerTest {

    private HelloController helloController;

    @BeforeEach
    void setUp() {
        Message.config()
                .addLanguage(Language.EN)
                .addLanguage(Language.CZ);

        helloController = new HelloController(null);
    }

    @Test
    void hello_inCzech_true() {
        assertEquals("český jazyk", helloController.hello("cz"));
    }

    @Test
    void hello_inEnglish_true() {
        assertEquals("english language", helloController.hello("en"));
    }
}