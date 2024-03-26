import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public abstract class Room implements Serializable {
    String region;
    int roomNumber;
    int capacity;
    int price;
    Map<String,Boolean> reservationDates ;
    String roomType;
    String Tv;
    String Bed;

    public Room() {
        this.reservationDates = new HashMap<String,Boolean>();
    }
    abstract void prepare();

    // 자동으로 호수 안겹치게 설정해주는 logic 필요
    public int makeRoomNumber(String region){
        FileIO fileIo = new FileIO();
        Map<Integer, Room> roomList = fileIo.roomListReader(region);
        int initialRoomNumber = 201;
        int roomRange = 15;
        int roomCount = 0;

        // 파일을 불러왔는데 데이터가 없는 경우
        if (roomList.size() == 0) {
            return initialRoomNumber;
        }
        roomCount = roomList.size();  // ex ) 15
        // 호실번호 계산
        int roomNumber = initialRoomNumber + (roomCount / roomRange) * 100 + (roomCount % roomRange);
        return roomNumber;
    }
}
