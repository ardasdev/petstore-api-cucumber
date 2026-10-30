package petstore.services;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import petstore.models.request.PetRequest;
import petstore.models.response.PetResponse;

import static io.restassured.RestAssured.given;

public class PetService extends BaseTest {


    public PetResponse createPet(PetRequest request) {
        return given(spec)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/pet")
                .as(PetResponse.class);
    }

    public PetResponse getPetById(long id) {
        return given(spec)
                .accept(ContentType.JSON)
                .when()
                .get("/pet/" + id)
                .as(PetResponse.class);
    }

    public PetResponse updatePet(PetRequest request) {
        return given(spec)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/pet")
                .as(PetResponse.class);
    }

    public Response deletePet(long petId) {
        return given(spec)
                .accept(ContentType.JSON)
                .header("api_key", "1")
                .when()
                .delete("/pet/" + petId);
    }
}