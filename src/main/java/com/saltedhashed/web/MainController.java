package com.saltedhashed.web;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
        Site existing = dao.find(site.getBaseUrl());
        if (existing != null && !existing.getOwner().equals(userContext.getUser().getEmail())) {
            throw new IllegalStateException("Cannot edit site that is not owned by the user");
        }
        site.setOwner(userContext.getUser().getEmail());
        dao.save(site);
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
