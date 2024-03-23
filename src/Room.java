import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class Room implements Serializable {
    private String region;
    private int roomNumber;
    private int capacity;
    private int price;
    private Map<Date,Boolean> Dates ;

    public Room(String region, int roomNumber, int capacity, int price) {
        this.region = region;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.price = price;
        this.Dates = new HashMap<Date,Boolean>();
    }
}
