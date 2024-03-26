package TestAF;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeluxeRoom extends RoomTest {
    RoomOptionFactory optionFactory;

    public void prepare(){
        Bed = optionFactory.BuyBed();
        Tv = optionFactory.BuyTv();
        region = optionFactory.Region();
        roomType = "TestAF.DeluxeRoom";
        capacity = 3;
        price = 150_000;
        roomNumber = makeRoomNumber(region);
    }
}
