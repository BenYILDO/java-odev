package com.beykent.realestate.controller;

import com.beykent.realestate.entity.Property;
import com.beykent.realestate.entity.Property.IlanTipi;
import com.beykent.realestate.entity.Property.Kategori;
import com.beykent.realestate.service.AgentService;
import com.beykent.realestate.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ilanlar")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;
    private final AgentService agentService;

    @GetMapping
    public String liste(Model model,
                        @RequestParam(required = false) String tipi,
                        @RequestParam(required = false) String kategori,
                        @RequestParam(required = false) String sehir) {
        if (tipi != null && !tipi.isBlank()) {
            model.addAttribute("ilanlar", propertyService.tipineGoreFilt(IlanTipi.valueOf(tipi)));
            model.addAttribute("aktifFiltre", tipi);
        } else if (kategori != null && !kategori.isBlank()) {
            model.addAttribute("ilanlar", propertyService.kategoriyeGoreFilt(Kategori.valueOf(kategori)));
            model.addAttribute("aktifFiltre", kategori);
        } else if (sehir != null && !sehir.isBlank()) {
            model.addAttribute("ilanlar", propertyService.sehireGoreFilt(sehir));
            model.addAttribute("aktifFiltre", "Şehir: " + sehir);
        } else {
            model.addAttribute("ilanlar", propertyService.aktifIlanlar());
        }
        return "property/list";
    }

    @GetMapping("/{id}")
    public String detay(@PathVariable Long id, Model model) {
        Property property = propertyService.ilanBul(id)
                .orElseThrow(() -> new IllegalArgumentException("İlan bulunamadı: " + id));
        model.addAttribute("property", property);
        return "property/detail";
    }

    @GetMapping("/yeni")
    public String yeniForm(Model model) {
        model.addAttribute("property", new Property());
        model.addAttribute("danismanlar", agentService.tumDanismanlar());
        model.addAttribute("ilanTipleri", IlanTipi.values());
        model.addAttribute("kategoriler", Kategori.values());
        return "property/form";
    }

    @PostMapping("/kaydet")
    public String kaydet(@Valid @ModelAttribute Property property,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("danismanlar", agentService.tumDanismanlar());
            model.addAttribute("ilanTipleri", IlanTipi.values());
            model.addAttribute("kategoriler", Kategori.values());
            return "property/form";
        }
        propertyService.ilanKaydet(property);
        redirectAttributes.addFlashAttribute("basari", "İlan başarıyla kaydedildi.");
        return "redirect:/ilanlar";
    }

    @GetMapping("/{id}/duzenle")
    public String duzenleForm(@PathVariable Long id, Model model) {
        Property property = propertyService.ilanBul(id)
                .orElseThrow(() -> new IllegalArgumentException("İlan bulunamadı: " + id));
        model.addAttribute("property", property);
        model.addAttribute("danismanlar", agentService.tumDanismanlar());
        model.addAttribute("ilanTipleri", IlanTipi.values());
        model.addAttribute("kategoriler", Kategori.values());
        return "property/form";
    }

    @PostMapping("/{id}/guncelle")
    public String guncelle(@PathVariable Long id,
                           @Valid @ModelAttribute Property property,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("danismanlar", agentService.tumDanismanlar());
            model.addAttribute("ilanTipleri", IlanTipi.values());
            model.addAttribute("kategoriler", Kategori.values());
            return "property/form";
        }
        property.setId(id);
        propertyService.ilanKaydet(property);
        redirectAttributes.addFlashAttribute("basari", "İlan başarıyla güncellendi.");
        return "redirect:/ilanlar";
    }

    @PostMapping("/{id}/sil")
    public String sil(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        propertyService.ilanSil(id);
        redirectAttributes.addFlashAttribute("basari", "İlan başarıyla silindi.");
        return "redirect:/ilanlar";
    }
}
