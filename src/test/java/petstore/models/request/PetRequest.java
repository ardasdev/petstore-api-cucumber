package petstore.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import petstore.models.PetStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetRequest {
    private long id;
    private PetCategoryRequest category;
    private String name;
    private List<String> photoUrls;
    private List<PetTagRequest> tags;
    private PetStatus status;
}
