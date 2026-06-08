package com.beykent.realestate.controller;

import com.beykent.realestate.entity.Agent;
import com.beykent.realestate.service.AgentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/danismanlar")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("danismanlar", agentService.tumDanismanlar());
        return "agent/list";
    }

    @GetMapping("/yeni")
    public String yeniForm(Model model) {
        model.addAttribute("agent", new Agent());
        return "agent/form";
    }

    @PostMapping("/kaydet")
    public String kaydet(@Valid @ModelAttribute Agent agent,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "agent/form";
        }
        agentService.danismanKaydet(agent);
        redirectAttributes.addFlashAttribute("basari", "Danışman başarıyla kaydedildi.");
        return "redirect:/danismanlar";
    }

    @GetMapping("/{id}/duzenle")
    public String duzenleForm(@PathVariable Long id, Model model) {
        Agent agent = agentService.danismanBul(id)
                .orElseThrow(() -> new IllegalArgumentException("Danışman bulunamadı: " + id));
        model.addAttribute("agent", agent);
        return "agent/form";
    }

    @PostMapping("/{id}/guncelle")
    public String guncelle(@PathVariable Long id,
                           @Valid @ModelAttribute Agent agent,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "agent/form";
        }
        agent.setId(id);
        agentService.danismanKaydet(agent);
        redirectAttributes.addFlashAttribute("basari", "Danışman başarıyla güncellendi.");
        return "redirect:/danismanlar";
    }

    @PostMapping("/{id}/sil")
    public String sil(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        agentService.danismanSil(id);
        redirectAttributes.addFlashAttribute("basari", "Danışman başarıyla silindi.");
        return "redirect:/danismanlar";
    }
}
