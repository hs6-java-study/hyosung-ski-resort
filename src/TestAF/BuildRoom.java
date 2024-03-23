package TestAF;

public abstract class BuildRoom {
    protected abstract RoomTest createRoom(String roomType);

    public RoomTest orderRoom(String roomType){
        RoomTest room = createRoom(roomType);
        room.prepare();
        return room;
    }
}
