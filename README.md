# Gayrimenkul Yönetim Sistemi

İstanbul Beykent Üniversitesi — İleri Java Programlama Final Projesi (2025–2026 Bahar)

Spring Boot MVC ile geliştirilmiş satılık/kiralık gayrimenkul ilan yönetim uygulaması.

---

## Gereksinimler

| Araç | Minimum Sürüm |
|------|---------------|
| Java (JDK) | 17 |
| Apache Maven | 3.8+ |

> Veritabanı kurulumu **gerekmez** — H2 in-memory DB otomatik başlar.

---

## macOS'ta Kurulum ve Çalıştırma

### 1. Java ve Maven kurulumu (Homebrew ile)

```bash
brew install openjdk@17
brew install maven
```

Java'yı PATH'e ekle:

```bash
echo 'export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

Kurulumu doğrula:

```bash
java -version   # openjdk 17.x.x
mvn -version    # Apache Maven 3.x.x
```

### 2. Projeyi çalıştır

```bash
# Repoyu klonla
git clone https://github.com/BenYILDO/java-odev.git
cd java-odev

# Uygulamayı başlat
mvn spring-boot:run
```

### 3. Tarayıcıda aç

```
http://localhost:8080
```

---

## Windows'ta Kurulum ve Çalıştırma

### 1. Java kurulumu

1. [https://adoptium.net](https://adoptium.net) adresinden **JDK 17** indir
2. `.msi` kurulum sihirbazını çalıştır
3. Kurulum sırasında **"Add to PATH"** seçeneğini işaretle

### 2. Maven kurulumu

1. [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi) adresinden `apache-maven-x.x.x-bin.zip` indir
2. `C:\Program Files\Maven\` klasörüne çıkart
3. **Sistem Ortam Değişkenleri → Path** kısmına `C:\Program Files\Maven\bin` ekle

Kurulumu doğrula (Komut İstemi / PowerShell):

```cmd
java -version
mvn -version
```

### 3. Projeyi çalıştır

```cmd
git clone https://github.com/BenYILDO/java-odev.git
cd java-odev
mvn spring-boot:run
```

### 4. Tarayıcıda aç

```
http://localhost:8080
```

---

## Uygulama Sayfaları

| URL | Açıklama |
|-----|----------|
| `/` | Ana sayfa — istatistikler ve son ilanlar |
| `/ilanlar` | Tüm ilanların listesi, filtreleme |
| `/ilanlar/yeni` | Yeni ilan ekleme formu |
| `/ilanlar/{id}` | İlan detay sayfası |
| `/danismanlar` | Emlak danışmanları listesi |
| `/danismanlar/yeni` | Yeni danışman ekleme |
| `/h2-console` | Veritabanı yönetim paneli (geliştirme) |

---

## Proje Yapısı

```
src/main/java/com/beykent/realestate/
├── entity/          → JPA entity sınıfları (Property, Agent)
├── repository/      → Spring Data JPA repository arayüzleri
├── service/         → İş mantığı katmanı
├── controller/      → Spring MVC controller sınıfları
└── config/          → Yardımcı konfigürasyon sınıfları

src/main/resources/
├── templates/       → Thymeleaf HTML şablonları
├── application.properties
└── data.sql         → Başlangıç verisi
```

---

## Teknolojiler

- **Spring Boot 3.2.5** — Web uygulaması çatısı
- **Spring MVC** — MVC mimarisi
- **Spring Data JPA / Hibernate** — ORM katmanı
- **H2 Database** — In-memory ilişkisel veritabanı
- **Thymeleaf** — Server-side HTML şablon motoru
- **Bean Validation** — Form doğrulama
- **Lombok** — Boilerplate kod azaltma
- **Bootstrap 5** — Arayüz bileşenleri
