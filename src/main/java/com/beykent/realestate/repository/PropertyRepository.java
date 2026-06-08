package com.beykent.realestate.repository;

import com.beykent.realestate.entity.Property;
import com.beykent.realestate.entity.Property.IlanTipi;
import com.beykent.realestate.entity.Property.Kategori;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findByAktifTrue();

    List<Property> findByTipiAndAktifTrue(IlanTipi tipi);

    List<Property> findByKategoriAndAktifTrue(Kategori kategori);

    List<Property> findBySehirContainingIgnoreCaseAndAktifTrue(String sehir);

    List<Property> findByFiyatBetweenAndAktifTrue(Double min, Double max);

    @Query("SELECT COUNT(p) FROM Property p WHERE p.aktif = true")
    long countAktif();

    @Query("SELECT COUNT(p) FROM Property p WHERE p.tipi = 'SATILIK' AND p.aktif = true")
    long countSatilik();

    @Query("SELECT COUNT(p) FROM Property p WHERE p.tipi = 'KIRALIK' AND p.aktif = true")
    long countKiralik();
}
