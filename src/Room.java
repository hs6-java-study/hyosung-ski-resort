import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class Room implements Serializable {
    private String region;
    private int roomNumber;
    private int capacity;
    private int price;
    private Map<String,Boolean> reservationDates ;

    public Room(String region, int roomNumber, int capacity, int price) {
        this.region = region;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.price = price;
        this.reservationDates = new HashMap<String,Boolean>();
    }
}
