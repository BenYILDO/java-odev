package com.beykent.realestate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agent")
@Data
@NoArgsConstructor
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ad boş olamaz")
    @Column(nullable = false)
    private String ad;

    @NotBlank(message = "Soyad boş olamaz")
    @Column(nullable = false)
    private String soyad;

    @NotBlank(message = "Telefon boş olamaz")
    private String telefon;

    @Email(message = "Geçerli bir e-posta giriniz")
    @NotBlank(message = "E-posta boş olamaz")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Ofis boş olamaz")
    private String ofis;

    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Property> ilanlar = new ArrayList<>();

    public String getTamAd() {
        return ad + " " + soyad;
    }
}
