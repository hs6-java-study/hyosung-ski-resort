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
    private static final long serialVersionUID = 6741273514427688155L; // Room 클래스의 serialVersionUID 사용
    private String size;
    private int price;
    private Map<String,Boolean> rentalDates;

    public Product(String size, int price) {
        this.size = size;
        this.price = price;
        this.rentalDates = new HashMap<String,Boolean>();
    }
    @Override
    public String toString() {
        return "Product{ size='" + size + '\'' +
                ", price=" + price +
                ", rentalDates=" + rentalDates +
                '}';
    }
}

