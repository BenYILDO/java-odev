# GAYRİMENKUL YÖNETİM SİSTEMİ

**İleri Java Programlama — Final Projesi**

İstanbul Beykent Üniversitesi | Mühendislik – Mimarlık Fakültesi | Yazılım Mühendisliği Bölümü

2025–2026 Bahar Dönemi

---

\newpage

## İçindekiler

1. Giriş
2. Literatür
3. UML Sınıf Diyagramı
4. Kullanılan Teknolojiler
5. Uygulama Ekran Görüntüleri
6. Proje Sonucu
7. Kaynakça

---

\newpage

## 1. Giriş

### 1.1 Projenin Amacı

Gayrimenkul sektöründe ilan takibi ve danışman yönetimi, günümüzde hâlâ büyük ölçüde manuel yöntemlerle yürütülmektedir. Bu proje; satılık ve kiralık mülklerin dijital ortamda kayıt altına alınmasını, filtrelenebilmesini ve danışman bazında takip edilmesini sağlayan, Spring Boot MVC mimarisiyle geliştirilmiş tam işlevli bir web uygulaması sunmaktadır.

### 1.2 Problem Tanımı

Küçük ve orta ölçekli emlak ofislerinin karşılaştığı başlıca sorunlar şunlardır:

- İlanlar kâğıt veya Excel tabanlı yöntemlerle tutulmakta, güncellenmesi ve aranması güç olmaktadır.
- Danışman–mülk eşleşmeleri sistematik biçimde takip edilememektedir.
- Tip (satılık / kiralık) ve kategori (daire, villa, arsa vb.) bazlı hızlı filtreleme yapılamamaktadır.
- Fiyat ve konum bilgilerine anlık erişim mümkün değildir.

Bu uygulama söz konusu sorunlara; web tabanlı, kullanımı kolay ve ek kurulum gerektirmeyen modern bir çözüm sunmaktadır.

### 1.3 Projenin Hedefleri

- Spring Boot MVC mimarisini gerçek bir proje üzerinde uygulamalı olarak kullanmak
- İki JPA entity (`Property` ve `Agent`) arasında OneToMany/ManyToOne ilişkisi kurmak
- Tüm CRUD işlemlerini (oluştur, listele, detay gör, güncelle, sil) Thymeleaf arayüzü üzerinden gerçekleştirmek
- Dependency Injection, ORM ve Bean Validation gibi ileri Java kavramlarını uygulamak

---

\newpage

## 2. Literatür

### 2.1 Spring Boot

Spring Boot; Java ekosisteminin en yaygın kullanılan web uygulama çatısı olan Spring Framework üzerine inşa edilmiştir. "Convention over configuration" (yapılandırma yerine uzlaşı) felsefesiyle, geliştiricinin sıfır XML yapılandırmasıyla çalışan bir uygulama oluşturabilmesini sağlar. Gömülü Tomcat sunucusu sayesinde ayrı bir sunucu kurulumu gerektirmez; `mvn spring-boot:run` komutuyla uygulama saniyeler içinde ayağa kalkar (Walls, 2022).

Bu projede Spring Boot **3.2.5** sürümü kullanılmıştır.

### 2.2 MVC (Model–View–Controller) Mimarisi

MVC; uygulamayı üç bağımsız sorumluluk katmanına ayıran bir yazılım tasarım desenidir (Gamma ve diğ., 1994). Her katmanın görevi şu şekilde tanımlanmaktadır:

| Katman | Sorumluluk | Bu Projede Karşılığı |
|--------|------------|----------------------|
| **Model** | Veri yapısı ve iş mantığı | `Property`, `Agent` entity sınıfları; `Service` katmanı |
| **View** | Kullanıcı arayüzü | Thymeleaf HTML şablonları |
| **Controller** | HTTP isteklerini karşılar, modeli hazırlar, view'ı döndürür | `PropertyController`, `AgentController`, `HomeController` |

MVC'nin temel faydası; iş mantığının, görünümün ve kontrolün birbirinden bağımsız geliştirilip test edilebilmesidir. Bu projede her katman ayrı Java paketlerine ayrılarak sorumluluklar net biçimde sınırlandırılmıştır.

### 2.3 Dependency Injection (Bağımlılık Enjeksiyonu)

Dependency Injection; bir sınıfın ihtiyaç duyduğu bağımlılıkları kendi oluşturmak yerine dışarıdan almasını ifade eden bir tasarım ilkesidir. Spring IoC (Inversion of Control) konteyneri bu bağımlılıkları otomatik olarak yönetir.

Bu projede DI, Lombok'un `@RequiredArgsConstructor` anotasyonu aracılığıyla **constructor injection** yöntemiyle uygulanmıştır. Aşağıdaki örnekte `PropertyService`, `PropertyRepository` bağımlılığını Spring konteynerinden alır:

```java
@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;
    // Spring, bu alanı otomatik olarak inject eder
}
```

Constructor injection; field injection'a kıyasla daha güvenli bir yaklaşımdır çünkü bağımlılıkların zorunlu (non-null) olmasını garanti eder ve sınıfın test edilebilirliğini artırır.

### 2.4 ORM ve Spring Data JPA

Object–Relational Mapping (ORM); Java nesnelerini ilişkisel veritabanı tablolarıyla eşleştiren bir tekniktir. Spring Data JPA, Hibernate ORM'in üzerine inşa edilmiş yüksek seviyeli bir soyutlama katmanıdır. Geliştirici herhangi bir SQL yazmadan CRUD işlemleri gerçekleştirebilir; metot isimlendirme kuralıyla (method naming convention) sorgu tanımlanabilir:

```java
// Spring Data JPA bu metodu otomatik olarak SQL'e çevirir:
List<Property> findByTipiAndAktifTrue(IlanTipi tipi);
// Üretilen SQL: SELECT * FROM property WHERE tipi = ? AND aktif = true
```

Bu projede veritabanı tabloları JPA tarafından otomatik oluşturulmakta (`ddl-auto=create-drop`), başlangıç verisi ise `data.sql` dosyasıyla yüklenmektedir.

### 2.5 Thymeleaf

Thymeleaf; Java EE ekosistemi için geliştirilmiş, Spring MVC ile doğal entegrasyon sunan bir server-side HTML şablon motorudur. Controller'dan gelen model verileri `th:text`, `th:each`, `th:if`, `th:field` gibi direktiflerle HTML içinde işlenir. Thymeleaf şablonları saf HTML olarak da tarayıcıda açılabilir, bu da prototiplemeyi kolaylaştırır (Thymeleaf, 2024).

```html
<!-- Controller'dan gelen "ilanlar" listesini tabloya yazan Thymeleaf örneği -->
<tr th:each="p : ${ilanlar}">
    <td th:text="${p.baslik}"></td>
    <td th:text="${p.fiyat} + ' ₺'"></td>
</tr>
```

---

\newpage

## 3. UML Sınıf Diyagramı

Aşağıdaki UML sınıf diyagramı uygulamanın dört ana katmanını — entity, repository, service ve controller — ile bu katmanlar arasındaki bağımlılık ve ilişkileri göstermektedir.

**[BURAYA UML DIYAGRAMI EKLENECEK — uml/GayrimenkulYonetimSistemi.png dosyasını buraya yapıştırın]**

### 3.1 Diyagramın Açıklaması

**Entity Katmanı — Temel İlişkiler:**

- `Agent` → `Property` : **OneToMany** ilişkisi — bir danışmanın birden fazla ilanı olabilir; `ilanlar` alanı `List<Property>` türündedir.
- `Property` → `Agent` : **ManyToOne** ilişkisi — her ilanın en fazla bir sorumlu danışmanı vardır; `agent_id` yabancı anahtar sütunuyla sağlanır.
- `Property` sınıfı iki enum içerir: `IlanTipi` (SATILIK / KİRALIK) ve `Kategori` (DAİRE / VİLLA / ARSA / OFİS / DÜKKAN).

**Katmanlar Arası Bağımlılıklar (Dependency Injection):**

- `PropertyService` → `PropertyRepository` : Service katmanı, veri erişimini Repository üzerinden gerçekleştirir.
- `PropertyController` → `PropertyService` : Controller, iş mantığını Service katmanından çağırır.
- `HomeController` → `PropertyService`, `AgentService` : Ana sayfa istatistiklerini her iki servisten toplar.

---

\newpage

## 4. Kullanılan Teknolojiler

### 4.1 Backend Teknolojileri

| Teknoloji | Sürüm | Kullanım Amacı |
|-----------|-------|----------------|
| Java | 17 LTS | Temel programlama dili |
| Spring Boot | 3.2.5 | Web uygulama çatısı, auto-configuration |
| Spring MVC | 6.1.x | HTTP istek/yanıt yönetimi, MVC mimarisi |
| Spring Data JPA | 3.2.x | Repository katmanı, ORM soyutlaması |
| Hibernate | 6.4.x | JPA implementasyonu, SQL üretimi |
| Bean Validation | 3.0.x | Form alanı doğrulama (`@NotBlank`, `@Positive`, `@Email`) |
| Lombok | 1.18.x | `@Data`, `@RequiredArgsConstructor` ile boilerplate azaltma |
| H2 Database | 2.x | Kurulum gerektirmeyen in-memory ilişkisel veritabanı |

### 4.2 Frontend Teknolojileri

| Teknoloji | Sürüm | Kullanım Amacı |
|-----------|-------|----------------|
| Thymeleaf | 3.1.x | Server-side HTML şablon motoru |
| Bootstrap | 5.3.0 | Responsive arayüz bileşenleri |
| Bootstrap Icons | 1.11.0 | SVG ikon seti |

### 4.3 Geliştirme Ortamı

| Araç | Açıklama |
|------|----------|
| IDE | IntelliJ IDEA / VS Code |
| Build Aracı | Apache Maven 3.8+ |
| Versiyon Kontrolü | Git / GitHub |
| Bağımlılık Yönetimi | Maven `pom.xml` — Spring Boot Parent POM |

### 4.4 Veritabanı Şeması

Uygulama iki tablo kullanmaktadır. Tablolar Spring Boot başlangıcında JPA tarafından H2 veritabanında otomatik oluşturulur.

**`agent` tablosu:**

| Sütun | Tür | Kısıt |
|-------|-----|-------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| ad | VARCHAR | NOT NULL |
| soyad | VARCHAR | NOT NULL |
| telefon | VARCHAR | — |
| email | VARCHAR | UNIQUE |
| ofis | VARCHAR | — |

**`property` tablosu:**

| Sütun | Tür | Kısıt |
|-------|-----|-------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| baslik | VARCHAR | NOT NULL |
| fiyat | DOUBLE | — |
| tipi | VARCHAR | SATILIK / KİRALIK |
| kategori | VARCHAR | DAİRE / VİLLA / ARSA / OFİS / DÜKKAN |
| metrekare | INTEGER | — |
| oda_sayisi | INTEGER | — |
| adres | VARCHAR | — |
| sehir | VARCHAR | — |
| aciklama | VARCHAR(1000) | — |
| aktif | BOOLEAN | DEFAULT true |
| kayit_tarihi | DATE | — |
| agent_id | BIGINT | FOREIGN KEY → agent(id) |

---

\newpage

## 5. Uygulama Ekran Görüntüleri

### 5.1 Ana Sayfa

**[EKRAN GÖRÜNTÜSÜ ALINACAK]**
*Tarayıcıda `http://localhost:8080` adresini açın. Koyu gradyan hero banner, dört istatistik kartı (Aktif İlan / Satılık / Kiralık / Danışman sayıları), "Neden Biz?" bölümü ve son eklenen ilanların kartları görünmelidir.*

Ana sayfa; kullanıcının sisteme ilk girişinde istatistiklere ve öne çıkan ilanlara hızlı erişimini sağlar. Hero section'daki "Satılık İlanlar" ve "Kiralık İlanlar" butonları ilgili filtreye doğrudan yönlendirir.

---

### 5.2 İlan Listesi

**[EKRAN GÖRÜNTÜSÜ ALINACAK]**
*`http://localhost:8080/ilanlar` adresine gidin. Tüm ilanların tablo halinde sıralandığı sayfanın görüntüsünü alın. Başlık, Tip, Kategori, Şehir, m², Fiyat ve Danışman sütunları görünmelidir.*

---

### 5.3 Filtreleme

**[EKRAN GÖRÜNTÜSÜ ALINACAK]**
*İlan listesi sayfasında "Satılık" butonuna tıklayın. URL `?tipi=SATILIK` parametresi alır ve yalnızca satılık ilanlar listelenir. Üstte "Filtre: SATILIK" etiketi görünür.*

Uygulama; ilan tipi (Satılık/Kiralık), kategori (Daire/Villa/Arsa/Ofis/Dükkan) ve serbest metin şehir araması olmak üç filtreleme yöntemi sunmaktadır.

---

### 5.4 Yeni İlan Ekleme — Form

**[EKRAN GÖRÜNTÜSÜ ALINACAK]**
*`http://localhost:8080/ilanlar/yeni` adresine gidin. Başlık, İlan Tipi, Kategori, Fiyat, Metrekare, Oda Sayısı, Şehir, Adres, Danışman seçimi ve Açıklama alanlarını içeren formu alın.*

---

### 5.5 Form Doğrulama (Bean Validation)

**[EKRAN GÖRÜNTÜSÜ ALINACAK]**
*Yeni ilan formunu tamamen boş bırakıp "Kaydet" butonuna tıklayın. Her zorunlu alanın altında kırmızı hata mesajları ("Başlık boş olamaz", "Fiyat boş olamaz" vb.) çıkmalıdır.*

Bean Validation anotasyonları (`@NotBlank`, `@NotNull`, `@Positive`, `@Email`) ile form doğrulama uygulanmıştır. Hatalar sunucu tarafında kontrol edilip kullanıcıya geri bildirim verilmektedir.

---

### 5.6 İlan Kaydedildi — Başarı Mesajı

**[EKRAN GÖRÜNTÜSÜ ALINACAK]**
*Formu eksiksiz doldurup kaydedin. Yönlendirilen ilan listesi sayfasının üstünde yeşil "İlan başarıyla kaydedildi." başarı mesajı görünmelidir.*

---

### 5.7 İlan Detay Sayfası

**[EKRAN GÖRÜNTÜSÜ ALINACAK]**
*İlan listesinde herhangi bir ilanın adına tıklayın. Sol sütunda ilan bilgileri (başlık, fiyat, konum, m², oda sayısı, kayıt tarihi, açıklama), sağ sütunda sorumlu danışmanın kartı (ad, telefon, e-posta, ofis) görünmelidir.*

---

### 5.8 İlan Düzenleme

**[EKRAN GÖRÜNTÜSÜ ALINACAK]**
*İlan listesinde herhangi bir ilanın sarı kalem butonuna tıklayın. Formun mevcut verilerle dolu geldiğini gösteren ekran görüntüsünü alın. Bu, HTTP GET ile veriyi çekip HTTP POST ile güncelleme akışını gösterir.*

---

### 5.9 İlan Silme — Onay Diyalogu

**[EKRAN GÖRÜNTÜSÜ ALINACAK]**
*İlan listesinde herhangi bir ilanın kırmızı çöp kutusu butonuna tıklayın. Tarayıcının onay diyalog penceresinin ("Bu ilanı silmek istediğinize emin misiniz?") ekran görüntüsünü alın.*

---

### 5.10 Danışman Listesi

**[EKRAN GÖRÜNTÜSÜ ALINACAK]**
*`http://localhost:8080/danismanlar` adresine gidin. Her danışman için ad/soyad, ofis, telefon, e-posta ve ilan sayısının göründüğü kart görünümünün ekran görüntüsünü alın.*

---

### 5.11 Özel 404 Hata Sayfası

**[EKRAN GÖRÜNTÜSÜ ALINACAK]**
*Tarayıcıda `http://localhost:8080/ilanlar/9999` adresini açın. Büyük "404" yazısı, harita ikonu ve "Sayfa Bulunamadı" mesajıyla özel hata sayfasının ekran görüntüsünü alın.*

---

\newpage

## 6. Proje Sonucu

### 6.1 Yapılanlar

Bu proje kapsamında aşağıdaki çalışmalar başarıyla tamamlanmıştır:

**Mimari ve Kod Kalitesi:**

- Spring Boot MVC mimarisi eksiksiz uygulanmış; Entity, Repository, Service ve Controller sorumlulukları ayrı paketlerde tasarlanmıştır.
- Dependency Injection, constructor injection yöntemiyle tüm katmanlarda tutarlı biçimde kullanılmıştır.
- `AgentConverter` sınıfıyla `Converter<String, Agent>` arayüzü implement edilerek form binding özelleştirilmiştir.
- `GlobalExceptionHandler` (`@ControllerAdvice`) ile merkezi hata yönetimi sağlanmıştır.

**Veri Katmanı:**

- `Property` ve `Agent` entity'leri arasında JPA OneToMany/ManyToOne ilişkisi kurulmuştur.
- `PropertyRepository`'de tip, kategori ve şehir bazlı özel sorgular JPA method naming ile tanımlanmıştır.
- `data.sql` dosyasıyla 3 danışman ve 6 ilan içeren başlangıç verisi yüklenmiştir.

**Arayüz ve Kullanıcı Deneyimi:**

- Tüm CRUD işlemleri (oluştur, listele, detay, güncelle, sil) Thymeleaf arayüzü üzerinden tam işlevli olarak gerçekleştirilmektedir.
- Bean Validation ile form doğrulama ve anlık hata mesajları eklenmiştir.
- Tip, kategori ve şehir bazlı filtreleme özellikleri geliştirilmiştir.
- Bootstrap 5 ile responsive ve modern bir arayüz oluşturulmuştur.
- Özel 404, 500 ve 403 hata sayfaları tasarlanmıştır.

### 6.2 Öğrenilenler

Bu proje sürecinde edinilen en önemli kazanımlar şunlardır:

- Spring Boot'un auto-configuration mekanizmasının minimum yapılandırmayla nasıl çalışır uygulama ortaya çıkardığı deneyimlenmiştir.
- MVC deseninin katmanlar arası Separation of Concerns (sorumluluk ayrımı) ilkesini ne ölçüde güçlendirdiği pratikte gözlemlenmiştir.
- Spring Data JPA'nın method naming convention'ıyla SQL yazmadan karmaşık sorgular tanımlanabildiği öğrenilmiştir.
- Thymeleaf'in `th:field`, `th:errors` direktifleriyle form binding ve validation gösteriminin entegre çalıştığı anlaşılmıştır.
- `@ControllerAdvice` ile merkezi exception handling'in uygulama güvenilirliğini nasıl artırdığı kavranmıştır.

### 6.3 Yapılamayanlar ve Gerekçeleri

| Özellik | Yapılamamasının Gerekçesi |
|---------|--------------------------|
| Kullanıcı kimlik doğrulama (Spring Security) | Dersin öğrenme çıktıları arasında yer almamaktadır; proje kapsamını aşmaktadır. |
| E-posta bildirimi | SMTP sunucu konfigürasyonu gerektirmekte olup geliştirme ortamında kurulumu güçtür. |
| Sayfalama (Pagination) | İlan listesi küçük ölçekte çalışmakta olup gelecek sürüm için planlanmıştır. |
| Gerçek veritabanı (PostgreSQL/MySQL) | H2 in-memory DB, kurulum gerektirmeksizin proje gereksinimlerini karşılamaktadır. |

---

\newpage

## 7. Kaynakça

1. Spring (2024). *Spring Boot Reference Documentation (3.2.x)*. https://docs.spring.io/spring-boot/docs/3.2.x/reference/html/

2. Spring (2024). *Spring Data JPA Reference Documentation*. https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

3. Spring (2024). *Spring Framework — Web MVC*. https://docs.spring.io/spring-framework/reference/web/webmvc.html

4. Thymeleaf (2024). *Tutorial: Using Thymeleaf*. https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html

5. Hibernate (2024). *Hibernate ORM 6.x User Guide*. https://docs.jboss.org/hibernate/orm/6.4/userguide/html_single/

6. Bootstrap (2023). *Bootstrap 5.3 Documentation*. https://getbootstrap.com/docs/5.3/

7. Gamma, E., Helm, R., Johnson, R., & Vlissides, J. (1994). *Design Patterns: Elements of Reusable Object-Oriented Software*. Addison-Wesley Professional.

8. Walls, C. (2022). *Spring in Action, 6th Edition*. Manning Publications.

9. Bauer, C., King, G., & Gregory, G. (2016). *Java Persistence with Hibernate, 2nd Edition*. Manning Publications.
