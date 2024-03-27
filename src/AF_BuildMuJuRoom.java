import java.io.Serializable;

public class AF_BuildMuJuRoom extends AF_BuildRoom implements Serializable {
    protected Room createRoom(String roomType){
        Room room = null;
        AF_RoomOptionFactory optionFactory = new AF_MuJuRoomOptionFactory();

        if (roomType.equals("standard")){
            room = new AF_StandardRoom(optionFactory);
        }
        else if (roomType.equals("deluxe")){
            room = new AF_DeluxeRoom(optionFactory);
        }
        else if (roomType.equals("Family")){
            room = new AF_FamilyRoom(optionFactory);
        }
        return room;
    }
}
