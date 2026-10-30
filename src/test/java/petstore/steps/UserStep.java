package petstore.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import petstore.context.ScenarioContext;
import petstore.models.request.UserRequest;
import petstore.models.response.UserResponse;
import petstore.services.UserService;

import static org.junit.Assert.*;

public class UserStep {

    private final UserService userService = new UserService();

    @Given("kullanici username degeri {string} olan gecerli bir kullanici bilgisi hazirliyor")
    public void kullanici_username_degeri_olan_gecerli_bir_kullanici_bilgisi_hazirliyor(String username) {
        ScenarioContext.userRequest = buildDefaultUserRequest(username);
        ScenarioContext.currentUsername = username;
    }

    @Then("donen govde code, type ve message alanlarini icermeli")
    public void donen_govde_code_type_ve_message_alanlarini_icermeli() {
        int code = ScenarioContext.userRawResponse.jsonPath().getInt("code");
        String type = ScenarioContext.userRawResponse.jsonPath().getString("type");
        String message = ScenarioContext.userRawResponse.jsonPath().getString("message");
        assertTrue("code pozitif olmali", code > 0);
        assertNotNull("type null olmamali", type);
        assertNotNull("message null olmamali", message);
    }

    @Then("test sonunda {string} kullanicisi {string} ile silinerek ortam temizlenir")
    public void test_sonunda_kullanicisi_ile_silinerek_ortam_temizlenir(String username, String path) {
        try {
            userService.deleteUser(username);
        } catch (Exception ignored) {}
    }


    @Given("{string} adresine POST ile {string} kullanicisi sifresiyle olusturulmus")
    public void adresine_post_ile_kullanici_sifresiyle_olusturulmus(String path, String username) {
        ScenarioContext.userRequest = buildDefaultUserRequest(username);
        ScenarioContext.currentUsername = username;
        ScenarioContext.userRawResponse = userService.createUser(ScenarioContext.userRequest);

    }

    @When("kullanici {string} adresine {string} ve sifresiyle GET istegi gonderir")
    public void kullanici_login_get_istegi_gonderir(String path, String username) {
        ScenarioContext.userRawResponse = userService.loginUser(username, "password123");
    }

    @Then("donen govde {string} mesajini icermeli")
    public void donen_govde_mesajini_icermeli(String expectedMessage) {
        String body = ScenarioContext.userRawResponse.getBody().asString();
        assertTrue("Yanit '" + expectedMessage + "' icermeli", body.contains(expectedMessage));
    }

    @Given("{string} adresine POST ile {string} kullanicisi olusturulmus")
    public void adresine_post_ile_kullanici_olusturulmus(String path, String username) {
        ScenarioContext.userRequest = buildDefaultUserRequest(username);
        ScenarioContext.currentUsername = username;
        ScenarioContext.userRawResponse = userService.createUser(ScenarioContext.userRequest);
    }

    @Then("donen kullanici bilgileri firstName, lastName ve email alanlarini icermeli")
    public void donen_kullanici_bilgileri_firstname_lastname_ve_email_alanlarini_icermeli() {
        UserResponse user = ScenarioContext.userRawResponse.as(UserResponse.class);
        assertNotNull("firstName null olmamali", user.getFirstName());
        assertNotNull("lastName null olmamali", user.getLastName());
        assertNotNull("email null olmamali", user.getEmail());
    }

    @And("kullanici guncel firstName ve email bilgilerini hazirliyor")
    public void kullanici_guncel_firstname_ve_email_bilgilerini_hazirliyor() {
        ScenarioContext.userRequest.setFirstName("UpdatedFirstName");
        ScenarioContext.userRequest.setEmail("updated@example.com");
    }

    @Then("kullanicinin firstName ve email alanlari guncellenmis olmali")
    public void kullanicinin_firstname_ve_email_alanlari_guncellenmis_olmali() {
        UserResponse user = userService.getUserByUsername(ScenarioContext.currentUsername)
                .as(UserResponse.class);
        assertEquals(ScenarioContext.userRequest.getFirstName(), user.getFirstName());
        assertEquals(ScenarioContext.userRequest.getEmail(), user.getEmail());
    }

    private UserRequest buildDefaultUserRequest(String username) {
        return new UserRequest(
                Math.abs((long) username.hashCode()),
                username,
                "Arda",
                "Özel",
                username + "@example.com",
                "password123",
                "5551234567",
                1
        );
    }
}