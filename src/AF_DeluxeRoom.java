import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class AF_DeluxeRoom extends Room implements Serializable {
    AF_RoomOptionFactory optionFactory;

    public void prepare(){
        Bed = optionFactory.BuyBed();
        Tv = optionFactory.BuyTv();
        region = optionFactory.Region();
        roomType = "DeluxeRoom";
        capacity = 3;
        price = 150_000;
        roomNumber = makeRoomNumber(region);
        System.out.println("AF_DeluxeRoom"+roomNumber);
    }
}
