import java.io.Serializable;

public class MuJuRoomOptionFactory implements RoomOptionFactory, Serializable {
    private static final long serialVersionUID = 6741273514427688155L; // Room 클래스의 serialVersionUID 사용
    public String BuyTv() {
        return "SamsungTV";
    }
    public String BuyBed() {return "지누스 침대";}
    public String Region() {
        return "muju";
    }
}