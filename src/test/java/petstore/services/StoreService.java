package petstore.services;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import petstore.models.request.OrderRequest;

import static io.restassured.RestAssured.given;

public class StoreService extends BaseTest {

    public Response getInventory() {
        return given(spec)
                .when()
                .get("/store/inventory");
    }

    public Response createOrder(OrderRequest request) {
        return given(spec)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/store/order");
    }

    public Response getOrderById(long id) {
        return given(spec)
                .accept(ContentType.JSON)
                .when()
                .get("/store/order/" + id);
    }
    public Response deleteOrder(long id) {
        return given(spec)
                .accept(ContentType.JSON)
                .when()
                .delete("/store/order/" + id);
    }

}