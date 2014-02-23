package com.saltedhashed.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.validator.constraints.impl.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.NativeWebRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.saltedhashed.dao.UserDao;
import com.saltedhashed.model.User;

@Controller
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Inject
    private UserContext context;

    @Inject
    private UserDao userDao;

    private EmailValidator emailValidator = new EmailValidator();
    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/persona/auth")
    @ResponseBody
    public String authenticateWithPersona(@RequestParam String assertion,
            @RequestParam boolean userRequestedAuthentication, HttpServletRequest request,
            HttpServletResponse httpResponse, Model model)
            throws IOException {
        if (context.getUser() != null) {
            return "";
        }
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("assertion", assertion);
        params.add("audience", request.getScheme() + "://" + request.getServerName() + ":" + (request.getServerPort() == 80 ? "" : request.getServerPort()));
        PersonaVerificationResponse response = restTemplate.postForObject("https://verifier.login.persona.org/verify", params, PersonaVerificationResponse.class);
        if (response.getStatus().equals("okay")) {
            User user = userDao.find(response.getEmail());
            if (user == null && userRequestedAuthentication) {
                return "/signup?email=" + response.getEmail();
            } else if (user != null){
                if (userRequestedAuthentication) {
                    context.setUser(user);
                    return "/";
                } else {
                    return "";
                }
            } else {
                return ""; //in case this is not a user-requested operation, do nothing
            }
        } else {
            logger.warn("Persona authentication failed due to reason: " + response.getReason());
            throw new IllegalStateException("Authentication failed");
        }
    }

    @RequestMapping("/signup")
    public String socialSignupPage(@RequestParam String email, NativeWebRequest request, Model model) {
        User user = new User();
        user.setEmail(email);
        model.addAttribute("user", user);
        return "signup";
    }

    @RequestMapping("/auth/completeRegistration")
    public String completeRegistration(@RequestParam String email, NativeWebRequest request, Model model) {

        if (!emailValidator.isValid(email, null)) {
            return "redirect:/?message=Invalid email. Try again";
        }

        User user = userDao.completeUserRegistration(email);
        context.setUser(user);

        return "redirect:/?message=Registration successful";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        session.invalidate();
        return "index";
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    @SuppressWarnings("unused")
    private static class PersonaVerificationResponse {
        private String status;
        private String email;
        private String reason;
        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getReason() {
            return reason;
        }
        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
