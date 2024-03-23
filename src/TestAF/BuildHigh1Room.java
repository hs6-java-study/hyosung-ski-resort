package TestAF;

public class BuildHigh1Room extends BuildRoom {
    protected RoomTest createRoom(String roomType){
        RoomTest room = null;
        RoomOptionFactory optionFactory = new High1RoomOptionFactory();

        if (roomType.equals("standard")){
            room = new StandardRoom(optionFactory);
        }
        else if (roomType.equals("deluxe")){
            room = new DeluxeRoom(optionFactory);
        }
        else if (roomType.equals("Family")){
            room = new FamilyRoom(optionFactory);
        }
        return room;
    }
}
