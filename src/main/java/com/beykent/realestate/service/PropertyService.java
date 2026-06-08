package com.beykent.realestate.service;

import com.beykent.realestate.entity.Property;
import com.beykent.realestate.entity.Property.IlanTipi;
import com.beykent.realestate.entity.Property.Kategori;
import com.beykent.realestate.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public List<Property> tumIlanlar() {
        return propertyRepository.findAll();
    }

    public List<Property> aktifIlanlar() {
        return propertyRepository.findByAktifTrue();
    }

    public Optional<Property> ilanBul(Long id) {
        return propertyRepository.findById(id);
    }

    public Property ilanKaydet(Property property) {
        return propertyRepository.save(property);
    }

    public void ilanSil(Long id) {
        propertyRepository.deleteById(id);
    }

    public List<Property> tipineGoreFilt(IlanTipi tipi) {
        return propertyRepository.findByTipiAndAktifTrue(tipi);
    }

    public List<Property> kategoriyeGoreFilt(Kategori kategori) {
        return propertyRepository.findByKategoriAndAktifTrue(kategori);
    }

    public List<Property> sehireGoreFilt(String sehir) {
        return propertyRepository.findBySehirContainingIgnoreCaseAndAktifTrue(sehir);
    }

    public long toplamAktif() {
        return propertyRepository.countAktif();
    }

    public long toplamSatilik() {
        return propertyRepository.countSatilik();
    }

    public long toplamKiralik() {
        return propertyRepository.countKiralik();
    }
}
