import java.io.Serializable;

public class GangChonRoomOptionFactory implements RoomOptionFactory, Serializable {
    private static final long serialVersionUID = 6741273514427688155L; // Room 클래스의 serialVersionUID 사용
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