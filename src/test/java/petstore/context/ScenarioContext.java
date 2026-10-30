package petstore.context;

import io.restassured.response.Response;
import petstore.models.request.OrderRequest;
import petstore.models.request.UserRequest;

public class ScenarioContext {

    public static OrderRequest orderRequest;
    public static UserRequest userRequest;
    public static String currentUsername;
    public static Response storeRawResponse;
    public static Response userRawResponse;

    public static void reset() {
        orderRequest = null;
        userRequest = null;
        currentUsername = null;
        storeRawResponse = null;
        userRawResponse = null;
    }
}