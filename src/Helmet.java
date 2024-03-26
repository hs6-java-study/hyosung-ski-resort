import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Helmet extends Product implements Serializable {

    Helmet(String size,int price, Map<String,Boolean> rentalDates){
        super(size,price,rentalDates);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
