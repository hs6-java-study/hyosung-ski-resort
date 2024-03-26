import TestAF.BuildGangChonRoom;
import TestAF.BuildMuJuRoom;
import TestAF.BuildRoom;
import TestAF.RoomTest;

public class Main {
    public static void main(String[] args) {
// ===========추상 팩토리 Test 실행 코드=============
//        BuildRoom mjBuild = new BuildMuJuRoom();
//        BuildRoom gcBuild = new BuildGangChonRoom();
//
//        RoomTest room = mjBuild.orderRoom("standard");
//        System.out.println(room.toString());
//        RoomTest room2 = mjBuild.orderRoom("deluxe");
//        System.out.println(room2.toString());
//        RoomTest room3 = gcBuild.orderRoom("standard");
//        System.out.println(room3.toString());
//        RoomTest room4 = gcBuild.orderRoom("Family");
//        System.out.println(room4.toString());

        Menu menu = new Menu();
        menu.run();
    }
}