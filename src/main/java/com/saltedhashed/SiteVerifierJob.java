package com.saltedhashed;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTimeConstants;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SiteVerifierJob {

    private RestTemplate restTemplate = new RestTemplate();

    private Verifier verifier = new Verifier();
    @Scheduled(fixedDelay=DateTimeConstants.MILLIS_PER_DAY)
    public void run() {
        List<Site> sites = null;
        List<String> passwords = new ArrayList<>(3);
        for (int i = 0; i < 3; i ++) {
            passwords.add(PasswordUtils.getRandomPassword());
        }
        for (Site site : sites) {
            for (String password : passwords) {
                PasswordResponse response =
                        restTemplate.postForObject(site.getBaseUrl() + site.getEndpointPath(), password, PasswordResponse.class);
                verifier.verify(password, response);
            }
        }
    }
}
