import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class Reservation implements Serializable {
    private Member member;
    private Room room;
    private Map<String,Product> products;
}