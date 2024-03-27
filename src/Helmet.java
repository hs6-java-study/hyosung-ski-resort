import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Helmet extends Product implements Serializable {
    private static final long serialVersionUID = 6741273514427688155L; // Room 클래스의 serialVersionUID 사용

    Helmet(String size,int price, Map<String,Boolean> rentalDates){
        super(size,price,rentalDates);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
