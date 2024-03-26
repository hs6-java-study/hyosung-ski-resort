public abstract class AF_BuildRoom{
    protected abstract Room createRoom(String roomType);

    public Room orderRoom(String roomType){
        Room room = createRoom(roomType);
        room.prepare();
        setRoomNumber(room);
        return room;
    }

    // 자동으로 호수 안겹치게 설정해주는 logic 필요
    private void setRoomNumber(Room room){
        System.out.println(room.region + "빌더룸이구만");
    }
}
