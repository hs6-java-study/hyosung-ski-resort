package TestAF;

import lombok.Data;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
@Data
public abstract class RoomTest {
    String region;
    String roomType;
    int roomNumber;
    int capacity;
    int price;
    String Tv;
    String Bed;
    private Map<String,Boolean> reservationDates ;

    public RoomTest(){
        this.reservationDates = new HashMap<String,Boolean>();
    }
    abstract void prepare();

    // 자동으로 호수 안겹치게 설정해주는 logic 필요
    public int makeRoomNumber(String region){

        return 8;
    }
}
