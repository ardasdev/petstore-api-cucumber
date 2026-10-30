package petstore.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import petstore.models.OrderStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private long id ;
    private long petId;
    private int quantity;
    private OrderStatus status ;
    private boolean complete ;
}
