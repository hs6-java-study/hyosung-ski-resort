import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class StandardRoom extends Room implements Serializable {
    private static final long serialVersionUID = 6741273514427688155L; // Room 클래스의 serialVersionUID 사용
    RoomOptionFactory optionFactory;

    public void prepare(){
        Bed = optionFactory.BuyBed();
        region = optionFactory.Region();
        roomType = "StandardRoom";
        capacity = 2;
        price = 100_000;
        roomNumber = makeRoomNumber(region);
    }
}
