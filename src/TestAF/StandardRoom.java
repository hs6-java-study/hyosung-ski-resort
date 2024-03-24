package TestAF;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StandardRoom extends RoomTest {
    RoomOptionFactory optionFactory;

    public void prepare(){
        Bed = optionFactory.BuyBed();
        region = optionFactory.Region();
        roomType = "StandardRoom";
        capacity = 2;
        price = 100_000;
    }
}
