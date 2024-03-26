public class Main {
    public static void main(String[] args) {
// ===========추상 팩토리 Test 실행 코드=============
//        AF_BuildRoom mjBuild = new AF_BuildMuJuRoom();
//        AF_BuildRoom gcBuild = new AF_BuildGangChonRoom();
//
//        Room room = mjBuild.orderRoom("standard");
//        System.out.println(room.toString());
//        Room room2 = mjBuild.orderRoom("deluxe");
//        System.out.println(room2.toString());
//        Room room3 = gcBuild.orderRoom("standard");
//        System.out.println(room3.toString());
//        Room room4 = gcBuild.orderRoom("Family");
//        System.out.println(room4.toString());

        Menu menu = new Menu();
        menu.run();
    }
}