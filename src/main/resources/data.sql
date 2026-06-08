INSERT INTO agent (ad, soyad, telefon, email, ofis) VALUES
('Ahmet', 'Yılmaz', '0532 111 22 33', 'ahmet@emlak.com', 'Beşiktaş Şubesi'),
('Fatma', 'Kaya', '0533 222 33 44', 'fatma@emlak.com', 'Kadıköy Şubesi'),
('Mehmet', 'Demir', '0535 333 44 55', 'mehmet@emlak.com', 'Şişli Şubesi');

INSERT INTO property (baslik, fiyat, tipi, kategori, metrekare, oda_sayisi, adres, sehir, aciklama, aktif, agent_id) VALUES
('Kadıköy Merkezde 3+1 Daire', 4500000, 'SATILIK', 'DAIRE', 120, 3, 'Moda Cad. No:15', 'İstanbul', 'Deniz manzaralı, ferah ve aydınlık daire.', true, 2),
('Beşiktaş Kiralık Ofis', 25000, 'KIRALIK', 'OFIS', 85, 0, 'Barbaros Bulvarı No:42', 'İstanbul', 'Merkezi konumda, asansörlü binada ofis.', true, 1),
('Çeşme Villa Satılık', 12000000, 'SATILIK', 'VILLA', 350, 5, 'Alaçatı Mah. Zeytinlik Sk.', 'İzmir', 'Havuzlu, denize 500m mesafede lüks villa.', true, 3),
('Şişli 2+1 Kiralık', 18000, 'KIRALIK', 'DAIRE', 75, 2, 'Halaskargazi Cad. No:88', 'İstanbul', 'Yeni tadilatlı, eşyalı daire.', true, 3),
('Antalya Arsa', 2800000, 'SATILIK', 'ARSA', 500, 0, 'Konyaaltı İlçesi', 'Antalya', 'İmarlı, köşe arsa. Yatırıma uygun.', true, 1),
('Bağcılar Dükkan Kiralık', 12000, 'KIRALIK', 'DUKKAN', 60, 0, 'Fevzi Çakmak Cad. No:7', 'İstanbul', 'Ana cadde üzeri, işlek konumda dükkan.', true, 2);
