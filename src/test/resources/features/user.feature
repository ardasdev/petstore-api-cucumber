Feature: Kullanici (User) islemleri
  Her senaryo kendine ozel benzersiz bir kullanici olusturur, dogrular ve temizler;
  boylece senaryolar birbirinden bagimsizdir.

  Background:
    Given Petstore API erisilebilir durumda
    And temel adres "https://petstore.swagger.io/v2" olarak ayarli

  @TC_09
  Scenario: Yeni bir kullanici olusturulabilir
    Given kullanici username degeri "user_post" olan gecerli bir kullanici bilgisi hazirliyor
    When kullanici "/user" adresine POST istegi gonderir
    Then donen HTTP durum kodu 200 olmali
    And donen govde code, type ve message alanlarini icermeli
    And test sonunda "user_post" kullanicisi "/user/user_post" ile silinerek ortam temizlenir

  @TC_10
  Scenario: Kullanici sisteme giris yapabilir
    Given "/user" adresine POST ile "user_login" kullanicisi sifresiyle olusturulmus
    When kullanici "/user/login" adresine "user_login" ve sifresiyle GET istegi gonderir
    Then donen HTTP durum kodu 200 olmali
    And donen govde "logged in user session" mesajini icermeli
    And test sonunda "user_login" kullanicisi "/user/user_login" ile silinerek ortam temizlenir

  @TC_11
  Scenario: Kullanici username ile getirilebilir
    Given "/user" adresine POST ile "user_get" kullanicisi olusturulmus
    When kullanici "/user/user_get" adresine GET istegi gonderir
    Then donen HTTP durum kodu 200 olmali
    And donen kullanici bilgileri firstName, lastName ve email alanlarini icermeli
    And test sonunda "user_get" kullanicisi "/user/user_get" ile silinerek ortam temizlenir

  @TC_12
  Scenario: Kullanici bilgileri guncellenebilir
    Given "/user" adresine POST ile "user_put" kullanicisi olusturulmus
    And kullanici guncel firstName ve email bilgilerini hazirliyor
    When kullanici "/user/user_put" adresine PUT istegi gonderir
    Then donen HTTP durum kodu 200 olmali
    And kullanicinin firstName ve email alanlari guncellenmis olmali
    And test sonunda "user_put" kullanicisi "/user/user_put" ile silinerek ortam temizlenir

  @TC_13
  Scenario: Kullanici silinebilir
    Given "/user" adresine POST ile "user_del" kullanicisi olusturulmus
    When kullanici "/user/user_del" adresine DELETE istegi gonderir
    Then donen HTTP durum kodu 200 olmali
    And dogrulama icin "/user/user_del" GET istegi 404 dondurmeli