package com.beykent.realestate.controller;

import com.beykent.realestate.service.AgentService;
import com.beykent.realestate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PropertyService propertyService;
    private final AgentService agentService;

    @GetMapping("/")
    public String anaSayfa(Model model) {
        model.addAttribute("sonIlanlar", propertyService.aktifIlanlar());
        model.addAttribute("toplamIlan", propertyService.toplamAktif());
        model.addAttribute("toplamSatilik", propertyService.toplamSatilik());
        model.addAttribute("toplamKiralik", propertyService.toplamKiralik());
        model.addAttribute("toplamDanisman", agentService.toplamDanisman());
        return "index";
    }
}
