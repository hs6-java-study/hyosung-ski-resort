import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class FamilyRoom extends Room implements Serializable {
    private static final long serialVersionUID = 6741273514427688155L; // Room 클래스의 serialVersionUID 사용
    RoomOptionFactory optionFactory;

    public void prepare(){
        Bed = optionFactory.BuyBed();
        Tv = optionFactory.BuyTv();
        region = optionFactory.Region();
        roomType = "FamilyRoom";
        capacity = 4;
        price = 5_000_000;
        roomNumber = makeRoomNumber(region);
    }
}
