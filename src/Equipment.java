import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Equipment extends Product implements Serializable {
    Equipment(int serialNum, String size,int price, Map<String,Boolean> rentalDates){
        super(serialNum, size,price,rentalDates);
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
