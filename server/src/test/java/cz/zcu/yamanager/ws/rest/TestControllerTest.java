package cz.zcu.yamanager.ws.rest;

import cz.zcu.yamanager.util.localization.Language;
import cz.zcu.yamanager.util.localization.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestControllerTest {

    private TestController testController;

    @BeforeEach
    void setUp() {
        Message.config()
                .addLanguage(Language.EN)
                .addLanguage(Language.CZ);

        testController = new TestController(null);
    }

    @Test
    void hello_inCzech_true() {
        assertEquals("český jazyk", testController.hello("cz"));
    }

    @Test
    void hello_inEnglish_true() {
        assertEquals("english language", testController.hello("en"));
    }
}