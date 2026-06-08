package com.beykent.realestate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "property")
@Data
@NoArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Başlık boş olamaz")
    @Column(nullable = false)
    private String baslik;

    @NotNull(message = "Fiyat boş olamaz")
    @Positive(message = "Fiyat pozitif olmalıdır")
    private Double fiyat;

    @NotNull(message = "İlan tipi seçiniz")
    @Enumerated(EnumType.STRING)
    private IlanTipi tipi;

    @NotNull(message = "Kategori seçiniz")
    @Enumerated(EnumType.STRING)
    private Kategori kategori;

    @Positive(message = "Metrekare pozitif olmalıdır")
    private Integer metrekare;

    @Column(name = "oda_sayisi")
    private Integer odaSayisi;

    @NotBlank(message = "Adres boş olamaz")
    private String adres;

    @NotBlank(message = "Şehir boş olamaz")
    private String sehir;

    @Column(length = 1000)
    private String aciklama;

    private boolean aktif = true;

    @Column(name = "kayit_tarihi")
    private LocalDate kayitTarihi = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    private Agent agent;

    public enum IlanTipi {
        SATILIK, KIRALIK
    }

    public enum Kategori {
        DAIRE, VILLA, ARSA, OFIS, DUKKAN
    }
}
