package org.example.apitest.controller.test;

import org.example.apitest.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping("/test")
    @ResponseBody
    public ResponseEntity<String> getTest(
            HttpServletRequest httpServletRequest
    ) throws Exception{

        return ResponseEntity.ok("gdgd");
    }
}
