package com.saltedhashed.web;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saltedhashed.dao.SiteDao;
import com.saltedhashed.model.Site;

@Controller
public class MainController {

    @Inject
    private SiteDao dao;

    @RequestMapping("/")
    public void index() {
    }
//checksum of the site to be the same as latest build in github

    @RequestMapping("/site/insert")
    @ResponseBody
    public void insert(Site site) {
        dao.save(site);
    }
}
