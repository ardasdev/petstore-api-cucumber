Feature: Magaza (Store) islemleri
  Her senaryo kendi siparis verisini olusturur, dogrular ve temizler;
  envanter senaryosu zaten veri olusturmaz, bagimsizdir.

  Background:
    Given Petstore API erisilebilir durumda
    And temel adres "https://petstore.swagger.io/v2" olarak ayarli

  @TC_05
  Scenario: Magaza envanteri goruntulenebilir
    Given magaza envanteri mevcut
    When kullanici "/store/inventory" adresine GET istegi gonderir
    Then donen HTTP durum kodu 200 olmali
    And donen govde statu adlarini sayisal adetlere esleyen bir nesne olmali

  @TC_06
  Scenario: Yeni bir siparis olusturulabilir
    Given kullanici id degeri 6 ve status "placed" olan gecerli bir siparis hazirliyor
    When kullanici "/store/order" adresine POST istegi gonderir
    Then donen HTTP durum kodu 200 olmali
    And donen siparis id, petId ve status degerleri gonderilenle ayni olmali
    And test sonunda 6 idli siparis "/store/order/6" ile silinerek ortam temizlenir

  @TC_07
  Scenario: Bir siparis ID ile getirilebilir
    Given "/store/order" adresine POST ile id degeri 7 olan bir siparis olusturulmus
    When kullanici "/store/order/7" adresine GET istegi gonderir
    Then donen HTTP durum kodu 200 olmali
    And donen siparis bilgileri id degeri 7 olan kaydi icermeli
    And test sonunda 7 idli siparis "/store/order/7" ile silinerek ortam temizlenir

  @TC_08
  Scenario: Bir siparis silinebilir
    Given "/store/order" adresine POST ile id degeri 8 olan bir siparis olusturulmus
    When kullanici "/store/order/8" adresine DELETE istegi gonderir
    Then donen HTTP durum kodu 200 olmali
    And dogrulama icin "/store/order/8" GET istegi 404 dondurmeli