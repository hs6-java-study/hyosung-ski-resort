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
//    public FileIO fileIo;
    private Map<String,Boolean> reservationDates ;

    public RoomTest(){
        this.reservationDates = new HashMap<String,Boolean>();
//        this.fileIo = new FileIO();
    }
    abstract void prepare();

    // 자동으로 호수 안겹치게 설정해주는 logic 필요
//    public int makeRoomNumber(String region){
//        Map<Integer, Room> roomList = fileIo.roomListReader(region);
//        int initialRoomNumber = 201;
//        int roomRange = 15;
//        int roomCount = roomList.size();  // ex ) 15
//
//        // 파일을 불러왔는데 데이터가 없는 경우
//        if (roomCount == 0) {
//            return initialRoomNumber;
//        }
//
//        // 호실번호 계산
//        int roomNumber = initialRoomNumber + (roomCount / roomRange) * 100 + (roomCount % roomRange);
//        return roomNumber;
//    }
}
