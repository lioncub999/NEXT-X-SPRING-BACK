package org.example.apitest.controller.auth;

import org.example.apitest.model.auth.Register;
import org.example.apitest.model.log.TraceWriter;
import org.example.apitest.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @ResponseBody
    @PostMapping("/dupCheck")
    public Boolean dupCheck(
            HttpServletRequest request,
            @RequestBody Register register
            ) {
        TraceWriter traceWriter = new TraceWriter("", request.getMethod(), request.getServletPath());
        traceWriter.add("< INPUT >");
        traceWriter.add("[register.registerNm : " + register.getRegisterNm() + "]");
        traceWriter.add("");

        boolean result = false;

        try {
            int count = authService.dupCheck(register);
            if (count == 0) {
                result = true;
            }
        } catch (Exception e) {
            traceWriter.add("ERROR MESSAGE :" + e);
        }

        traceWriter.add("< OUTPUT >");
        traceWriter.add("[result : " + result + "]");
        traceWriter.log(0);

        return result;
    }
}
