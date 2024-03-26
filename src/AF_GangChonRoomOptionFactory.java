import java.io.Serializable;

public class AF_GangChonRoomOptionFactory implements AF_RoomOptionFactory, Serializable {
    public String BuyTv(){
        return "LGtv";
    }
    public String BuyBed() {
        return "에이스 침대";
    }
    public String Region(){
        return "gangchon";
    }
}