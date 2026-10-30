package petstore.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import petstore.context.ScenarioContext;
import petstore.models.PetStatus;
import petstore.models.request.PetCategoryRequest;
import petstore.models.request.PetRequest;
import petstore.models.request.PetTagRequest;
import petstore.models.response.PetResponse;
import petstore.services.BaseTest;
import petstore.services.PetService;
import petstore.services.StoreService;
import petstore.services.UserService;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class PetStep extends BaseTest {

    private final PetService petService = new PetService();
    private final StoreService storeService = new StoreService();
    private final UserService userService = new UserService();

    private PetRequest petRequest;
    private PetResponse petResponse;
    private Response response;

    @Before
    public void resetScenarioContext() {
        ScenarioContext.reset();
    }

    @Given("Petstore API erisilebilir durumda")
    public void petstore_api_erisilebilir_durumda() {}

    @Given("temel adres {string} olarak ayarli")
    public void temel_adres_olarak_ayarli(String baseUri) {}

    @Then("donen HTTP durum kodu {int} olmali")
    public void donen_http_durum_kodu_olmali(int expectedStatus) {
        if (response != null) {
            response.then().statusCode(expectedStatus);
        } else if (ScenarioContext.storeRawResponse != null) {
            ScenarioContext.storeRawResponse.then().statusCode(expectedStatus);
        } else if (ScenarioContext.userRawResponse != null) {
            ScenarioContext.userRawResponse.then().statusCode(expectedStatus);
        }

    }

    @Given("kullanici id degeri {int} olan gecerli bir pet bilgisi hazirliyor")
    public void kullanici_id_degeri_olan_gecerli_bir_pet_bilgisi_hazirliyor(int id) {
        petRequest = new PetRequest(
                id,
                new PetCategoryRequest(1, "dogs"),
                "doggie",
                Collections.singletonList("https://example.com/photo.jpg"),
                Collections.singletonList(new PetTagRequest(1, "friendly")),
                PetStatus.available
        );
    }

    @When("kullanici {string} adresine POST istegi gonderir")
    public void kullanici_post_istegi_gonderir(String path) {
        response = null;
        if (ScenarioContext.orderRequest != null) {
            ScenarioContext.storeRawResponse = storeService.createOrder(ScenarioContext.orderRequest);
        } else if (ScenarioContext.userRequest != null) {
            ScenarioContext.userRawResponse = userService.createUser(ScenarioContext.userRequest);
        } else {
            petResponse = petService.createPet(petRequest);


        }
    }

    @Then("donen pet id, name ve status degerleri gonderilenle ayni olmali")
    public void donen_pet_id_name_ve_status_degerleri_gonderilenle_ayni_olmali() {
        assertEquals(petRequest.getId(), petResponse.getId());
        assertEquals(petRequest.getName(), petResponse.getName());
        assertEquals(petRequest.getStatus(), petResponse.getStatus());
    }

    @Then("test sonunda {int} idli pet {string} ile silinerek ortam temizlenir")
    public void test_sonunda_idli_pet_ile_silinerek_ortam_temizlenir(int id, String path) {
        try {
            petService.deletePet(id);
        } catch (Exception ignored) {}
    }


    @Given("{string} adresine POST ile id degeri {int} olan bir pet onceden olusturulmus")
    public void pet_onceden_olusturulmus(String path, int id) {
        petRequest = buildDefaultPetRequest(id, PetStatus.available);
        petService.createPet(petRequest);
    }

    @When("kullanici {string} adresine GET istegi gonderir")
    public void kullanici_get_istegi_gonderir(String path) {
        response = null;
        if (path.equals("/store/inventory")) {
            ScenarioContext.storeRawResponse = storeService.getInventory();
        } else if (path.startsWith("/store/order/")) {
            long id = Long.parseLong(path.substring(path.lastIndexOf('/') + 1));
            ScenarioContext.storeRawResponse = storeService.getOrderById(id);
        } else if (path.startsWith("/user/")) {
            String username = path.substring("/user/".length());
            ScenarioContext.userRawResponse = userService.getUserByUsername(username);
        } else {
            long id = Long.parseLong(path.substring(path.lastIndexOf('/') + 1));
            petResponse = petService.getPetById(id);
        }
    }

    @Then("donen pet bilgileri name, status ve photoUrls alanlarini icermeli")
    public void donen_pet_bilgileri_icermeli() {
        assertNotNull(petResponse.getName());
        assertNotNull(petResponse.getStatus());
        assertNotNull(petResponse.getPhotoUrls());
        assertFalse(petResponse.getPhotoUrls().isEmpty());
    }



    @Given("{string} adresine POST ile id degeri {int} ve status {string} olan bir pet olusturulmus")
    public void pet_ile_status_olusturulmus(String path, int id, String status) {
        petRequest = buildDefaultPetRequest(id, PetStatus.valueOf(status));
        petService.createPet(petRequest);
    }

    @And("kullanici status alanini {string} olarak guncellemek uzere hazirliyor")
    public void kullanici_status_guncellemek_uzere_hazirliyor(String status) {
        petRequest.setStatus(PetStatus.valueOf(status));
    }

    @When("kullanici {string} adresine PUT istegi gonderir")
    public void kullanici_put_istegi_gonderir(String path) {
        response = null;
        if (ScenarioContext.userRequest != null) {
            String username = path.substring(path.lastIndexOf('/') + 1);
            ScenarioContext.userRawResponse = userService.updateUser(username, ScenarioContext.userRequest);
        } else {
            petResponse = petService.updatePet(petRequest);
        }
    }

    @Then("pet status alani {string} olarak guncellenmeli")
    public void pet_status_alani_olarak_guncellenmeli(String expectedStatus) {
        assertEquals(PetStatus.valueOf(expectedStatus), petResponse.getStatus());
    }



    @Given("{string} adresine POST ile id degeri {int} olan bir pet olusturulmus")
    public void pet_olusturulmus(String path, int id) {
        petRequest = buildDefaultPetRequest(id, PetStatus.available);
        petService.createPet(petRequest);
    }

    @When("kullanici {string} adresine DELETE istegi gonderir")
    public void kullanici_delete_istegi_gonderir(String path) {
        if (path.startsWith("/store/order/")) {
            long id = Long.parseLong(path.substring(path.lastIndexOf('/') + 1));
            ScenarioContext.storeRawResponse = storeService.deleteOrder(id);
        } else if (path.startsWith("/user/")) {
            String username = path.substring("/user/".length());
            ScenarioContext.userRawResponse = userService.deleteUser(username);
        } else {
            long id = Long.parseLong(path.substring(path.lastIndexOf('/') + 1));
            response = petService.deletePet(id);
        }
    }

    @Then("silinen pet id degeri {int} olmali")
    public void silinen_pet_id_degeri_olmali(int expectedId) {
        String message = response.jsonPath().getString("message");
        assertEquals(String.valueOf(expectedId), message);
    }

    @Then("dogrulama icin {string} GET istegi {int} dondurmeli")
    public void dogrulama_icin_get_istegi_dondurmeli(String path, int expectedStatus) {
        try {
            int actualStatus = given(spec).when().get(path).statusCode();
            assertEquals(expectedStatus, actualStatus);
        } catch (Exception e) {
            String msg = e.getMessage() != null ? e.getMessage() : "";
            assertTrue("Beklenen status: " + expectedStatus + ", gelen: " + msg,
                    msg.startsWith("status code: " + expectedStatus));
        }
    }


    private PetRequest buildDefaultPetRequest(long id, PetStatus status) {
        return new PetRequest(
                id,
                new PetCategoryRequest(1, "dogs"),
                "doggie",
                Collections.singletonList("https://example.com/photo.jpg"),
                Collections.singletonList(new PetTagRequest(1, "friendly")),
                status
        );
    }
}