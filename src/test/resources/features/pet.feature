Feature: Pet islemleri
  Her senaryo kendi pet verisini olusturur, dogrular ve temizler;
  boylece senaryolar birbirinden bagimsizdir.

  Background:
    Given Petstore API erisilebilir durumda
    And temel adres "https://petstore.swagger.io/v2" olarak ayarli

  @TC_01
  Scenario: Yeni bir pet olusturulabilir
    Given kullanici id degeri 2001 olan gecerli bir pet bilgisi hazirliyor
    When kullanici "/pet" adresine POST istegi gonderir
    Then donen HTTP durum kodu 200 olmali
    And donen pet id, name ve status degerleri gonderilenle ayni olmali
    And test sonunda 2001 idli pet "/pet/2001" ile silinerek ortam temizlenir

  @TC_02
  Scenario: Bir pet ID ile getirilebilir
    Given "/pet" adresine POST ile id degeri 2002 olan bir pet onceden olusturulmus
    When kullanici "/pet/2002" adresine GET istegi gonderir
    Then donen HTTP durum kodu 200 olmali
    And donen pet bilgileri name, status ve photoUrls alanlarini icermeli
    And test sonunda 2002 idli pet "/pet/2002" ile silinerek ortam temizlenir

  @TC_03
  Scenario: Var olan bir pet guncellenebilir
    Given "/pet" adresine POST ile id degeri 2003 ve status "available" olan bir pet olusturulmus
    And kullanici status alanini "sold" olarak guncellemek uzere hazirliyor
    When kullanici "/2" adresine PUT istegi gonderir
    Then donen HTTP durum kodu 200 olmali
    And pet status alani "sold" olarak guncellenmeli
    And test sonunda 2003 idli pet "/pet/2003" ile silinerek ortam temizlenir

  @TC_04
  Scenario: Bir pet silinebilir
    Given "/pet" adresine POST ile id degeri 2004 olan bir pet olusturulmus
    When kullanici "/pet/2004" adresine DELETE istegi gonderir
    Then donen HTTP durum kodu 200 olmali
    And silinen pet id degeri 2004 olmali
    And dogrulama icin "/pet/2004" GET istegi 404 dondurmeli