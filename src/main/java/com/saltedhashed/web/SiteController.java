package com.saltedhashed.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.saltedhashed.SiteVerifierJob;
import com.saltedhashed.dao.SiteDao;
import com.saltedhashed.model.Site;

@Controller
public class SiteController {

    @Inject
    private SiteDao dao;

    @Inject
    private UserContext userContext;

    @Inject
    private SiteVerifierJob verifierJob;

    @Inject
    private ServletContext servletContext;

    @Value("${base.url}")
    private String baseUrl;

    @ModelAttribute("baseUrl")
    public String getBaseUrl() {
        return baseUrl;
    }

    @RequestMapping("/")
    public String index(Model model) {
        if (userContext.getUser() != null) {
            model.addAttribute("sites", dao.getSitesForUser(userContext.getUser().getEmail()));
        }
        return "index";
    }
    @RequestMapping("/docs")
    public String docs() {
        return "docs";
    }
    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/site/save")
    public String save(Site site) {
        try {
            URL url = new URL(site.getBaseUrl());
            String baseUrl = url.toExternalForm();
            String path = url.getPath();
            if (!path.isEmpty()) {
                // replace last
                StringBuilder b = new StringBuilder(baseUrl);
                b.replace(baseUrl.lastIndexOf(path), baseUrl.lastIndexOf(path) + 1, "");
                baseUrl = b.toString();
            }
            site.setBaseUrl(baseUrl);
        } catch (MalformedURLException e) {
            return "redirect:/sites?message=Malformed base URL";
        }
        Site existing = dao.find(site.getBaseUrl());
        if (existing != null && !existing.getOwner().equals(userContext.getUser().getEmail())) {
            throw new IllegalStateException("Cannot edit site that is not owned by the user");
        }
        site.setOwner(userContext.getUser().getEmail());
        dao.save(site);

        // if this is a new site, immediately run the verification process
        if (existing == null) {
            site.setCreatedTimestamp(System.currentTimeMillis());
            verifierJob.verifySite(verifierJob.generateTestPasswordRequests(), site);
        }

        return "redirect:/sites";
    }

    @RequestMapping("/site/delete")
    public String delete(@RequestParam String baseUrl) {
        Site site = dao.find(baseUrl);
        if (!site.getOwner().equals(userContext.getUser().getEmail())) {
            throw new IllegalStateException("Cannot delete site that is not owned by the user");
        }
        dao.delete(site);
        return "redirect:/sites";
    }

    @RequestMapping("/site/verify")
    public String verify(@RequestParam String baseUrl) {
        Site site = dao.find(baseUrl);
        if (!site.getOwner().equals(userContext.getUser().getEmail())) {
            throw new IllegalStateException("Cannot invoke verification on site that is not owned by the user");
        }
        verifierJob.verifySite(verifierJob.generateTestPasswordRequests(), site);
        return "redirect:/sites";
    }

    @RequestMapping("/sites")
    public String sites(Model model) {
        if (userContext.getUser() == null) {
            return "redirect:/";
        }
        model.addAttribute("sites", dao.getSitesForUser(userContext.getUser().getEmail()));
        return "sites";
    }

    @RequestMapping("/site/badge")
    public void getBatch(@RequestParam String baseUrl, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        Site site = dao.find(baseUrl);
        if (site == null || site.getStatuses().isEmpty()) {
            return;
        }

        IOUtils.copy(servletContext.getResourceAsStream("/img/badge.png"), response.getOutputStream());
    }

    @RequestMapping("/site/status")
    public String getSiteStatus(@RequestParam String baseUrl, Model model) {
        Site site = dao.find(baseUrl);
        if (site == null || site.getStatuses().isEmpty()) {
            model.addAttribute("noData", true);
        } else {
            model.addAttribute("success", site.getStatuses().get(0).isSuccess());
        }
        return "status";
    }
}