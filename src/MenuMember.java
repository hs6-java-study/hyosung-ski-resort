import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class MenuMember {
    public Scanner sc ;
    public FileIO fileIo;
    private Map<Integer, Room> roomList;
    private Member member;
    private Room room;
    private List<Product> products;
    private Map<Integer,Reservation> reservationList;
    private int pointer;
    private String region;
    private int capacity;
    private String period;
    private Map<Calendar, Boolean> reservationDates;
    private Random random ;
    private ManageDate manageDate;


    MenuMember(){
        sc = new Scanner(System.in);
        fileIo = new FileIO();
        random = new Random();
    }

    public void run(Member member){
        this.member = member;
        do{
            System.out.println("===== 회원 메뉴 =====");
            System.out.println("1. 예약하기, 2. 예약취소, 3. 예약조회, 4. 로그아웃, 5. tmp_방 추가, 6. tmp_방 조회");
            this.pointer = Integer.parseInt(sc.nextLine());
            switch (pointer) {
                case 1:
                    makeMyReservation(member);
                    break;
                case 2:
                    deleteMyReservation();
                    break;
                case 3:
                    getMyReservations();
                    break;
                case 4:
                    System.out.println(member.getUserId() + "님 로그아웃 되었습니다!");
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
        }while(pointer != 4);
    }

    public void makeMyReservation(Member member){
        while (true){
            System.out.println("지점, 인원, 기간을 입력해 주세요");
//            this.region = choiceRegion();
//            this.capacity = choiceCapacity();
//            this.period = choosePeriod();

//            getRoomList(region, capacity, period);

            getRoomList("muju", 5, "2024.01.28~2024.02.06");

//            System.out.print("예약을 원하는 방의 호 수 입력");
//            System.out.println("호 수 : ");
//            int roomNumber = Integer.parseInt(sc.nextLine());
//            if (roomList.containsKey(roomNumber)) break;
            if (roomList.containsKey(102)) break;
            else System.out.println("방이 없다.");
        }

        room = new Room("muju",123,4,1000);
        member = member;
        Product helmet = new Helmet("asdf","M",1561,new HashMap<Date,Boolean>());
        products = new ArrayList<Product>();
        products.add(helmet);
        products.add(helmet);
        products.add(helmet);
        fileIo.helmetListWriter("muju",helmet);

        Reservation reservation = new Reservation(member, room, products);
        reservationList = fileIo.reservationListReader("muju");

        int randomNumber = random.nextInt(100000000);
        reservationList.put(randomNumber,reservation);
        fileIo.reservationListWriter("muju",reservationList);
    }

    // makeMyReservation 헬퍼 함수
    private void getRoomList(String region, int capacity, String period){
        roomList = fileIo.roomListReader(region);

        roomList.get(102).setReservationDates(new HashMap<Calendar,Boolean>(){{
            Calendar calendar = Calendar.getInstance();
            calendar.set(2024, 1, 29);
            put(calendar,true);
        }});

        // 인원 수 필터
        roomList = roomList.entrySet().stream()
                .filter(room -> room.getValue().getCapacity() == capacity)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // 기간 필터
        String[] tmp = period.split("~");
        manageDate = new ManageDate(tmp[0],tmp[1]);
        manageDate.getStartAndEndDate();
        roomList = roomList.entrySet().stream()
                .filter(room -> manageDate.isPossibleReservation(room.getValue().getReservationDates()) == true)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (Integer roomNumber : roomList.keySet()){
            System.out.println(roomList.get(roomNumber).toString());
        }

//        for (Integer roomNumber : roomList.keySet()){
//            if(manageDate.isPossibleReservation(roomList.get(roomNumber).getReservationDates())){
//
//            }
//        }
//
//        List<Calendar> startEndDates = manageDate.getStartAndEndDate();
//
//        // start - end 날짜 List
//        for (Calendar date : startEndDates) {
//            System.out.println(date.getTime());
//        }


    }

    // makeMyReservation 헬퍼 함수
    private String choiceRegion (){
        do{
            System.out.print("지점 => 1. 무주 , 2. 강촌 >>> 번호입력 : ");
            pointer = Integer.parseInt(sc.nextLine());
            switch (pointer){
                case 1:
                    region = "muju";
                    break;
                case 2:
                    region = "gangchon";
                    break;
                case 3:

                default:
                    System.out.println("잘못된입력");
            }
        }while(pointer >= 3);
        return region;
    }

    // makeMyReservation 헬퍼 함수
    private int choiceCapacity(){
        do{
            System.out.print("인원 => (1 ~ 6) >>> 번호입력 : ");
            capacity = Integer.parseInt(sc.nextLine());
            if (capacity >= 7) {
                System.out.println("잘못된 입력");
            }
        }while(capacity >= 7);
        return capacity;
    }

    // makeMyReservation 헬퍼 함수
    private String choosePeriod(){
        String start = null;
        String end = null;

        // 유효성 검사 필요
        System.out.print("시작날짜 => ex) 2024.04.26 >>> 번호입력 : ");
        start = sc.nextLine();
        System.out.print("종료날짜 => ex) 2024.04.26 >>> 번호입력 : ");
        end = sc.nextLine();

        return start + "~" + end;
    }

    public void getMyReservations(){
        for(Map.Entry reservation : fileIo.reservationListReader("muju").entrySet()){
            System.out.println(reservation.getValue());
        }
        for(Map.Entry reservation : fileIo.reservationListReader("gangchon").entrySet()){
            System.out.println(reservation.getValue());
        }
    }

    public void deleteMyReservation(){
        Map<Integer,Reservation> reservationList = fileIo.reservationListReader("muju");
        for (Map.Entry reservation : reservationList.entrySet()){
            System.out.println(reservation.toString());
        }
    }

    // 나중에 다른곳으로 빠질 애들
    public void tmp_addRoom(){
        System.out.println("방 만들기 Test");
        System.out.print("지역(muju/gangchon) : ");
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
