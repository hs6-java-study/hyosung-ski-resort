package TestAF;

public class MuJuRoomOptionFactory implements  RoomOptionFactory {
    public String BuyTv() {
        return "SamsungTV";
    }

    public String BuyBed() {
        return "지누스 침대";
    }

    public String Region() {
        return "MuJu";
    }
}