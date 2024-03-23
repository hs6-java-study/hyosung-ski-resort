import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class Room implements Serializable {
    private String region;
    private String roomNumber;
    private int capacity;
    private int price;

    public Room(String region, String roomNumber, int capacity, int price) {
        this.region = region;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.price = price;
    }
}
