package petstore.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import petstore.models.PetStatus;
import petstore.models.request.PetCategoryRequest;
import petstore.models.request.PetTagRequest;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PetResponse {

    private long id;
    private PetCategoryRequest category;
    private String name;
    private List<String> photoUrls;
    private List<PetTagRequest> tags;
    private PetStatus status;
}