import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class MenuMember {
    public Scanner sc ;
    public FileIO fileIo;
    private Map<Integer, Room> roomList;
    private Member member;
    private Room room;
    private Map<String,Product> products;
    private Map<Integer,Reservation> reservationList;
    private int pointer;
    private String region;
    private int capacity;
    private String period;
    private Map<String, Boolean> reservationDates;
    private Random random ;
    private ManageDate manageDate;
    private Map<String, Member> memberList;
    private List<String> rentProduct;


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
//                case 7:
//                    editMemberInfoSelf(member);
//                    break;
//                case 8:
//                    deleteMemberInfoSelf(member);
//                    break;
                default:
                    System.out.println("잘못된입력");
            }
        }while(pointer != 4);
    }

    public void makeMyReservation(Member member){
        // 숙박 예약 로직
        int reservationRoomNumber;
        while (true){
            System.out.println("==== 지점, 인원, 기간을 입력해 주세요 ====");
            this.region = choiceRegion();
            this.capacity = choiceCapacity();
            this.period = choosePeriod();
            this.roomList = getRoomList(this.region, this.capacity, this.period);

            for (Integer roomNumber : roomList.keySet()){
                System.out.println(roomList.get(roomNumber).toString());}

            System.out.println("예약을 원하는 방의 호 수 입력");
            System.out.print("호 수 : ");
            reservationRoomNumber = Integer.parseInt(sc.nextLine());

            if (this.roomList.containsKey(reservationRoomNumber)) break;
            else System.out.println("방이 없다.");
        }

        // 장비 렌탈 로직
        this.rentProduct = new ArrayList<>();
        while(true){
            System.out.println("==== 장비렌탈 ====");
            while(true){
                if(rentHelmetOrClothes("Helmet")) break ;
            }
            while(true){
                if(rentHelmetOrClothes("Clothes")) break ;
            }
            while(true){
                if(rentEquipment()) break ;
            }
            break;
        }

        room = new Room(this.region, reservationRoomNumber,this.capacity,this.roomList.get(reservationRoomNumber).getPrice());
        Map<String,Product> rental = new HashMap<String,Product>();
        Reservation reservation = new Reservation(member, room, rental);
        int randomNumber = random.nextInt(100000000);

        // 회원 예약 내역 리스트 추가
        memberList = fileIo.memberListReader();
        memberList.get(member.getUserId()).getReservationNumberList().add(randomNumber);
        fileIo.memberListWriter(memberList);

        // 숙소 + 렌탈 장비 대여 날짜 추가
        this.roomList = fileIo.roomListReader(region);
        Map<String,Product> ProductList = new HashMap<String, Product>();
        Map<String,Product> helmetList = fileIo.productListReader("Helmet",this.region);
        Map<String,Product> clothesList = fileIo.productListReader("Clothes",this.region);
        Map<String,Product> equipmentList = fileIo.productListReader("Equipment",this.region);
        ProductList.putAll(helmetList);
        ProductList.putAll(clothesList);
        ProductList.putAll(equipmentList);

        for(String rentNumber : rentProduct) {
            rental.put(rentNumber, ProductList.get(rentNumber));
        }

        for(String d : this.manageDate.startEndDates){
            // 숙소 대여날짜 업데이트
            this.roomList.get(reservationRoomNumber).getReservationDates().put(d, true);
            reservation.getRoom().getReservationDates().put(d,true);

            // 장비 대여날짜 업데이트
            for(String rentNumber : rentProduct) {
                ProductList.get(rentNumber).getRentalDates().put(d,true);
            }
        }
        for(Map.Entry<String,Product> p : ProductList.entrySet()){
            System.out.println(p.getKey() +" / "+ p.getValue());
        }
        fileIo.roomListWriter(region, this.roomList);
        fileIo.productListWriter("Helmet",this.region,helmetList);
        fileIo.productListWriter("Clothes",this.region,clothesList);
        fileIo.productListWriter("Equipment",this.region,equipmentList);

        // 예약 내역 추가
        reservationList = fileIo.reservationListReader(region);
        reservationList.put(randomNumber,reservation);
        fileIo.reservationListWriter(region,reservationList);

        // 예약 목록 확인
        for(Map.Entry m :reservationList.entrySet()){
            System.out.println(m.getKey() + " / " + m.getValue());
        }
    }

    // makeMyReservation 헬퍼 함수
    private Boolean rentHelmetOrClothes(String kind){
        List<String> selectedProductNumbers = new ArrayList<String>();
        this.products = fileIo.productListReader(kind,this.region);

        // 장비 갯수 보여주는 출력
        System.out.println("==== 장비 목록 ====");
        for(Map.Entry<String,Product> p : this.products.entrySet()){
            System.out.println(p.getKey() + " / " + p.getValue());
        }

        System.out.print(kind  + " >> S갯수/M갯수/L갯수 : ");
        String count = sc.nextLine();
        int[] counts = Arrays.stream((count.split("/")))
                        .mapToInt(Integer::parseInt).toArray();

        int totalProductCount = 0;
        for (int c : counts){
            totalProductCount += c;
        }

        if (totalProductCount > this.capacity) {
            System.out.println(kind +" 개수가 인원을 초과");
            return false;
        }

        for(Map.Entry<String,Product> product : this.products.entrySet()){
            if(!this.manageDate.isPossibleReservation((product.getValue()).getRentalDates())) {
                System.out.println((product.getValue()).getSerialNum() + "는 예약 불가능 " + kind);
                continue;
            }
            if ((product.getValue()).getSize().equals("S") && counts[0] != 0){
                selectedProductNumbers.add(product.getKey());
                counts[0]--;
            }
            else if ((product.getValue()).getSize().equals("M") && counts[1] != 0){
                selectedProductNumbers.add(product.getKey());
                counts[1]--;
            }
            else if ((product.getValue()).getSize().equals("L") && counts[2] != 0){
                selectedProductNumbers.add(product.getKey());
                counts[2]--;
            }
            else if (counts[0] == 0 && counts[1] == 0 && counts[2] == 0){
                System.out.println("장비대여가능");
                rentProduct.addAll(selectedProductNumbers);
                return true;
            }
        }
        if (counts[0] == 0 && counts[1] == 0 && counts[2] == 0){
            System.out.println("장비대여가능");
            rentProduct.addAll(selectedProductNumbers);
            return true;
        }
        System.out.println("장비부족 못빌림");
        return false;
    }

    // makeMyReservation 헬퍼 함수
    private Boolean rentEquipment(){
        List<String> selectedProductNumbers = new ArrayList<String>();
        this.products = fileIo.productListReader("Equipment",this.region);

        // 장비 갯수 보여주는 출력
        System.out.println("==== 장비 목록 ====");
        for(Map.Entry<String,Product> p : this.products.entrySet()){
            System.out.println(p.getKey() + " / " + p.getValue());
        }

        System.out.print("장비 >> 스키갯수 : ");
        int count = Integer.parseInt(sc.nextLine());

        if (count > this.capacity) {
            System.out.println("장비 개수가 인원을 초과");
            return false;
        }

        for(Map.Entry<String,Product> product : this.products.entrySet()){
            if ( count != 0){
                selectedProductNumbers.add(product.getKey());
                count--;
            }else{
                rentProduct.addAll(selectedProductNumbers);
                return true;
            }
        }
        System.out.println("장비부족 못빌리쥬");
        return false;
    }

    // makeMyReservation 헬퍼 함수
    private Map<Integer, Room>  getRoomList(String region, int capacity, String period){
        this.roomList = fileIo.roomListReader(region);

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

//    private void editMemberInfoSelf(Member member) {
//        System.out.println("1. 비밀번호 / 2. 전화번호");
//        System.out.print("수정하려는 정보를 입력 : ");
//        int answer = Integer.parseInt(sc.nextLine());
//        if(answer == 1) {
//            memberList = fileIo.memberListReader();
//            System.out.print("기존 비밀번호를 입력하세요: ");
//            String oldPassword = sc.nextLine();
//
//            boolean found = false;
//            for(Member m : memberList.values()) {
//                if(m.getPhoneNumber().equals(member.getPhoneNumber())) {
//                    if(m.getPassword().equals(oldPassword)) {
//                        System.out.print("새로운 비밀번호를 입력하세요: ");
//                        String newPassword = sc.nextLine();
//                        m.setPassword(newPassword);
//                        found = true;
//                        break;
//                    } else {
//                        System.out.println("틀렸습니다.");
//                    }
//                }
//            }
//            if(found) {
//                fileIo.memberListWriter(memberList);
//                System.out.println(member.getName() + " 회원의 정보가 성공적으로 수정 되었습니다.");
//                System.out.println("재접속 해주십시오.");
//                System.exit(0);
//            }
//        } else if( answer == 2) {
//            memberList = fileIo.memberListReader();
//            System.out.print("기존 전화번호를 입력하세요: ");
//            String oldPhoneNumber = sc.nextLine();
//            boolean found = false;
//            for(Member m : memberList.values()) {
//                if(m.getPhoneNumber().equals(member.getPhoneNumber())) {
//                    if(m.getPhoneNumber().equals(oldPhoneNumber)) {
//                        System.out.print("새로운 전화번호를 입력하세요: ");
//                        String newPhoneNumber = sc.nextLine();
//                        m.setPhoneNumber(newPhoneNumber);
//                        found = true;
//                        break;
//                    } else {
//                        System.out.println("틀렸습니다.");
//                    }
//                }
//            }
//            if(found) {
//                fileIo.memberListWriter(memberList); // 변경된 회원 목록을 파일에 다시 쓰기
//                System.out.println(member.getName() + " 회원의 정보가 성공적으로 수정 되었습니다.");
//                System.out.println("재접속 해주십시오.");
//                System.exit(0);
//            }
//        } else {
//            System.out.println("잘못된 입력");
//        }
//    }
//
//    private void deleteMemberInfoSelf(Member member) {
//        System.out.println("Y/N");
//        System.out.println("회원 탈퇴 하시겠습니까?");
//        String answer = sc.nextLine();
//        if(answer.equalsIgnoreCase("Y")) {
//            memberList = fileIo.memberListReader();
//            boolean removed =  memberList.entrySet().removeIf(entry -> entry.getValue().getPhoneNumber().equals(member.getPhoneNumber()));
//            if(removed) {
//                System.out.println(member.getName() + " 회원의 정보가 성공적으로 삭제되었습니다.");
//                fileIo.memberListWriter(memberList); // 변경된 회원 목록을 파일에 다시 쓰기
//                System.out.println("시스템을 종료합니다.");
//                System.exit(0);
//            }
//        } else if( answer.equalsIgnoreCase("N")) {
//            System.out.println("이전 페이지로 돌아갑니다.");
//        }
//    }
}
