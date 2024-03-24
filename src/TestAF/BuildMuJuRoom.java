package TestAF;

public class BuildMuJuRoom extends BuildRoom {
    protected RoomTest createRoom(String roomType){
        RoomTest room = null;
        RoomOptionFactory optionFactory = new MuJuRoomOptionFactory();

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
