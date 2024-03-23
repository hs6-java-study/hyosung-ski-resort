import TestAF.BuildHigh1Room;
import TestAF.BuildMuJuRoom;
import TestAF.BuildRoom;
import TestAF.RoomTest;
import java.util.Map;
import java.util.Scanner;

public class AuthAdmin {
    public Scanner sc;
    public FileIO fileIo ;
    private Map<String, Admin> adminList;
    private MenuAdmin menuAdmin;
    private Admin admin;
    private int pointer;

    AuthAdmin(){
        sc = new Scanner(System.in);
        fileIo = new FileIO();
    }

    public void run(){
        do{
            System.out.println("1. 관리자 로그인 / 2. 관리자 회원가입 / 3. 뒤로");
            pointer = Integer.parseInt(sc.nextLine());
            switch (pointer){
                case 1:
//                    ===========추상 팩토리 Test 실행 코드=============
//                    BuildRoom mjBuild = new BuildMuJuRoom();
//                    BuildRoom H1Build = new BuildHigh1Room();
//
//                    RoomTest room = mjBuild.orderRoom("standard");
//                    System.out.println(room.toString());
//                    RoomTest room2 = mjBuild.orderRoom("deluxe");
//                    System.out.println(room2.toString());
//                    RoomTest room3 = H1Build.orderRoom("standard");
//                    System.out.println(room3.toString());
//                    RoomTest room4 = H1Build.orderRoom("Family");
//                    System.out.println(room4.toString());
                    login();
                    break;
                case 2:
                    signup();
                    break;
                case 3:
                    System.out.println("뒤로이동");
                    break;
                default:
                    System.out.println("잘못된입력");
            }
        }while(pointer != 3);
    }

    public void signup(){
        System.out.print("ID입력 : ");
        String ID = sc.nextLine();
        admin = new Admin("asdf","45","gusehd502",true);
        adminList = fileIo.adminListReader();
        adminList.put(ID, admin);
        fileIo.adminListWriter(adminList);
    }

    public void login(){
        adminList = fileIo.adminListReader();
        for (Map.Entry ad : adminList.entrySet()){
            System.out.println("관리자ID : " + ad.getKey() + " / " + "관리자클래스 : " + ad.getValue());
        }
        menuAdmin = new MenuAdmin();
        menuAdmin.run();
    }
}
