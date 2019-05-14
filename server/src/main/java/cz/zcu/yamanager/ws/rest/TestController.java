package cz.zcu.yamanager.ws.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class TestController {

    @RequestMapping(value = "/test", method=GET)
    public String hello() {
        return "It works!!";
    }
}
