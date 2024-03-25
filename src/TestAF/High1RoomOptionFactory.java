package TestAF;

public class High1RoomOptionFactory implements  RoomOptionFactory{
    public String BuyTv(){
        return "LGtv";
    }
    public String BuyBed() {
        return "에이스 침대";
    }
    public String Region(){
        return "High1";
    }
}