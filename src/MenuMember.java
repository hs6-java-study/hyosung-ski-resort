import java.util.*;

public class MenuMember {
    public Scanner sc ;
    public FileIO fileIo;
    private Map<Integer, Room> roomList;
    private Member member;
    private Room room;
    private List<Product> products;
    private List<Reservation> reservationList;
    private int pointer;


    MenuMember(){
        sc = new Scanner(System.in);
        fileIo = new FileIO();
    }

    public void run(Member member){
        this.member = member;
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
                case 7:
                    tmp_checkRoom();
                    break;
                case 8:
                    tmp_checkRoom();
                    break;
                default:
                    System.out.println("잘못된입력");
            }
        }while(pointer != 4);
    }
    public void makeMyReservation(){
        room = new Room("muju",123,4,1000);
        member = new Member("asdf","Asdf","asdf","asdf",false);
        Product helmet = new Helmet("asdf","M",1561,new HashMap<Date,Boolean>());
        products = new ArrayList<Product>();
        products.add(helmet);
        products.add(helmet);
        products.add(helmet);
        Reservation reservation = new Reservation(member, room, products);
        reservationList = fileIo.reservationListReader("muju");
        reservationList.add(reservation);
        fileIo.reservationListWriter("muju",reservationList);
    }
    public void deleteMyReservation(){
        List<Reservation> list = fileIo.reservationListReader("muju");
        for (Reservation l : list){
            System.out.println(l.toString());
        }
    }
    public void getMyReservations(){

    }

    // 나중에 다른곳으로 빠질 애들
    public void tmp_addRoom(){
        System.out.println("방 만들기 Test");
        System.out.print("지역(muju/high1) : ");
        String region = sc.nextLine();
        if(!fileIo.inRegion(region)) return;
        System.out.print("몇호(숫자만) : ");
        int roomNumber = Integer.parseInt(sc.nextLine());
        System.out.print("인원(숫자만) : ");
        int capacity = Integer.parseInt(sc.nextLine());
        System.out.print("가격(숫자만) : ");
        int price = Integer.parseInt(sc.nextLine());

        Room room = new Room(region,roomNumber,capacity,price);
        roomList = fileIo.roomListReader(region);
        roomList.put(roomNumber,room);
        fileIo.roomListWriter(region, roomList);
    }

    public void tmp_checkRoom(){
        System.out.println("방 데이터 Test");
        System.out.print("지역 : ");
        String region = sc.nextLine();
        roomList = fileIo.roomListReader(region);
        for (Map.Entry mem : roomList.entrySet()){
            System.out.println("호수 : " + mem.getKey() + " / " + "방클래스 : " + mem.getValue());
        }
    }
}
