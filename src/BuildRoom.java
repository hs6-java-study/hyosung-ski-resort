public abstract class BuildRoom {
    protected abstract Room createRoom(String roomType);

    public Room orderRoom(String roomType){
        Room room = createRoom(roomType);
        room.prepare();
        return room;
    }
}
