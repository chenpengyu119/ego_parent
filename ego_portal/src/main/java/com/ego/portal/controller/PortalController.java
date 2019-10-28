package com.ego.portal.controller;

import com.ego.portal.service.PortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * @author pengyu
 * @date 2019/9/27 15:04.
 */
@Controller
public class PortalController {

    @Autowired
    private PortalService portalService;

    @RequestMapping("/")
    public String welcome(Model model){
        System.out.println("----执行"+new Date().toLocaleString() +"---------");
        model.addAttribute("ad1", portalService.showPortalAd());
        return "index";
    }
}
