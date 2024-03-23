import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    private String serialNum;
    private String size;
    private int price;
    private Map<Date,Boolean> rentalDates;

    @Override
    public String toString() {
        return "Product{" +
                "serialNum='" + serialNum + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", rentalDates=" + rentalDates +
                '}';
    }
}

