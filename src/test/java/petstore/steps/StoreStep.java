package petstore.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import petstore.context.ScenarioContext;
import petstore.models.OrderStatus;
import petstore.models.request.OrderRequest;
import petstore.models.response.OrderResponse;
import petstore.services.StoreService;

import java.util.Map;

import static org.junit.Assert.*;

public class StoreStep {

    private final StoreService storeService = new StoreService();

    @Given("magaza envanteri mevcut")
    public void magaza_envanteri_mevcut() {

    }

    @Then("donen govde statu adlarini sayisal adetlere esleyen bir nesne olmali")
    public void donen_govde_statu_adlarini_sayisal_adetlere_esleyen_bir_nesne_olmali() {
        Map<String, ?> inventory = ScenarioContext.storeRawResponse.jsonPath().getMap("$");
        assertNotNull(inventory);
        assertFalse("Envanter bos olmamali", inventory.isEmpty());
    }

    @Given("kullanici id degeri {int} ve status {string} olan gecerli bir siparis hazirliyor")
    public void kullanici_id_degeri_ve_status_olan_gecerli_bir_siparis_hazirliyor(int id, String status) {
        ScenarioContext.orderRequest = new OrderRequest(id, 1L, 1, OrderStatus.valueOf(status), false);
    }

    @Then("donen siparis id, petId ve status degerleri gonderilenle ayni olmali")
    public void donen_siparis_id_petid_ve_status_degerleri_gonderilenle_ayni_olmali() {
        OrderResponse orderResponse = ScenarioContext.storeRawResponse.as(OrderResponse.class);
        assertEquals(ScenarioContext.orderRequest.getId(), orderResponse.getId());
        assertEquals(ScenarioContext.orderRequest.getPetId(), orderResponse.getPetId());
        assertEquals(ScenarioContext.orderRequest.getStatus(), orderResponse.getStatus());
    }

    @Then("test sonunda {int} idli siparis {string} ile silinerek ortam temizlenir")
    public void test_sonunda_idli_siparis_ile_silinerek_ortam_temizlenir(int id, String path) {
        try {
            storeService.deleteOrder(id);
        } catch (Exception ignored) {}
    }


    @Given("{string} adresine POST ile id degeri {int} olan bir siparis olusturulmus")
    public void adresine_post_ile_id_degeri_olan_bir_siparis_olusturulmus(String path, int id) {
        OrderRequest request = new OrderRequest(id, 1L, 1, OrderStatus.placed, false);
        ScenarioContext.storeRawResponse = storeService.createOrder(request);
    }

    @Then("donen siparis bilgileri id degeri {int} olan kaydi icermeli")
    public void donen_siparis_bilgileri_id_degeri_olan_kaydi_icermeli(int expectedId) {
        OrderResponse orderResponse = ScenarioContext.storeRawResponse.as(OrderResponse.class);
        assertEquals(expectedId, orderResponse.getId());
    }

}