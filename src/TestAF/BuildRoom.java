package TestAF;

public abstract class BuildRoom {
    protected abstract RoomTest createRoom(String roomType);

    public RoomTest orderRoom(String roomType){
        RoomTest room = createRoom(roomType);
        room.prepare();
        setRoomNumber(room);
        return room;
    }

    // 자동으로 호수 안겹치게 설정해주는 logic 필요
    private void setRoomNumber(RoomTest room){
        System.out.println(room.region);
    }
}
