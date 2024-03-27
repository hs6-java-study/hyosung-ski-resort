import java.io.Serializable;

public class AF_MuJuRoomOptionFactory implements AF_RoomOptionFactory, Serializable {
    public String BuyTv() {
        return "SamsungTV";
    }
    public String BuyBed() {return "지누스 침대";}
    public String Region() {
        return "muju";
    }
}