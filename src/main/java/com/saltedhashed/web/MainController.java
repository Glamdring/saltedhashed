package com.saltedhashed.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.saltedhashed.dao.SiteDao;
import com.saltedhashed.dao.UserDao;
import com.saltedhashed.model.Site;
import com.saltedhashed.model.User;

@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Inject
    private SiteDao dao;

    @Inject
    private UserDao userDao;

    @Inject
    private UserContext context;

    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/")
    public void index() {
    }
//checksum of the site to be the same as latest build in github

    @RequestMapping("/site/insert")
    @ResponseBody
    public void insert(Site site) {
        dao.save(site);
    }

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
                return "/socialSignUp?email=" + response.getEmail();
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
