package cz.zcu.yamanager.ws.rest;

import cz.zcu.yamanager.domain.Test;
import cz.zcu.yamanager.repository.HelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cz.zcu.yamanager.util.localization.Language.get;
import static cz.zcu.yamanager.util.localization.Message.getString;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class HelloController {

    private final HelloRepository repository;

    @Autowired
    public HelloController(HelloRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/hello", method=GET)
    public String hello(@RequestParam(value = "lang", required = false, defaultValue = "en") String lang) {
        return getString(get(lang), "hello");
    }

    @RequestMapping(value = "/database", method=GET)
    public List<Test> database() {
        return repository.all();
    }
}
