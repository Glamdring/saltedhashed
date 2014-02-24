package com.saltedhashed.web;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saltedhashed.model.Algorithm;
import com.saltedhashed.model.PasswordRequest;
import com.saltedhashed.model.PasswordResponse;

@Controller
public class TestController {

    @RequestMapping(value="/test/hash", consumes="application/json", produces="application/json")
    @ResponseBody
    public PasswordResponse hashPassword(@RequestBody PasswordRequest request) {
        PasswordResponse response = new PasswordResponse();
        response.setAlgorithm(Algorithm.BCRYPT);
        response.setHash(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt(10)));
        return response;
    }
}
