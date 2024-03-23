import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Helmet extends Product implements Serializable {

    Helmet(String serialNum, String size,int price, Map<Date,Boolean> rentalDates){
        super(serialNum, size,price,rentalDates);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
