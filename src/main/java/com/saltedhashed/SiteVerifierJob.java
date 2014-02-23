package com.saltedhashed;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Joiner;
import com.saltedhashed.dao.PageableOperation;
import com.saltedhashed.dao.SiteDao;
import com.saltedhashed.model.PasswordResponse;
import com.saltedhashed.model.Site;
import com.saltedhashed.model.SiteStatus;

@Component
public class SiteVerifierJob {

    private static final Logger logger = LoggerFactory.getLogger(SiteVerifierJob.class);

    @Inject
    private SiteDao dao;

    private RestTemplate restTemplate = new RestTemplate();

    private Verifier verifier = new Verifier();

    private Joiner joiner = Joiner.on('\n');

    @Scheduled(fixedDelay=DateTimeConstants.MILLIS_PER_DAY)
    public void run() {

        final List<String> passwords = new ArrayList<>(3);
        for (int i = 0; i < 3; i ++) {
            passwords.add(PasswordUtils.getRandomPassword());
        }

        dao.performBatched(200, new PageableOperation<Site>() {
            @Override
            public void execute() {
                List<Site> sites = getData();

                for (Site site : sites) {
                    SiteStatus status = new SiteStatus();
                    status.setHttpCode(200);
                    Set<String> messages = new HashSet<>();
                    boolean success = true;
                    for (String password : passwords) {
                        try {
                            PasswordResponse response =
                                    restTemplate.postForObject(site.getBaseUrl() + site.getEndpointPath(), password, PasswordResponse.class);
                            boolean verificationResult = verifier.verify(password, response);
                            if (!verificationResult) {
                                messages.add("Incorrect hash for password " + password + ", using algorithm " + response.getAlgorithm() + ".");
                            }
                            success = success && verificationResult;
                        } catch (HttpClientErrorException ex) {
                            success = false;
                            status.setHttpCode(ex.getStatusCode().value());
                            messages.add(ex.getStatusText());
                        } catch (RestClientException ex) {
                            success = false;
                            messages.add("REST exception: " + ex.getMessage());
                            logger.warn("Problem with site: " + site, ex);
                        } catch (Exception ex) {
                            success = false;
                            messages.add("Unexpected problem.");
                            logger.warn("Problem with site: " + site, ex);
                        }
                    }
                    status.setSuccess(success);
                    status.setMessage(joiner.join(messages));
                    site.getStatuses().add(0, status);
                    if (site.getStatuses().size() > 5) {
                        site.getStatuses().remove(5);
                    }
                    dao.save(site);
                }
            }
        });
    }
}
