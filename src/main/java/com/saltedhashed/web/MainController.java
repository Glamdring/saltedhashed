package com.saltedhashed.web;

import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.saltedhashed.dao.SiteDao;
import com.saltedhashed.model.Site;

@Controller
public class MainController {

    @Inject
    private SiteDao dao;

    @Inject
    private UserContext userContext;

    @RequestMapping("/")
    public String index() {
        return "index";
    }
//checksum of the site to be the same as latest build in github

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
    @RequestMapping("/sites")
    public String sites(Model model) {
        if (userContext.getUser() == null) {
            return "redirect:/";
        }
        model.addAttribute("sites", dao.getSitesForUser(userContext.getUser().getEmail()));
        return "sites";
    }
}
