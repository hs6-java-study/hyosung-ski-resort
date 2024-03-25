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
    private Map<String, Boolean> reservationDates;
    private Random random ;
    private ManageDate manageDate;
    private Map<String, Member> memberList;


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
        // 숙박 예약 로직
        while (true){
            System.out.println("==== 지점, 인원, 기간을 입력해 주세요 ====");
//            this.region = choiceRegion();
//            this.capacity = choiceCapacity();
//            this.period = choosePeriod();

//           this.roomList = getRoomList(region, capacity, period);

            // 지워지는 부분
            this.roomList = getRoomList("muju", 5, "2024.01.28~2024.02.06");

            for (Integer roomNumber : roomList.keySet()){
                System.out.println(roomList.get(roomNumber).toString());}

            System.out.println("예약을 원하는 방의 호 수 입력");
            System.out.print("호 수 : ");
            int roomNumber = Integer.parseInt(sc.nextLine());

            if (this.roomList.containsKey(roomNumber)) break;
            else System.out.println("방이 없다.");
        }

//        while(true){
//            System.out.println("==== 장비렌탈 ====");
//            System.out.println("ex) 1/0/2");
//            rentHelmetOrClothes("헬멧");
//            break;
//        }
        // 장비 렌탈 로직 여기에 생성
        room = new Room("muju",123,4,1000);
        member = member;

        Reservation reservation = new Reservation(member, room, products);
        int randomNumber = random.nextInt(100000000);

        // 회원 예약 내역 리스트 추가
//        memberList = fileIo.memberListReader();
//        memberList.get(member.getUserId()).getReservationNumberList().add(randomNumber);
//        fileIo.memberListWriter(memberList);

        // 예약 내역 추가
        reservationList = fileIo.reservationListReader("muju");
        reservationList.put(randomNumber,reservation);
        fileIo.reservationListWriter("muju",reservationList);
    }
    private List rentHelmetOrClothes(String kind){
        List<String> selectedProductNumbers = new ArrayList<String>();
        System.out.print(kind  + " >> S갯수/M갯수/L갯수 : ");
        String count = sc.nextLine();
        int[] counts = Arrays.stream((count.split("/")))
                        .mapToInt(Integer::parseInt)
                        .toArray();
        
        // 파일 입력으로 대체
        this.products = new ArrayList<Product>();
        Product helmet = new Helmet("asdf","M",1561,new HashMap<Date,Boolean>());
        Product helmet2 = new Helmet("asdf","S",1561,new HashMap<Date,Boolean>());
        products = new ArrayList<Product>();
        products.add(helmet);
        products.add(helmet2);
        products.add(helmet);

        for(Product product : this.products){
            if (product.getSize().equals("S") && counts[0] != 0){
                selectedProductNumbers.add(product.getSerialNum());
                counts[0]--;
            }
            else if (product.getSize().equals("M") && counts[1] != 0){
                selectedProductNumbers.add(product.getSerialNum());
                counts[1]--;
            }
            else if (product.getSize().equals("L") && counts[2] != 0){
                selectedProductNumbers.add(product.getSerialNum());
                counts[2]--;
            }
            else if (counts[0] == 0 && counts[1] == 0 && counts[2] == 0){
                for(String p : selectedProductNumbers){
                    System.out.println(p.toString());
                }
                return selectedProductNumbers;
            }
        }
        System.out.println("못빌리쥬");
        return new ArrayList<String>();
    }

    // makeMyReservation 헬퍼 함수
    private Map<Integer, Room>  getRoomList(String region, int capacity, String period){
        this.roomList = fileIo.roomListReader(region);

        // 지워지는 부분
        this.roomList.get(102).setReservationDates(new HashMap<String,Boolean>(){{
            put("2024.01.29",true);
            put("2024.01.30",true);
            put("2024.01.31",true);
        }});

        // 인원 수 필터
        this.roomList = this.roomList.entrySet().stream()
                .filter(room -> room.getValue().getCapacity() == capacity)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // 기간 필터
        String[] tmp = period.split("~");
        this.manageDate = new ManageDate(tmp[0],tmp[1]);
        this.manageDate.getStartAndEndDate();
        this.roomList = this.roomList.entrySet().stream()
                .filter(room -> this.manageDate.isPossibleReservation(room.getValue().getReservationDates()) == true)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return this.roomList;
    }

    // makeMyReservation 헬퍼 함수
    private String choiceRegion (){
        do{
            System.out.print("지점 => 1. 무주 , 2. 하이원 >>> 번호입력 : ");
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
        // 유효성 검사 필요
        System.out.print("시작날짜 => ex) 2024.04.26 >>> 번호입력 : ");
        String start = sc.nextLine();
        System.out.print("종료날짜 => ex) 2024.04.26 >>> 번호입력 : ");
        String end = sc.nextLine();
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
