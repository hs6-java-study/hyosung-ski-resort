import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        String pointer = null;
        do{
            System.out.println("===== 회원 메뉴 =====");
            System.out.println("1.예약하기, 2.예약취소, 3.예약조회, 4.로그아웃, 5.tmp_방 추가, 6.tmp_방 조회");
            pointer = sc.nextLine();
            switch (pointer) {
                case "1":
                    makeMyReservation();
                    break;
                case "2":
                    deleteMyReservation();
                    break;
                case "3":
                    getMyReservations();
                    break;
                case "4":
                    System.out.println(member.getUserId() + "님 로그아웃 되었습니다!");
                    break;
                case "5":
                    tmp_addRoom();
                    break;
                case "6":
                    tmp_checkRoom();
                    break;
//                case 7:
//                    deleteMemberInfoSelf(member);
//                    break;
//                case 8:
//                    deleteMemberInfoSelf(member);
//                    break;
                default:
                    System.out.println("잘못된입력");
            }
        }while(pointer != "4");
    }

    public void makeMyReservation(){
        // 숙박 예약 로직
        System.out.println("==== 지점, 인원, 기간을 입력해 주세요 ====");

        this.region = chooseRegion();
        this.capacity = chooseCapacity();
        this.period = choosePeriod();
        this.roomList = getRoomList(this.region, this.capacity, this.period);

        System.out.println("==== 예약 가능한 객실 목록 ====");
        for (Integer roomNumber : roomList.keySet()){
            System.out.println(roomList.get(roomNumber).toString());
        }
        int reservationRoomNumber = chooseReservationRoomNumber();

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
        Reservation reservation = new Reservation(this.member, room, rental);
        int randomNumber = random.nextInt(100000000);

        // 회원 예약 내역 리스트 추가
        memberList = fileIo.memberListReader();
        memberList.get(member.getUserId()).getReservationNumberList().add(randomNumber);
        this.member.setReservationNumberList(memberList.get(member.getUserId()).getReservationNumberList());
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

        // 회원 등급에 따른 할인율 적용
        double discountRate = member.getDiscountRate();

        double discountedRoomPrice = room.getPrice() * (1 - discountRate); // 할인된 방 가격
        double totalRentalPrice = 0; // 장비 렌탈 가격 초기화

        for(String rentNumber : rentProduct) {
            Product rentedProduct = ProductList.get(rentNumber);
            if(rentedProduct != null) {
                totalRentalPrice += rentedProduct.getPrice() * (1 - discountRate); // 할인된 장비 렌탈 가격
            }
        }

        // 최종 결제 금액 계산
        double totalPayment = discountedRoomPrice + totalRentalPrice;

        // 포인트 업데이트 로직을 실행
        member.updatePoints((int)totalPayment);

        // 변경된 회원 정보 업데이트
        memberList.put(member.getUserId(), member);
        fileIo.memberListWriter(memberList);

        System.out.println("할인율 확인용 콘솔 : 전체 회원 일단 출력쓰");
        // 확인용 출력
        memberList.entrySet().stream()
                .forEach(entry -> System.out.println("[확인용 출력] 회원 ID : " + entry.getKey() + " | member 정보 : " + entry.getValue()));

    }

    // makeMyReservation 헬퍼 함수
    private Boolean rentHelmetOrClothes(String kind){
        List<String> selectedProductNumbers = new ArrayList<String>();
        this.products = fileIo.productListReader(kind, this.region);

        // 장비 보여주는 출력
        System.out.println("\n==== 장비 목록 ====");
        for(Map.Entry<String,Product> p : this.products.entrySet()){
            System.out.println(p.getKey() + " / " + p.getValue());
        }

        System.out.print(kind + AuthValidation.EQUIPMENT_COUNT.getInputMessage());
        String count = sc.nextLine();
        int[] counts = new int[3];
        if (!count.matches(AuthValidation.EQUIPMENT_COUNT.getRegex())) {
            System.out.println(AuthValidation.EQUIPMENT_COUNT.getFailureMessage());
            return false;
        }else{
            counts = Arrays.stream((count.split("/")))
                    .mapToInt(Integer::parseInt).toArray();
        }

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
        System.out.println("\n==== 장비 목록 ====");
        for(Map.Entry<String,Product> p : this.products.entrySet()){
            System.out.println(p.getKey() + " / " + p.getValue());
        }

        int count;
        try{
            System.out.print("Equipment >> 스키갯수 : ex) 3 : ");
            count = Integer.parseInt(sc.nextLine());
        }catch (NumberFormatException e){
            System.out.println("숫자를 입력해주세요");
            return false;
        }
        if (count > this.capacity) {
            System.out.println("장비 개수가 인원을 초과");
            return false;
        }

        for(Map.Entry<String,Product> product : this.products.entrySet()){
            if ( count != 0){
                System.out.println(count);
                selectedProductNumbers.add(product.getKey());
                count--;
            }else{
                rentProduct.addAll(selectedProductNumbers);
                return true;
            }
        }
        if(count == 0) {
            rentProduct.addAll(selectedProductNumbers);
            return true;
        }
        System.out.println("장비부족 못빌림");
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

    // makeMyReservation 헬퍼 함수 : 사라질 수도 있는 함수
    private String chooseRegion (){
        String pointer =  null;
        do{
            System.out.print("지점 => 1.무주 , 2.강촌 >>> 번호입력 : ");
            pointer = sc.nextLine();
            switch (pointer){
                case "1":
                    region = "muju";
                    break;
                case "2":
                    region = "gangchon";
                    break;
                default:
                    System.out.println("지점이 없습니다.");
            }
        }while(!pointer.equals("1") && !pointer.equals("2"));
        return region;
    }

    // makeMyReservation 헬퍼 함수 : 사라질 수도 있는 함수
    private int chooseCapacity(){
        String pointer = null;
        int capacity = 0;
        boolean validInput = false;
        do{
            System.out.print("인원 => (1 ~ 6) >>> 번호입력 : ");
            pointer = sc.nextLine();
            try{
                capacity = (Integer.parseInt(pointer));
                if (capacity < 1 || capacity  > 6 ) {
                    System.out.println("인원 수 부적합");
                }else{
                    validInput = true;
                }
            } catch (NumberFormatException e){
                System.out.println("잘못된 입력: 숫자를 입력하세요.");
            }
        }while (!validInput);
        return capacity;
    }

    // makeMyReservation 헬퍼 함수
    private String choosePeriod(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Calendar calendar = Calendar.getInstance();
        String today = dateFormat.format(new Date());
        String start = null;
        String end = null;
        do {
            // 시작 날짜 확인
            System.out.print("시작날짜 => " + AuthValidation.DATE.getInputMessage());
            start = sc.nextLine();
            if (!isValidDate(start) || !start.matches(AuthValidation.DATE.getRegex())) {
                System.out.println(AuthValidation.DATE.getFailureMessage());continue;
            }
            if( start.compareTo(today) <= 0) {
                System.out.println("시작 날짜는 오늘 날짜보다 늦어야 합니다.");continue;
            }
            break;
        } while (true);

        do{
            // 종료 날짜 확인
            System.out.print("종료날짜 => " + AuthValidation.DATE.getInputMessage());
            end = sc.nextLine();

            if (!isValidDate(end) || !end.matches(AuthValidation.DATE.getRegex())) {
                System.out.println(AuthValidation.DATE.getFailureMessage()); continue;
            }
            if (end.compareTo(start) <= 0){
                System.out.println("종료 날짜는 시작 날짜보다 늦어야 합니다."); continue;
            }
            break;
        } while (true);
        return start + "~" + end;
    }

    private Integer chooseReservationRoomNumber(){
        int reservationRoomNumber = 0;
        do {
            try {
                System.out.print("호실 : ");
                reservationRoomNumber = Integer.parseInt(sc.nextLine());
                if (this.roomList.containsKey(reservationRoomNumber)) {
                    return reservationRoomNumber;
                } else {
                    System.out.println("존재하지 않는 방");
                }
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
            }
        } while (true);
    }

    // choosePeriod의 헬퍼성 함수 : 시간 정규식 유효성 검사
    private boolean isValidDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public void getMyReservations(){
        Map<Integer, Reservation> totalReservations = new HashMap<Integer, Reservation>();
        totalReservations.putAll(fileIo.reservationListReader("muju"));
        totalReservations.putAll(fileIo.reservationListReader("gangchon"));

        System.out.println("\n====비교를 위한 전체 회원 목록 (삭제 예정)====");
        for(Map.Entry<Integer, Reservation> reservation : totalReservations.entrySet()){
            System.out.println(reservation.getKey() + " / " + reservation.getValue());
        }

        System.out.println("\n====나의 예약 목록조회====");
        for(Integer reservationNumber : this.member.getReservationNumberList()){
            System.out.println(reservationNumber + ":" + totalReservations.get(reservationNumber).toString());
        }
    }

    public void deleteMyReservation(){
        Map<Integer, Reservation> totalReservations = new HashMap<Integer, Reservation>();
        totalReservations.putAll(fileIo.reservationListReader("muju"));
        totalReservations.putAll(fileIo.reservationListReader("gangchon"));

        List<Integer> reservationNumberList = member.getReservationNumberList();

        for (int i = 0; i < reservationNumberList.size(); i++) {
            System.out.print((i + 1) + ". " );
            System.out.println(reservationNumberList.get(i) + " / " + totalReservations.get(reservationNumberList.get(i)).toString());
        }
        System.out.print("삭제할 번호 입력 : ");
        int order = Integer.parseInt(sc.nextLine());


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String today = dateFormat.format(new Date());
        for(Map.Entry<String,Boolean> d : totalReservations.get(reservationNumberList.get(order-1)).getRoom().getReservationDates().entrySet()){
            if(d.getKey().compareTo(today) <= 0){
                System.out.println("숙소 예약 날짜 중 현재보다 이전 날짜가 존재하니 삭제가 불가능하다.");
                return;
            }
        }

        // member에 접근하여 예약넘버 삭제
        // read하여 내 ID의  예약넘버 삭제 후 writer
        int deleteReservationNumber = reservationNumberList.remove(order-1);
        memberList = fileIo.memberListReader();
        memberList.get(member.getUserId()).setReservationNumberList(reservationNumberList);
        this.member.setReservationNumberList(reservationNumberList);
        fileIo.memberListWriter(memberList);

        // reservationslist read하기
        // 내 예약 넘버로 예약List에서 제거
        // Room 객체에서 Date의 Start-end, 지점-호수 체크
        // reservationsList의 물품 고유번호 체크
        Reservation deletedReservation = totalReservations.get(deleteReservationNumber);
        this.region = deletedReservation.getRoom().getRegion();
        reservationList = fileIo.reservationListReader(this.region);
        reservationList.remove(deleteReservationNumber);
        fileIo.reservationListWriter(this.region, reservationList);

        // 객실 날짜 수정
        // 객실 지점으로 read
        // 객실의 start-end 데이터로 날짜map 삭제 후 writer
        roomList = fileIo.roomListReader(this.region);
        room = roomList.get(deletedReservation.getRoom().getRoomNumber());
        for (Map.Entry<String,Boolean> date : deletedReservation.getRoom().getReservationDates().entrySet()){
            room.getReservationDates().remove(date.getKey());
        }
        roomList.get(deletedReservation.getRoom().getRoomNumber()).setReservationDates(room.getReservationDates());
        fileIo.roomListWriter(this.region, roomList);

        // 물품 날짜 수정
        // 3물품 파일 read
        // 3물풀에 물건 고유번호로 map에서 삭제 후 writer
        products = deletedReservation.getProducts();
        Map<String,Product> ProductList = new HashMap<String, Product>();
        Map<String,Product> helmetList = fileIo.productListReader("Helmet",this.region);
        Map<String,Product> clothesList = fileIo.productListReader("Clothes",this.region);
        Map<String,Product> equipmentList = fileIo.productListReader("Equipment",this.region);
        ProductList.putAll(helmetList);
        ProductList.putAll(clothesList);
        ProductList.putAll(equipmentList);

        for(Map.Entry<String, Product> p : products.entrySet()){
            for (Map.Entry<String,Boolean> date : deletedReservation.getRoom().getReservationDates().entrySet()){
                ProductList.get(p.getKey()).getRentalDates().remove(date.getKey());
            }
        }
        fileIo.productListWriter("Helmet",this.region,helmetList);
        fileIo.productListWriter("Clothes",this.region,clothesList);
        fileIo.productListWriter("Equipment",this.region,equipmentList);

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
