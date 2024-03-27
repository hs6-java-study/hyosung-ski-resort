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
            System.out.println(AnsiColor.line("\n\n\t\t\t\t\t\t\t\t< 회원 메뉴 >"));
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            System.out.println("\t\t\t\t\t\t\t\t1. 숙소 예약" );
            System.out.println("\t\t\t\t\t\t\t\t2. 예약 취소" );
            System.out.println("\t\t\t\t\t\t\t\t3. 예약 조회" );
            System.out.println("\t\t\t\t\t\t\t\t4. 로그 아웃" );
            System.out.println("\t\t\t\t\t\t\t\t5. 방생성 (임시)" );
            System.out.println("\t\t\t\t\t\t\t\t6. 방확인 (임시)" );
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
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
//                    tmp_addRoom();
                    AF_addRoom();
                    break;
                case "6":
                    tmp_checkRoom();
                    break;
                default:
                    System.out.println("잘못된입력");
            }
        }while(!pointer.equals("4"));
    }

    public void makeMyReservation(){
        // 숙박 예약 로직
        this.region = chooseRegion(); if(this.region.equals("X")) return;
        this.capacity = chooseCapacity(); if(this.capacity == 0) return;
        this.period = choosePeriod(); if(this.period.equals("X")) return;
        this.roomList = getRoomList(this.region, this.capacity, this.period);

        System.out.println();
        System.out.println(AnsiColor.yellow("\t\t\t\t\t\t\t\t< 내용 확인 >"));
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t지점 : " + (this.region.equals("muju") ? "무주점 리조트" : "강촌점 리조트" ));
        System.out.println("\t\t\t\t\t\t\t인원 : " + this.capacity + "명");
        System.out.println("\t\t\t\t\t\t\t기간 : " + this.period);

        System.out.println("\n\t\t\t\t\t\t\t[ 예약 가능한 객실 목록 ] ");

        System.out.format("\t\t\t+——————————————————————————————————————————————————+%n");
        System.out.format("\t\t\t      방 종류          호실       인원      가격  %n");
        System.out.format("\t\t\t+——————————————————————————————————————————————————+%n");

        if (roomList.size() == 0) {
            System.out.println(AnsiColor.red("\n\t\t\t\t\t\t\t예약 가능한 객실이 없습니다."));
            return;
        }

        for (Integer roomNumber : roomList.keySet()){
            Room roomInfo = roomList.get(roomNumber);
            System.out.format("\t\t\t  %-14s      %-4d       %-2d     %,8d  %n",
                    roomInfo.roomType, roomInfo.roomNumber, roomInfo.capacity, roomInfo.price);
            System.out.format("\t\t\t+——————————————————————————————————————————————————+%n");
        }
        System.out.println("\t\t\t\t\t\t\t객실의 호실을 입력해 주세요.");
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        int reservationRoomNumber = chooseReservationRoomNumber();
        // 장비 렌탈 로직
        this.rentProduct = new ArrayList<>();


        while(true){
            System.out.print("\t\t\t\t\t\t장비를 대여 하시겠습니까 ? (O / X) : ");
            String pointer = sc.nextLine();
            if (pointer.equals("X") || pointer.equals("x")) {
                System.out.println(AnsiColor.green("\t\t\t\t\t\t숙박 예약이 완료되었습니다."));
                break;
            }else if (!pointer.equals("O") && !pointer.equals("o")) {
                System.out.println(AnsiColor.red("\t\t\t\t\t\t잘못된 입력입니다."));
                continue;
            }
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            rentHelmetOrClothes("Helmet");
            rentHelmetOrClothes("Clothes");
            rentEquipment();
            break;
        }

        // 예약 내역 파일 불러오기
        reservationList = fileIo.reservationListReader(region);
        room = this.roomList.get(reservationRoomNumber);
        int randomNumber = 0;
        do{
            randomNumber = random.nextInt(100000000);
            if(!reservationList.containsKey(randomNumber)) break;
        }while(true);

        // 회원 예약 내역 리스트 추가
        memberList = fileIo.memberListReader();
        memberList.get(member.getUserId()).getReservationNumberList().add(randomNumber);
        this.member.setReservationNumberList(memberList.get(member.getUserId()).getReservationNumberList());
        fileIo.memberListWriter(memberList);

        // 숙소 + 렌탈 장비 대여 날짜 추가
        this.roomList = fileIo.roomListReader(region);
        Map<String,Product> ProductList = new HashMap<String, Product>();
        Map<String,Product> helmetList =
                fileIo.productListReader("Helmet",this.region);
        Map<String,Product> clothesList =
                fileIo.productListReader("Clothes",this.region);
        Map<String,Product> equipmentList =
                fileIo.productListReader("Equipment",this.region);
        ProductList.putAll(helmetList);
        ProductList.putAll(clothesList);
        ProductList.putAll(equipmentList);


        Map<String, Product> rentProductList = new HashMap<String, Product>();
        for(String rentNumber : rentProduct) {

            rentProductList.put(rentNumber, ProductList.get(rentNumber));
            rentProductList.get(rentNumber).setRentalDates(new HashMap<String , Boolean>());
        }
        Reservation reservation = new Reservation(this.member, room, rentProductList);
        reservation.getRoom().setReservationDates(new HashMap<String, Boolean>());

        for(String d : this.manageDate.startEndDates){
            // 숙소 대여날짜 업데이트
            this.roomList.get(reservationRoomNumber).getReservationDates().put(d, true);
            reservation.getRoom().getReservationDates().put(d,true);

            // 장비 대여날짜 업데이트
            for(String rentNumber : rentProduct) {
                ProductList.get(rentNumber).getRentalDates().put(d,true);
                rentProductList.get(rentNumber).getRentalDates().put(d,true);
            }
        }

        fileIo.roomListWriter(region, this.roomList);
        fileIo.productListWriter("Helmet",this.region,helmetList);
        fileIo.productListWriter("Clothes",this.region,clothesList);
        fileIo.productListWriter("Equipment",this.region,equipmentList);

        // 예약 내역 추가
        reservationList.put(randomNumber,reservation);
        fileIo.reservationListWriter(region,reservationList);

        // 회원 등급에 따른 할인율 적용
        List<String> pointDateList = this.manageDate.getStartAndEndDate(); // 기존 리스트
        Set<String> pointDateSet = new HashSet<>(pointDateList); // 중복 제거
        double discountRate = member.getDiscountRate();
        double totalPayment = 0;

        for (int i = 0; i < pointDateSet.size(); i++) {
            double discountedRoomPrice = room.getPrice() * (1 - discountRate); // 할인된 방 가격
            double totalRentalPrice = 0; // 날짜마다 초기화
            for(String rentNumber : rentProduct) {
                Product rentedProduct = ProductList.get(rentNumber);
                if(rentedProduct != null) {
                    totalRentalPrice += rentedProduct.getPrice() * (1 - discountRate); // 할인된 장비 렌탈 가격
                }
            }
            // 각 날짜별 결제 금액을 총 결제 금액에 더하기
            totalPayment += discountedRoomPrice + totalRentalPrice;
        }

        // 포인트 업데이트 로직을 실행
        member.updatePoints((int)totalPayment);

        // 변경된 회원 정보 업데이트
        memberList.put(member.getUserId(), member);
        fileIo.memberListWriter(memberList);

        System.out.println("할인율 확인용 콘솔 : 전체 회원 일단 출력쓰");
        // 확인용 출력
        memberList.entrySet().stream()
                .forEach(entry -> System.out.println("[확인용 출력] 회원 ID : " + entry.getKey() + " | member 정보 : " + entry.getValue()));

        System.out.println(reservation.toString());
        System.out.println(AnsiColor.yellow("\t\t\t\t\t\t\t\t< 내용 확인 >"));
        System.out.println("\t\t\t\t\t\t\t예약자명 : " + reservation.getMember().getName().toString());
        System.out.println("\t\t\t\t\t\t\t전화번호 : " + reservation.getMember().getPhoneNumber().toString());
        System.out.println("\t\t\t\t\t\t\t지점 : " + reservation.getRoom().region.toString());
        System.out.println("\t\t\t\t\t\t\t인원 수 : " + reservation.getRoom().capacity);
        System.out.println("\t\t\t\t\t\t\t호실 : " + reservation.getRoom().roomNumber);
        System.out.println("\t\t\t\t\t\t\t예약 시작일 : " + this.period.split("~")[0]);
        System.out.println("\t\t\t\t\t\t\t예약 마감일 : " + this.period.split("~")[1]);
        int[] helmet = new int[3];
        int[] clothes = new int[3];
        int[] equiment =  new int[3];
        separateProduct(reservation, helmetList, helmet);
        separateProduct(reservation, clothesList, clothes);
        separateProduct(reservation, equipmentList, equiment);
        System.out.println("\t\t\t\t\t\t\t헬멧 : " + "S(" + helmet[0] + "), " + "M(" + helmet[1] + "), " + "L(" + helmet[2] + ")" );
        System.out.println("\t\t\t\t\t\t\t의류 : " + "S(" + clothes[0] + "), " + "M(" + clothes[1] + "), " + "L(" + clothes[2] + ")" );
        System.out.println("\t\t\t\t\t\t\t장비 : " + equiment[0]);
        System.out.println("\t\t\t\t\t\t\t누적 지출 : " + totalPayment);
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
    }
    private void separateProduct(Reservation reservation, Map<String,Product> productListType ,int[] productType){
        for (Map.Entry<String,Product> p : reservation.getProducts().entrySet()){
            if (productListType.containsKey(p.getKey())){
                if (p.getValue().getSize().equals("S")) productType[0]++;
                else if (p.getValue().getSize().equals("M")) productType[1]++;
                else if (p.getValue().getSize().equals("L")) productType[2]++;
                else if (p.getValue().getSize().equals("Free")) productType[0]++;
            }
        }
    }

    // makeMyReservation 헬퍼 함수
    private void rentHelmetOrClothes(String kind){
        List<String> selectedProductNumbers = new ArrayList<String>();
        this.products = fileIo.productListReader(kind, this.region);

        // 장비 보여주는 출력
        String kindKoreaName = (kind.equals("Helmet") ? "헬멧" : "의류");
        System.out.println("\n\t\t\t\t\t\t\t\t [ " + kindKoreaName + " 목록 ]");
        System.out.format("\t\t\t+——————————————————————————————————————————————————+%n");
        System.out.format("\t\t\t    제품 고유번호      제품 종류    사이즈      가격    %n");
        System.out.format("\t\t\t+———————————————————————————————————————————————————+%n");
        for(Map.Entry<String,Product> product : this.products.entrySet()){
            System.out.format("\t\t\t   %-14s     %-4s      %-2s      %,8d   %n",
                    product.getKey(),kindKoreaName,product.getValue().getSize(),product.getValue().getPrice());
            System.out.format("\t\t\t+———————————————————————————————————————————————————+%n");
        }
        System.out.println("\t\t\t\t\t  " + AuthValidation.EQUIPMENT_COUNT.getInputMessage());

        do {
            System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
            String count = sc.nextLine();
            int[] counts = new int[3];
            if (!count.matches(AuthValidation.EQUIPMENT_COUNT.getRegex())) {
                System.out.println("\t\t\t\t\t  " + AuthValidation.EQUIPMENT_COUNT.getFailureMessage());
                continue;
            }else{
                counts = Arrays.stream((count.split("/")))
                        .mapToInt(Integer::parseInt).toArray();
            }

            int totalProductCount = 0;
            for (int c : counts){
                totalProductCount += c;
            }

            if (totalProductCount > this.capacity) {
                System.out.println(AnsiColor.red("\t\t\t\t\t신청한 " + kindKoreaName + " 개수가 인원을 초과했습니다."));
                continue;
            }

            for(Map.Entry<String,Product> product : this.products.entrySet()){
                if(!this.manageDate.isPossibleReservation((product.getValue()).getRentalDates())) {
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
                    System.out.println(AnsiColor.green("\t\t\t\t\t\t\t" + kindKoreaName + "를 대여할 수 있습니다."));
                    rentProduct.addAll(selectedProductNumbers);
                    System.out.println("\t+———————————————————————————————————————————————————————————————————+");

                    return ;
                }
            }
            if (counts[0] == 0 && counts[1] == 0 && counts[2] == 0){
                System.out.println(AnsiColor.green("\t\t\t\t\t\t\t" + kindKoreaName + "를 대여할 수 있습니다."));
                rentProduct.addAll(selectedProductNumbers);
                System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                return ;
            }
            System.out.println(AnsiColor.red("\t\t\t\t\t장비가 부족하여 빌릴 수 없습니다."));
        }while(true);
    }

    // makeMyReservation 헬퍼 함수
    private void rentEquipment(){
        List<String> selectedProductNumbers = new ArrayList<String>();
        this.products = fileIo.productListReader("Equipment",this.region);

        // 장비 갯수 보여주는 출력
        System.out.println("\n\t\t\t\t\t\t\t\t [ 장비 목록 ]");
        System.out.format("\t\t\t+——————————————————————————————————————————————————+%n");
        System.out.format("\t\t\t    제품 고유번호      제품 종류    사이즈      가격    %n");
        System.out.format("\t\t\t+———————————————————————————————————————————————————+%n");

        for(Map.Entry<String,Product> product : this.products.entrySet()){
            System.out.format("\t\t\t   %-14s     %-4s      %-2s      %,8d   %n",
                    product.getKey(),"장비",product.getValue().getSize(),product.getValue().getPrice());
            System.out.format("\t\t\t+———————————————————————————————————————————————————+%n");
        }
        System.out.println("\t\t\t\t\t  대여할 장비의 갯수를 입력해주세요  ex) 3");
        do {
            int count;
            try{
                System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
                count = Integer.parseInt(sc.nextLine());
            }catch (NumberFormatException e){
                System.out.println(AnsiColor.red("\t\t\t\t\t 숫자를 입력해주세요."));
                continue;
            }
            if (count > this.capacity) {
                System.out.println(AnsiColor.red("\t\t\t\t\t 장비 개수가 인원을 초과했습니다."));
                continue;
            }

            for(Map.Entry<String,Product> product : this.products.entrySet()){
                if(!this.manageDate.isPossibleReservation((product.getValue()).getRentalDates())) {
                    continue;
                }
                if ( count != 0){
                    selectedProductNumbers.add(product.getKey());
                    count--;
                }else{
                    rentProduct.addAll(selectedProductNumbers);
                    System.out.println(AnsiColor.green("\t\t\t\t\t\t\t 장비를 대여할 수 있습니다."));
                    System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                    return;
                }
            }
            if(count == 0) {
                rentProduct.addAll(selectedProductNumbers);
                System.out.println(AnsiColor.green("\t\t\t\t\t\t\t 장비를 대여할 수 있습니다."));
                System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                return;
            }
            System.out.println(AnsiColor.red("\t\t\t\t\t 장비가 부족하여 빌릴 수 없습니다."));
        }while(true);
    }

    // makeMyReservation 헬퍼 함수
    private Map<Integer, Room>  getRoomList(String region, int capacity, String period){
        this.roomList = fileIo.roomListReader(region);

        // 인원 수 필터
        this.roomList = this.roomList.entrySet().stream()
                .filter(room -> room.getValue().getCapacity() >= capacity)
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
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t\t1. 무주점 " );
        System.out.println("\t\t\t\t\t\t\t\t2. 강촌점 " );
        System.out.println("\t\t\t\t\t\t\t지점을 입력해 주세요.");
        System.out.println(AnsiColor.yellow("\t\t\t\t\t\t-뒤로가려면 \"X\"를 입력해주세요-"));
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        do{
            System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
            pointer = sc.nextLine();
            switch (pointer){
                case "1":
                    region = "muju";
                    break;
                case "2":
                    region = "gangchon";
                    break;
                case "X":
                    return "X";
                case "x":
                    return "X";
                default:
                    System.out.println(AnsiColor.red("\t\t\t\t\t\t지점이 없습니다."));
            }
        }while(!pointer.equals("1") && !pointer.equals("2"));
        return region;
    }

    // makeMyReservation 헬퍼 함수 : 사라질 수도 있는 함수
    private int chooseCapacity(){
        String pointer = null;
        int capacity = 0;
        boolean validInput = false;
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t  1 ~ 6 번호 입력" );
        System.out.println("\t\t\t\t\t\t\t인원을 입력해 주세요.");
        System.out.println(AnsiColor.yellow("\t\t\t\t\t\t-뒤로가려면 \"X\"를 입력해주세요-"));
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        do{
            System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
            pointer = sc.nextLine();
            try{
                if (pointer.equals("X") || pointer.equals("x") ) return 0;
                capacity = (Integer.parseInt(pointer));
                if (capacity < 1 || capacity  > 6 ) {
                    System.out.println(AnsiColor.red("\t\t\t\t\t\t인원 수 부적합"));
                }else{
                    validInput = true;
                }
            } catch (NumberFormatException e){
                System.out.println(AnsiColor.red("\t\t\t\t\t\t잘못된 입력: 숫자를 입력해주세요."));
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
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t" + AuthValidation.DATE.getInputMessage());
        System.out.println("\t\t\t\t\t\t\t시작 날짜를 입력해 주세요.");
        System.out.println(AnsiColor.yellow("\t\t\t\t\t\t-뒤로가려면 \"X\"를 입력해주세요-"));
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        do {
            // 시작 날짜 확인
            System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
            start = sc.nextLine();
            if (start.equals("X") || start.equals("x") ) return "X";

            if (!isValidDate(start) || !start.matches(AuthValidation.DATE.getRegex())) {
                System.out.println(AuthValidation.DATE.getFailureMessage());continue;
            }
            if( start.compareTo(today) <= 0) {
                System.out.println(AnsiColor.red("\t\t\t\t\t\t이미 지난 날입니다."));continue;
            }
            break;
        } while (true);

        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t" + AuthValidation.DATE.getInputMessage());
        System.out.println("\t\t\t\t\t\t\t종료 날짜를 입력해 주세요.");
        System.out.println(AnsiColor.yellow("\t\t\t\t\t\t-뒤로가려면 \"X\"를 입력해주세요-"));
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        do{
            // 종료 날짜 확인
            System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
            end = sc.nextLine();
            if (end.equals("X") || end.equals("x") ) return "X";

            if (!isValidDate(end) || !end.matches(AuthValidation.DATE.getRegex())) {
                System.out.println(AuthValidation.DATE.getFailureMessage()); continue;
            }
            if (end.compareTo(start) <= 0){
                System.out.println(AnsiColor.red("\t\t\t\t\t\t종료 날이 시작 날보다 빠릅니다.")); continue;
            }
            break;
        } while (true);
        return start + "~" + end;
    }

    private Integer chooseReservationRoomNumber(){
        int reservationRoomNumber = 0;
        do {
            try {
                System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
                reservationRoomNumber = Integer.parseInt(sc.nextLine());
                if (this.roomList.containsKey(reservationRoomNumber)) {
                    System.out.println(AnsiColor.green("\t\t\t\t\t\t\t\t예약이 가능합니다."));
                    return reservationRoomNumber;
                } else {
                    System.out.println(AnsiColor.red("\t\t\t\t\t\t  존재하지 않는 방입니다."));
                }
            } catch (NumberFormatException e) {
                System.out.println(AnsiColor.red("\t\t\t\t\t잘못된 입력입니다. 숫자를 입력해주세요."));
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

        int order = 0;
        boolean validInput = false;
        do {
            try {
                System.out.print("삭제할 번호 입력 : ");
                order = Integer.parseInt(sc.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
            }
        } while (!validInput);

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
        memberList.get(member.getUserId())
                .setReservationNumberList(reservationNumberList);
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
        Map<String,Product> ProductList =
                new HashMap<String, Product>();
        Map<String,Product> helmetList =
                fileIo.productListReader("Helmet",this.region);
        Map<String,Product> clothesList =
                fileIo.productListReader("Clothes",this.region);
        Map<String,Product> equipmentList =
                fileIo.productListReader("Equipment",this.region);

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

        // 회원 등급에 따른 할인율 적용
        int deletePointDate = deletedReservation.getRoom().getReservationDates().size();
        double discountRate = member.getDiscountRate();

        // 차감해야 할 총 금액 계산
        double totalRentalPrice = 0;
        for (int i = 0; i < deletePointDate; i++) {
            double discountedRoomPrice = room.getPrice() * (1 - discountRate); // 할인된 방 가격
            double totalRentalDeduction = 0; // 날짜마다 초기화
            for(String rentNumber : deletedReservation.getProducts().keySet()) { // 수정된 부분
                Product rentedProduct = ProductList.get(rentNumber);
                if(rentedProduct != null) {
                    totalRentalDeduction += rentedProduct.getPrice() * (1 - discountRate); // 할인된 장비 렌탈 가격
                }
            }
            totalRentalPrice += discountedRoomPrice + totalRentalDeduction;
        }

        // 사용자의 현재 포인트에서 차감
        int newPoints = member.getPoint() - (int)totalRentalPrice;

        // 포인트 업데이트
        member.setPoint(newPoints); // 사용자 포인트 직접 설정 또는 적절한 메소드 사용

        // 변경된 회원 정보 업데이트
        memberList.put(member.getUserId(), member);
        fileIo.memberListWriter(memberList);
    }

    public void AF_addRoom(){
        System.out.print("1.무주 / 2.강촌 : ");
        String where = sc.nextLine();
        System.out.println("standard/deluxe/Family");
        String RoomType = sc.nextLine();
        Room room = null;

        switch (where){
            case "1":
                BuildRoom mjBuild = new BuildMuJuRoom();
                room = mjBuild.orderRoom(RoomType);
                break;
            case "2":
                BuildRoom gcBuild = new BuildGangChonRoom();
                room = gcBuild.orderRoom(RoomType);
                break;
            default:
                System.out.println("잘못된 입력");
        }
        where = (where.equals("1") ? "muju" : "gangchon");
        // Map<Integer, Room> roomList;
        roomList = fileIo.roomListReader(where);
        roomList.put(room.getRoomNumber(),room);
        fileIo.roomListWriter(where, roomList);
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
