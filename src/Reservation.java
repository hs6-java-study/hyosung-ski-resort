import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class Reservation implements Serializable {
    private Member member;
    private Room room;
    private List<Product> products;
}