package org.example.apitest.controller.test;

import org.example.apitest.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/test/hello")
    @ResponseBody
    public String getHome(
            HttpServletRequest httpServletRequest
    ) throws Exception{
        String name = (String) httpServletRequest.getSession().getAttribute("userId");
        System.out.println(name);

        return "gkdl";
    }

    @GetMapping("/test/login")
    @ResponseBody
    public int testLogin(
            HttpServletRequest httpServletRequest
    ) throws Exception {
    // Session이 있으면 가져오고 없으면 Session을 생성해서 return (default = true)
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession();
        String name = "sehwi";
        session.setAttribute("userId", name);
        System.out.println(session.getAttribute("userId"));
    // Session의 유효 시간 설정 (1800초 = 30분)
        session.setMaxInactiveInterval(1800);
        System.out.println("세션생성");
        return 1;
    }
}
