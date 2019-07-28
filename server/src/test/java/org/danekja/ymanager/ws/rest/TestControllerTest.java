package org.danekja.ymanager.ws.rest;

import org.danekja.ymanager.util.localization.Language;
import org.danekja.ymanager.util.localization.Message;
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

        testController = new TestController();
    }

    @Test
    void hello_inEnglish_true() {
        assertEquals("It works!!", testController.hello());
    }
}