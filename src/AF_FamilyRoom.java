import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class AF_FamilyRoom extends Room implements Serializable {
    AF_RoomOptionFactory optionFactory;

    public void prepare(){
        Bed = optionFactory.BuyBed();
        Tv = optionFactory.BuyTv();
        region = optionFactory.Region();
        roomType = "FamilyRoom";
        capacity = 4;
        price = 200_000;
        roomNumber = makeRoomNumber(region);
    }
}
