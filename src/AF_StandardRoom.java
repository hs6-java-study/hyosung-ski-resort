import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class AF_StandardRoom extends Room implements Serializable {
    AF_RoomOptionFactory optionFactory;

    public void prepare(){
        Bed = optionFactory.BuyBed();
        region = optionFactory.Region();
        roomType = "StandardRoom";
        capacity = 2;
        price = 100_000;
        roomNumber = makeRoomNumber(region);
    }
}
