import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Clothes extends Product implements Serializable {
    Clothes(int serialNum, String size,int price, Map<String,Boolean> rentalDates){
        super(serialNum, size,price,rentalDates);
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
