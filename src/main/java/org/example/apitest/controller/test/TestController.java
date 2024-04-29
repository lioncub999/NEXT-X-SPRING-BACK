package org.example.apitest.controller.test;

import org.example.apitest.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping("/test/hello")
    @ResponseBody
    public int getHome() {

        return testService.userCount();
    }
}
