---
title: "Gayrimenkul Yönetim Sistemi"
subtitle: "İleri Java Programlama — Final Projesi"
author: "İstanbul Beykent Üniversitesi | Yazılım Mühendisliği Bölümü"
date: "2025–2026 Bahar Dönemi"
---

# 1. Giriş

## 1.1 Projenin Amacı

Gayrimenkul sektöründe ilan takibi ve danışman yönetimi, günümüzde hâlâ büyük ölçüde manuel ve dağınık yöntemlerle yürütülmektedir. Bu proje; satılık ve kiralık mülklerin dijital ortamda kayıt altına alınmasını, filtrelenebilmesini ve danışman bazında takip edilmesini sağlayan bir web uygulaması sunmaktadır.

## 1.2 Problem Tanımı

Küçük ve orta ölçekli emlak ofislerinin ortak sorunları şunlardır:

- İlanlar kâğıt veya Excel tabanlı tutulmaktadır.
- Danışman–mülk eşleşmeleri takip edilememektedir.
- Tip (satılık/kiralık) ve kategori (daire, villa, arsa vb.) bazlı filtreleme yapılamamaktadır.
- Fiyat ve konum bilgilerine hızlı erişim mümkün değildir.

Bu uygulama söz konusu sorunlara web tabanlı, kullanımı kolay ve kurulum gerektirmeyen bir çözüm sunmaktadır.

## 1.3 Hedefler

- Spring Boot MVC mimarisiyle çalışan tam fonksiyonlu bir web uygulaması geliştirmek
- En az iki JPA entity arasında ilişkisel veritabanı bağlantısı kurmak
- Tüm CRUD işlemlerini Thymeleaf arayüzü üzerinden gerçekleştirmek
- Dependency Injection ve ORM kullanımını uygulamalı olarak göstermek

---

# 2. Literatür

## 2.1 Spring Boot

Spring Boot, Java ekosisteminin en yaygın kullanılan web uygulama çatısı olan Spring Framework üzerine inşa edilmiştir. Sıfır yapılandırma (zero-configuration) felsefesiyle geliştiricinin hızlıca çalışan uygulama oluşturmasını sağlar. Gömülü Tomcat sunucusu sayesinde ayrı bir sunucu kurulumuna gerek kalmaz.

Bu projede Spring Boot 3.2.5 kullanılmıştır.

## 2.2 MVC (Model–View–Controller) Mimarisi

MVC, uygulamayı üç sorumluluk katmanına ayıran bir yazılım tasarım desenidir:

| Katman | Sorumluluk | Projede Karşılığı |
|--------|------------|-------------------|
| **Model** | Veri ve iş mantığı | `Property`, `Agent` entity sınıfları; `Service` katmanı |
| **View** | Kullanıcı arayüzü | Thymeleaf HTML şablonları |
| **Controller** | İstek–yanıt yönetimi | `PropertyController`, `AgentController` |

MVC'nin temel faydası; iş mantığının, görünümün ve kontrolün birbirinden bağımsız geliştirilip test edilebilmesidir.

## 2.3 Dependency Injection (Bağımlılık Enjeksiyonu)

Dependency Injection (DI), bir sınıfın ihtiyaç duyduğu bağımlılıkları kendi oluşturmak yerine dışarıdan almasını ifade eder. Spring IoC (Inversion of Control) konteyneri bu bağımlılıkları otomatik olarak yönetir.

Bu projede DI, Lombok'un `@RequiredArgsConstructor` anotasyonu aracılığıyla constructor injection yöntemiyle uygulanmıştır:

```java
@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository; // Spring tarafından inject edilir
}
```

## 2.4 ORM ve Spring Data JPA

Object–Relational Mapping (ORM), Java nesnelerini ilişkisel veritabanı tablolarıyla eşleştiren bir tekniktir. Spring Data JPA, Hibernate ORM üzerine inşa edilmiş soyutlama katmanıdır. SQL yazmadan CRUD işlemleri ve özel sorgular tanımlanabilir:

```java
List<Property> findByTipiAndAktifTrue(IlanTipi tipi);
```

## 2.5 Thymeleaf

Thymeleaf, Java EE ekosistemi için geliştirilmiş server-side HTML şablon motorudur. Spring MVC ile doğal entegrasyonu sayesinde Controller'dan gelen model verilerini HTML içinde `th:text`, `th:each`, `th:if` gibi direktiflerle görüntüler.

---

# 3. UML Sınıf Diyagramı

Aşağıdaki UML sınıf diyagramı, uygulamanın dört ana katmanını (entity, repository, service, controller) ve bunlar arasındaki ilişkileri göstermektedir.

*[BURAYA UML DIYAGRAMI RESMI EKLENECEK — uml/GayrimenkulYonetimSistemi.png dosyasını buraya yapıştır]*

Diyagramda dikkat çeken ilişkiler:

- `Agent` → `Property` : **OneToMany** (bir danışmanın birden fazla ilanı olabilir)
- `Property` → `Agent` : **ManyToOne** (her ilanın en fazla bir danışmanı vardır)
- `PropertyService` → `PropertyRepository` : **Dependency Injection** (constructor injection)
- `PropertyController` → `PropertyService` : **Dependency Injection** (constructor injection)

---

# 4. Kullanılan Teknolojiler

## 4.1 Backend

| Teknoloji | Sürüm | Gerekçe |
|-----------|-------|---------|
| Java | 17 LTS | Uzun dönem destekli kararlı sürüm |
| Spring Boot | 3.2.5 | MVC + DI + ORM entegrasyonu |
| Spring Data JPA | 3.2.5 | SQL yazmadan veritabanı erişimi |
| Hibernate | 6.x | JPA implementasyonu, ORM |
| Bean Validation | 3.x | Form alanı doğrulama anotasyonları |
| Lombok | 1.18.x | Getter/Setter/Constructor boilerplate azaltma |
| H2 Database | 2.x | Kurulum gerektirmeyen in-memory DB |

## 4.2 Frontend

| Teknoloji | Sürüm | Gerekçe |
|-----------|-------|---------|
| Thymeleaf | 3.1.x | Spring MVC entegrasyonu, server-side render |
| Bootstrap | 5.3 | Responsive ve modern arayüz bileşenleri |
| Bootstrap Icons | 1.11 | SVG ikon seti |

## 4.3 Geliştirme Ortamı

| Araç | Açıklama |
|------|----------|
| IDE | IntelliJ IDEA / VS Code |
| Build Aracı | Apache Maven 3.8+ |
| Versiyon Kontrolü | Git / GitHub |

## 4.4 Veritabanı Tasarımı

İki tablo kullanılmıştır:

**agent tablosu:**
`id`, `ad`, `soyad`, `telefon`, `email` (unique), `ofis`

**property tablosu:**
`id`, `baslik`, `fiyat`, `tipi`, `kategori`, `metrekare`, `oda_sayisi`, `adres`, `sehir`, `aciklama`, `aktif`, `kayit_tarihi`, `agent_id` (FK → agent)

---

# 5. Uygulama Ekran Görüntüleri

## 5.1 Ana Sayfa

*[EKRAN GÖRÜNTÜSÜ: Tarayıcıda http://localhost:8080 adresini aç, tam sayfanın görüntüsünü al. Hero banner, istatistik kartları (toplam ilan/satılık/kiralık/danışman sayıları) ve son ilanlar bölümü görünmelidir.]*

Ana sayfa; istatistik kartları, hero banner ve son eklenen ilanları listeleyen bölümlerden oluşmaktadır.

---

## 5.2 İlan Listesi ve Filtreleme

*[EKRAN GÖRÜNTÜSÜ: http://localhost:8080/ilanlar adresine git. Tüm ilanların tablo halinde göründüğü sayfanın ekran görüntüsünü al.]*

*[EKRAN GÖRÜNTÜSÜ: "Satılık" butonuna tıkla, http://localhost:8080/ilanlar?tipi=SATILIK adresinin ekran görüntüsünü al. Sadece satılık ilanların listelendiği görülmelidir.]*

İlan listesi sayfasında tip (Satılık/Kiralık), kategori (Daire/Villa/Arsa/Ofis/Dükkan) ve şehre göre filtreleme yapılabilmektedir.

---

## 5.3 Yeni İlan Ekleme (Create)

*[EKRAN GÖRÜNTÜSÜ: http://localhost:8080/ilanlar/yeni adresine git. Boş ilan ekleme formunun ekran görüntüsünü al. Başlık, fiyat, tip, kategori, metrekare, şehir, adres alanları görünmelidir.]*

*[EKRAN GÖRÜNTÜSÜ: Formu doldurmadan "Kaydet" butonuna tıkla. Kırmızı hata mesajlarının göründüğü ekran görüntüsünü al. Bean Validation'ın çalıştığını gösterir.]*

*[EKRAN GÖRÜNTÜSÜ: Formu eksiksiz doldurup kaydettikten sonra yönlendirilen ilan listesi sayfasının ekran görüntüsünü al. Üstte yeşil "İlan başarıyla kaydedildi" mesajı görünmelidir.]*

---

## 5.4 İlan Detay Sayfası (Read)

*[EKRAN GÖRÜNTÜSÜ: İlan listesinde herhangi bir ilanın "İncele" butonuna tıkla. http://localhost:8080/ilanlar/{id} adresinin ekran görüntüsünü al. İlan başlığı, fiyat, konum bilgileri ve sağ tarafta danışman kartı görünmelidir.]*

---

## 5.5 İlan Düzenleme (Update)

*[EKRAN GÖRÜNTÜSÜ: İlan listesinde herhangi bir ilanın sarı kalem (düzenle) butonuna tıkla. Mevcut verilerin dolu olduğu düzenleme formunun ekran görüntüsünü al.]*

*[EKRAN GÖRÜNTÜSÜ: Fiyat alanını değiştirip "Kaydet" butonuna tıkla. "İlan başarıyla güncellendi" mesajının göründüğü ekran görüntüsünü al.]*

---

## 5.6 İlan Silme (Delete)

*[EKRAN GÖRÜNTÜSÜ: İlan listesinde herhangi bir ilanın kırmızı çöp kutusu butonuna tıkla. Önce tarayıcının onay dialog penceresinin ekran görüntüsünü al.]*

*[EKRAN GÖRÜNTÜSÜ: Onayladıktan sonra ilan listesine döndüğünde "İlan başarıyla silindi" mesajının göründüğü ekran görüntüsünü al.]*

---

## 5.7 Danışman Yönetimi

*[EKRAN GÖRÜNTÜSÜ: http://localhost:8080/danismanlar adresine git. Danışman kartlarının listelendiği sayfanın ekran görüntüsünü al. Her kartta danışman adı, telefon, e-posta, ofis ve ilan sayısı görünmelidir.]*

*[EKRAN GÖRÜNTÜSÜ: http://localhost:8080/danismanlar/yeni adresine git. Yeni danışman ekleme formunun ekran görüntüsünü al.]*

---

## 5.8 Özel Hata Sayfaları

*[EKRAN GÖRÜNTÜSÜ: Tarayıcıda http://localhost:8080/ilanlar/9999 adresini aç. "404 — Sayfa Bulunamadı" hata sayfasının ekran görüntüsünü al.]*

---

## 5.9 H2 Veritabanı Konsolu

*[EKRAN GÖRÜNTÜSÜ: http://localhost:8080/h2-console adresine git. JDBC URL alanına "jdbc:h2:mem:realestate" yaz, bağlan. Sol panelde AGENT ve PROPERTY tablolarının göründüğü ekran görüntüsünü al.]*

---

# 6. Proje Sonucu

## 6.1 Yapılanlar

Bu projede aşağıdaki çalışmalar tamamlanmıştır:

- Spring Boot MVC mimarisi eksiksiz uygulanmıştır: Entity, Repository, Service, Controller katmanları ayrı sınıflarda tasarlanmıştır.
- İki JPA entity (`Property` ve `Agent`) arasında OneToMany/ManyToOne ilişkisi kurulmuştur.
- Her iki entity için tam CRUD işlemleri (oluştur, listele, detay, güncelle, sil) Thymeleaf arayüzü üzerinden gerçekleştirilmektedir.
- Dependency Injection constructor injection yöntemiyle uygulanmıştır.
- Bean Validation ile form doğrulama ve hata mesajları eklenmiştir.
- Tip, kategori ve şehir bazlı filtreleme özellikleri geliştirilmiştir.
- Özel 404/500 hata sayfaları ve merkezi exception handler eklenmiştir.
- Bootstrap 5 ile responsive ve modern bir arayüz oluşturulmuştur.
- Başlangıç verisi (`data.sql`) ile uygulama başlar başlamaz test edilebilir hale getirilmiştir.

## 6.2 Öğrenilenler

- Spring Boot'un auto-configuration mekanizması sayesinde minimum yapılandırmayla çalışan uygulama geliştirmenin mümkün olduğu gözlemlenmiştir.
- MVC deseninin katmanlar arası sorumluluk ayrımını (Separation of Concerns) nasıl sağladığı uygulamalı olarak öğrenilmiştir.
- Spring Data JPA'nın method naming convention'ı ile SQL yazmadan karmaşık sorgular tanımlanabildiği görülmüştür.
- Thymeleaf'in Spring MVC ile entegrasyonu, form binding ve validation gösterimi açısından oldukça esnek bir yapı sunduğu anlaşılmıştır.

## 6.3 Yapılamayanlar ve Gerekçeleri

- **Kullanıcı kimlik doğrulama:** Spring Security entegrasyonu proje kapsamı dışında bırakılmıştır; dersin öğrenme çıktılarındaki zorunlu gereksinim listesinde yer almadığından önceliklendirilmemiştir.
- **E-posta bildirimi:** SMTP sunucu konfigürasyonu gerektirdiğinden, ödev ortamında kurulum güçlüğü nedeniyle uygulanmamıştır.
- **Sayfalama (Pagination):** İlan listesi büyüdükçe performans sorununa yol açabilecek bu özellik, ileri geliştirme aşamasına bırakılmıştır.

---

# 7. Kaynakça

1. Spring Boot Documentation. (2024). *Spring Boot Reference Guide (3.2.x)*. https://docs.spring.io/spring-boot/docs/3.2.x/reference/html/
2. Spring Data JPA Documentation. (2024). *Spring Data JPA Reference Documentation*. https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
3. Thymeleaf. (2024). *Thymeleaf — Tutorial: Using Thymeleaf*. https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html
4. Hibernate ORM. (2024). *Hibernate ORM 6.x User Guide*. https://docs.jboss.org/hibernate/orm/6.0/userguide/html_single/
5. Bootstrap. (2023). *Bootstrap 5 Documentation*. https://getbootstrap.com/docs/5.3/
6. Gamma, E., Helm, R., Johnson, R., & Vlissides, J. (1994). *Design Patterns: Elements of Reusable Object-Oriented Software*. Addison-Wesley.
7. Walls, C. (2022). *Spring in Action, 6th Edition*. Manning Publications.
