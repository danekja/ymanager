package cz.zcu.yamanager.ws.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController("/test")
public class TestController {

    @RequestMapping(method=GET)
    public String get() {
        return "test GET";
    }

    @RequestMapping(method=POST)
    public String post() {
        return "test POST";
    }
}
