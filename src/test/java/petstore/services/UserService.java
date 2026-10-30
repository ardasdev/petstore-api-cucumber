package petstore.services;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import petstore.models.request.UserRequest;

import static io.restassured.RestAssured.given;

public class UserService extends BaseTest {

    public Response createUser(UserRequest request) {
        return given(spec)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/user");
    }

    public Response loginUser(String username, String password) {
        return given(spec)
                .accept(ContentType.JSON)
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get("/user/login");
    }

    public Response getUserByUsername(String username) {
        return given(spec)
                .accept(ContentType.JSON)
                .when()
                .get("/user/" + username);
    }

    public Response updateUser(String username, UserRequest request) {
        return given(spec)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/user/" + username);
    }

    public Response deleteUser(String username) {
        return given(spec)
                .accept(ContentType.JSON)
                .when()
                .delete("/user/" + username);
    }
}