import java.util.Map;
import java.util.Scanner;

public class MenuMember {
    public Scanner sc ;
    public FileIO fileIo;
    private Map<Integer, Room> roomList;
    private Room room;
    private int pointer;


    MenuMember(){
        sc = new Scanner(System.in);
        fileIo = new FileIO();
    }

    public void run(Member member){
        do{
            System.out.println("1. 예약하기, 2. 예약취소, 3. 예약조회, 4. 뒤로, 5. tmp_방 추가, 6. tmp_방 조회");
            pointer = Integer.parseInt(sc.nextLine());
            switch (pointer) {
                case 1:
                    makeMyReservation();
                    break;
                case 2:
                    deleteMyReservation();
                    break;
                case 3:
                    break;
                case 4:
                    System.out.println("뒤로이동");
                    break;
                case 5:
                    tmp_addRoom();
                    break;
                case 6:
                    tmp_checkRoom();
                    break;
                default:
                    System.out.println("잘못된입력");
            }
        }while(pointer != 3);
    }
    public void makeMyReservation(){

    }
    public void deleteMyReservation(){

    }
    public void getMyReservations(){

    }
    public void tmp_addRoom(){
        System.out.println("데이터 쓰기 테스트");
        System.out.print("지역 : ");
        String region = sc.nextLine();
        if(!fileIo.inRegion(region)) return;
        System.out.print("몇호 : ");
        int roomNumber = Integer.parseInt(sc.nextLine());
        Room room = new Room(region,roomNumber,4,10000);
        roomList = fileIo.roomListReader(region);
        roomList.put(roomNumber,room);
        fileIo.roomListWriter(region, roomList);
    }

    public void tmp_checkRoom(){
        System.out.println("데이터 읽기 테스트");
        System.out.print("지역 : ");
        String region = sc.nextLine();
        roomList = fileIo.roomListReader(region);
        for (Map.Entry mem : roomList.entrySet()){
            System.out.println("호수 : " + mem.getKey() + " / " + "방클래스 : " + mem.getValue());
        }
    }
}
