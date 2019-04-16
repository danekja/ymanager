package cz.zcu.yamanager;

import cz.zcu.yamanager.util.localization.Language;
import cz.zcu.yamanager.util.localization.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        Message.config()
                .addLanguage(Language.EN)
                .addLanguage(Language.CZ);

        SpringApplication.run(Application.class, args);
    }
}
