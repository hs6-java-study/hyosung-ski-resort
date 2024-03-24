package TestAF;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FamilyRoom extends RoomTest {
    RoomOptionFactory optionFactory;

    public void prepare(){
        Bed = optionFactory.BuyBed();
        Tv = optionFactory.BuyTv();
        region = optionFactory.Region();
        roomType = "FamilyRoom";
        capacity = 4;
        price = 200_000;
    }
}
