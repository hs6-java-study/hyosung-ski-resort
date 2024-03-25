import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    private int serialNum;
    private String size;
    private int price;
    private Map<String,Boolean> rentalDates;

    public Product(int serialNum, String size, int price) {
        this.serialNum = serialNum;
        this.size = size;
        this.price = price;
        this.rentalDates = new HashMap<String,Boolean>();
    }
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

