import java.util.Map;
import java.util.Scanner;

public class MenuMember {
    public Scanner sc ;
    public FileIO fileIo;
    private Map<String, Room> roomList;
    private Room room;
    private int pointer;


    MenuMember(){
        sc = new Scanner(System.in);
        fileIo = new FileIO();
    }

    public void run(Member member){
        do{
            System.out.println("1. 데이터 쓰기, 2. 데이터 읽기");
            pointer = Integer.parseInt(sc.nextLine());
            switch (pointer) {
                case 1:
                    makeMyReservation();
                    break;
                case 2:
                    deleteMyReservation();
                    break;
                case 3:
                    System.out.println("뒤로이동");
                    break;
                default:
                    System.out.println("잘못된입력");
            }
        }while(pointer != 3);



    }
    public void makeMyReservation(){
        System.out.println("데이터 쓰기 테스트");
        System.out.print("지역 : ");
        String region = sc.nextLine();
        if(!fileIo.inRegion(region)) return;
        System.out.print("몇호 : ");
        String roomNumber = sc.nextLine();
        //          int roomNumber = Integer.parseInt(sc.nextLine());
        Room room = new Room(region,roomNumber,4,10000);
        roomList = fileIo.roomListReader(region);
        roomList.put(roomNumber,room);
        fileIo.roomListWriter(region, roomList);
    }
    public void deleteMyReservation(){
        System.out.println("데이터 읽기 테스트");
        System.out.print("지역 : ");
        String region = sc.nextLine();
        roomList = fileIo.roomListReader(region);
        for (Map.Entry mem : roomList.entrySet()){
            System.out.println("호수 : " + mem.getKey() + " / " + "방클래스 : " + mem.getValue());
        }
    }
    public void getMyReservations(){

    }
}
