public class BuildMuJuRoom extends BuildRoom {
    protected Room createRoom(String roomType){
        Room room = null;
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
