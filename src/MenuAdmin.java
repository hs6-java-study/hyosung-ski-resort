import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class MenuAdmin {
    private Admin admin;
    public Scanner sc;
    public FileIO fileIo;
    private int pointer;
    Map<String, Member> memberList;
    Map<Integer, Reservation> reservationList;
    Map<String, Helmet> helmetList;
    Map<String, Clothes> clothesList;
    Map<String, Equipment> equipmentList;

    Map<String, Product> productList;

    MenuAdmin() {
        sc = new Scanner(System.in);
        fileIo = new FileIO();
    }

    public void run(Admin admin) {
        this.admin = admin;
        do {
            System.out.println("1. 회원 관리, 2. 예약 내역 관리, 3. 장비 관리, 4. 매출 조회, 5. 종료");
            pointer = Integer.parseInt(sc.nextLine());
            switch (pointer) {
                case 1: {
                    System.out.println("1. 회원 전체 조회, 2. 회원 정보 검색, 3. 회원 탈퇴 처리");
                    int pointerMemberManage = Integer.parseInt(sc.nextLine());
                    if (pointerMemberManage == 1) {
                        getAllMemberListInfo();
                    } else if (pointerMemberManage == 2) {
                        getOneMemberInfo();
                    } else if (pointerMemberManage == 3) {
                        deleteOneMember();
                    } else {
                        System.out.println("잘못된 입력");
                    }
                    break;
                }

                case 2: {
                    System.out.println("1. 예약 목록 전체 조회, 2. 특정 회원 예약내역 조회");
                    int pointerReservationManage = Integer.parseInt(sc.nextLine());
                    if (pointerReservationManage == 1) {
                        getAllReservationInfo();
                    } else if (pointerReservationManage == 2) {
                        getReservationInfoByOneMember();
                    } else {
                        System.out.println("잘못된 입력");
                    }
                    break;
                }
                case 3: {
                    System.out.println("1. 대여 장비 전체 재고 조회, 2. 장비 개수 추가, 3. 장비 개수 삭제");
                    int pointerProductManage = Integer.parseInt(sc.nextLine());
                    if (pointerProductManage == 1) {
                        getAllProductInfo();
                    } else if (pointerProductManage == 2) {
                        addProduct();
                    } else if (pointerProductManage == 3) {
                        deleteProduct();
                    } else {
                        System.out.println("잘못된 입력");
                    }
                    break;
                }
                case 4: {
                    System.out.println("1. 숙박 매출 조회, 2. 대여 장비 매출 조회");
                    int pointerRevenueManage = Integer.parseInt(sc.nextLine());
                    if (pointerRevenueManage == 1) {
                        getAllReservationRevenue();
                    } else if (pointerRevenueManage == 2) {
                        getAllProductRevenue();
                    } else {
                        System.out.println("잘못된 입력");
                    }
                    break;
                }

                case 5: {
                    System.out.println("시스템을 종료합니다.");
                    System.exit(0);
                    break;
                }
                default:
                    System.out.println("잘못된 입력");
            }
        } while (pointer != 5);
    }

    private void getAllMemberListInfo() {
        memberList = fileIo.memberListReader();
        if (memberList != null) {
            System.out.println("ID \t 전화번호 \t 등급 \t 포인트 \t 예약리스트");
            memberList.entrySet().stream()
                    .forEach(entry -> System.out.println(entry.getValue().getName() + "\t" + entry.getValue().getPhoneNumber() + "\t" + entry.getValue().getGrade() + "\t" + entry.getValue().getPoint()+ "\t" +entry.getValue().getReservationNumberList()));
        } else {
            System.out.println("회원 정보가 없습니다.");
        }
    }

    private void getOneMemberInfo() {
        memberList = fileIo.memberListReader();
        System.out.print("검색 하실 회원의 이름: ");
        String searchName = sc.nextLine();

        boolean isEmptySearchedMemberByName = memberList.entrySet().stream().anyMatch(entry -> entry.getValue().getName().equals(searchName));

        if (isEmptySearchedMemberByName) {
            System.out.println("ID \t 전화번호 \t 등급 \t 포인트 \t");

            memberList.entrySet().stream()
                    .filter(entry -> entry.getValue().getName().equals(searchName))
                    .forEach(entry -> System.out.println(entry.getKey() + "\t" +
                            entry.getValue().getPhoneNumber() + "\t" +
                            entry.getValue().getGrade() + "\t" +
                            entry.getValue().getPoint()));
        } else {
            System.out.println("일치하는 회원 정보가 없습니다.");
        }
    }


    private void deleteOneMember() {
        System.out.print("삭제할 회원의 이름: ");
        String deleteName = sc.nextLine();
        memberList = fileIo.memberListReader();
        boolean removed = memberList.entrySet().removeIf(entry -> entry.getValue().getName().equals(deleteName));

        if (removed) {
            System.out.println(deleteName + " 회원의 정보가 성공적으로 삭제되었습니다.");
            fileIo.memberListWriter(memberList); // 변경된 회원 목록을 파일에 다시 쓰기
        } else {
            System.out.println(deleteName + " 회원을 찾을 수 없습니다.");
        }
    }

    private void getAllReservationInfo() {
        // 예약하기 제대로 안되서 미확인
        System.out.println("1. 무주점  2. 강촌점");
        String regionPointer = sc.nextLine();
        switch (regionPointer) {
            case "1" : {
                reservationList = fileIo.reservationListReader("muju");
                break;
            }
            case "2" : {
                reservationList = fileIo.reservationListReader("gangchon");
                break;
            }
            default:
                System.out.println("잘못된 입력"); return;
        }
        if (reservationList != null && !reservationList.isEmpty()) {
            System.out.println("예약 ID \t 이름 \t 방 번호 \t 예약 금액 \t 예약 날짜 \t 예약 시간");
            reservationList.values().forEach(reservation -> {
                System.out.println(reservation.getMember().getUserId() + "\t" +
                        reservation.getMember().getName() + "\t" +
                        reservation.getRoom().getReservationDates() + "\t" +
                        reservation.getRoom().getRoomNumber() + "\t" +
                        reservation.getRoom().getPrice());
//                        reservation.getProducts().stream()
//                                .map(product -> product.getRentalDates() + " ")
//                                .collect(Collectors.joining(", ")));
            });
        } else {
            System.out.println("예약 내역이 없습니다.");
        }
    }

    private void getReservationInfoByOneMember() {
        System.out.print("조회할 이름을 입력하세요 : ");
        String searchName = sc.nextLine();
        reservationList = fileIo.reservationListReader("muju");
        List<Reservation> filteredReservationsByMuju = reservationList.values().stream()
                .filter(reservation -> reservation.getMember().getName().equals(searchName))
                .collect(Collectors.toList());

        // 필터링된 예약 목록 출력
        if (!filteredReservationsByMuju.isEmpty()) {
            System.out.println("예약자명 \t 전화번호 \t 지점 \t 방 번호 \t 인원수 \t 가격 \t 날짜");
            filteredReservationsByMuju.forEach(reservation ->
                    System.out.println(reservation.getMember().getName() + "\t" +
                            reservation.getMember().getPhoneNumber() + "\t" +
                            reservation.getRoom().getRegion() + "\t" +
                            reservation.getRoom().getRoomNumber() + "\t" +
                            reservation.getRoom().getCapacity() + "\t" +
                            reservation.getRoom().getPrice() + "\t" +
                            reservation.getRoom().getReservationDates() + "\t"));
        } else {
            System.out.println(searchName + "님의 무주점 예약 내역이 없습니다.");
        }

        reservationList = fileIo.reservationListReader("gangchon");
        List<Reservation> filteredReservationsByGangchon = reservationList.values().stream()
                .filter(reservation -> reservation.getMember().getName().equals(searchName))
                .collect(Collectors.toList());

        // 필터링된 예약 목록 출력
        if (!filteredReservationsByGangchon.isEmpty()) {
            System.out.println("예약 ID \t 이름 \t 예약 날짜 \t 예약 시간");
            filteredReservationsByGangchon.forEach(reservation ->
                    System.out.println(reservation.getMember().getName() + "\t" +
                            reservation.getMember().getPhoneNumber() + "\t" +
                            reservation.getRoom().getRegion() + "\t" +
                            reservation.getRoom().getRoomNumber() + "\t" +
                            reservation.getRoom().getCapacity() + "\t" +
                            reservation.getRoom().getPrice() + "\t" +
                            reservation.getRoom().getReservationDates() + "\t"));
        } else {
            System.out.println(searchName + "님의 강촌점 예약 내역이 없습니다.");
        }
    }

    public void getAllProductInfo() {
        productList = fileIo.AllProductListReader();
        if(productList.isEmpty()) {
            System.out.println("장비 목록이 존재하지 않습니다.");
        }
        productList.entrySet().stream()
                .forEach(entry -> System.out.println("[확인용 출력] " + entry.getKey() + " | product 정보 : " + entry.getValue()));
    }

    private void addProduct() {
        System.out.println("무주 / 강촌");
        System.out.print("원하는 장비의 지점을 입력하세요 : ");
        String regionInput = sc.nextLine();
        String region = null;
        if( regionInput.equals("무주")) {
            region = "muju";
        } else if ( regionInput.equals("강촌")) {
            region = "gangchon";
        } else {
            System.out.println("잘못된 입력");
            return;
        }
        System.out.println("1. 헬멧 \t 2. 의류 \t 3. 장비");
        int productPointer = Integer.parseInt(sc.nextLine());
        switch (productPointer) {
            case 1 :{
                combineAddProduct(region , "Helmet");
                break;}
            case 2 : {
                combineAddProduct(region , "Clothes");
                break;}
            case 3 :{
                combineAddProduct(region , "Equipment");
                break;}
            default: {
                System.out.println("잘못된 입력");
                break;
            }
        }
    }

    private void deleteProduct() {
        System.out.println("무주 / 강촌");
        System.out.print("원하는 장비의 지점을 입력하세요 : ");
        String regionInput = sc.nextLine();
        String region = null;
        if( regionInput.equals("무주")) {
            region = "muju";
        } else if ( regionInput.equals("강촌")) {
            region = "gangchon";
        } else {
            System.out.println("잘못된 입력");
            return;
        }
        System.out.println("1. 헬멧 \t 2. 의류 \t 3. 장비");
        int productPointer = Integer.parseInt(sc.nextLine());
        switch (productPointer) {
            case 1 :{
                combineDeleteProduct(region, "Helmet");
                break;}
            case 2 : {
                combineDeleteProduct(region, "Clothes");
                break;}
            case 3 :{
                combineDeleteProduct(region, "Equipment");
                break;}
            default: {
                System.out.println("잘못된 입력");
                break;
            }
        }

    }

    private void getAllReservationRevenue() {
        System.out.println("1. 무주점 \t 2. 강촌점");
        System.out.print("숙박 매출을 보고싶은 지점을 입력 : ");
        int revenuePointer = Integer.parseInt(sc.nextLine());
        String region = null;
        if(revenuePointer == 1) {
            region = "muju";
        } else if (revenuePointer == 2) {
            region = "gangchon";
        } else {
            System.out.println("잘못된 입력");
            return;
        }
        System.out.println("1. 3개월 조회 \t 2. 특정 날짜 조회");
        String pointer = sc.nextLine();
        switch (pointer) {
            case "1": {
                int grandTotalRevenue = 0;
                Calendar currentDate = Calendar.getInstance();

                for(int i = 0; i < 3; i++) {
                    int totalRevenue = 0;
                    String yearMonth = String.format("%04d.%02d", currentDate.get(Calendar.YEAR),currentDate.get(Calendar.MONTH)+2);
                    Map<Integer, Reservation> reservationList = fileIo.reservationListReader(region);

                    for(Reservation reservation : reservationList.values()) {
                        int revenue = reservation.getRoom().getReservationDates().entrySet().stream()
                                .filter(entry -> entry.getKey().startsWith(yearMonth))
                                .mapToInt(entry -> entry.getValue() ? reservation.getRoom().getPrice() : 0)
                                .sum();
                        totalRevenue += revenue;
                    }
                    grandTotalRevenue += totalRevenue;
                    System.out.println(yearMonth + " : " + totalRevenue);
                    currentDate.add(Calendar.MONTH, -1);
                }
                System.out.println("숙박 매출 총합 : " + grandTotalRevenue);
                break;
            }
            case "2": {
                getSpecificReservationRevenue(region);
                break;
            }
            default:
                System.out.println("잘못된 입력");
                break;
        }
    }

    private void getAllProductRevenue() {
        System.out.println("1. 무주점 \t 2. 강촌점");
        System.out.print("숙박 매출을 보고싶은 지점을 입력 : ");
        int revenuePointer = Integer.parseInt(sc.nextLine());
        String region = null;
        if(revenuePointer == 1) {
            region = "muju";
        } else if (revenuePointer == 2) {
            region = "gangchon";
        } else {
            System.out.println("잘못된 입력");
            return;
        }
        Map<String, Integer> monthlyRevenueMap = new HashMap<>();
        Calendar currentDate = Calendar.getInstance();
        Map<Integer, Reservation> reservationList = fileIo.reservationListReader(region);

        for (int i = 0; i < 3; i++) {
            int totalRevenue = 0;
            String yearMonth = String.format("%04d.%02d", currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH) + 2);
            for (Reservation reservation : reservationList.values()) {
                for (Product product : reservation.getProducts().values()) {
                    int revenueForMonth = product.getRentalDates().entrySet().stream()
                            .filter(entry -> entry.getKey().startsWith(yearMonth))
                            .mapToInt(entry -> entry.getValue() ? product.getPrice() : 0)
                            .sum();
                    totalRevenue += revenueForMonth;
                }
            }
            monthlyRevenueMap.put(yearMonth, totalRevenue);
            System.out.println(yearMonth + " : " + totalRevenue);
            currentDate.add(Calendar.MONTH, -1);
        }
        int grandTotalRevenue = monthlyRevenueMap.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("총 합: " + grandTotalRevenue);
    }

    private void getSpecificReservationRevenue(String region) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

        try {
            int grandTotalRevenue = 0;
            Calendar startDate = Calendar.getInstance();
            Calendar endDate = Calendar.getInstance();
            System.out.println("조회하려는 첫 날짜 입력 2024.04.01");
            startDate.setTime(sdf.parse(sc.nextLine()));
            System.out.println("조회하려는 마지막 날짜 입력 2024.04.03");
            endDate.setTime(sdf.parse(sc.nextLine()));
            endDate.add(Calendar.DATE, 1);
            int totalRevenue = 0;
            Map<Integer, Reservation> reservationList = fileIo.reservationListReader(region);

            for(Reservation reservation : reservationList.values()) {
                int revenue = reservation.getRoom().getReservationDates().entrySet().stream()
                        .filter(entry -> {
                            try {
                                    Calendar reservationDate = Calendar.getInstance();
                                    reservationDate.setTime(sdf.parse(entry.getKey()));
                                    return !reservationDate.before(startDate) && reservationDate.before(endDate);
                            } catch (Exception e) {
                                    e.printStackTrace();
                                    return false;
                            }})
                            .mapToInt(entry -> entry.getValue() ? reservation.getRoom().getPrice() : 0)
                            .sum();

                totalRevenue += revenue;
                }
                grandTotalRevenue += totalRevenue;
            System.out.println("숙박 매출 총합 : " + grandTotalRevenue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String numberGen(int len) {

        Random rand = new Random();
        String numStr = "";

        for(int i=0;i<len;i++) {
            String ran = Integer.toString(rand.nextInt(10));
                if(!numStr.contains(ran)) {
                    numStr += ran;
                } else {
                    i-=1;
                }
            }
        return numStr;
    }

    private void combineAddProduct(String region ,String name) {
        productList = fileIo.productListReader(name,region);
        if(productList != null && !productList.isEmpty()){
            productList.entrySet().stream()
                    .forEach(entry -> System.out.println("[확인용 출력] getKey : " + entry.getKey() + "  | getValue : " + entry.getValue()));
        } else {
            fileIo.productListWriter(name,region,new HashMap<String, Boolean>());
            productList = new HashMap<String, Product>();
        }
        String size = null;
        int howMany = 0;
        int price = 0;

        if(name.equals("Equipment")) {
            System.out.println("추가할 "+ name + " 개수 / 가격");
            System.out.println("2/5000");
            String[] productInput = sc.nextLine().split("/");
            size = "Free";
            howMany = Integer.parseInt(productInput[0].trim());
            price = Integer.parseInt(productInput[1].trim());

        } else {
            System.out.println("추가할 "+ name + " 사이즈 / 개수 / 가격");
            System.out.println("s/2/5000");
            String[] productInput = sc.nextLine().split("/");
            String inputSize = productInput[0].trim().toUpperCase();
            if(Arrays.asList("S", "M", "L").contains(inputSize)) {
                size = inputSize;
            }
            howMany = Integer.parseInt(productInput[1].trim());
            price = Integer.parseInt(productInput[2].trim());
        }
        for(int i = 0; i < howMany; i++) {
            productList.put(numberGen(4), new Product(Integer.parseInt(numberGen(4)),size,price,new HashMap<String, Boolean>()));
        }
        fileIo.productListWriter(name, region, productList);
    }

    private void combineDeleteProduct(String region, String name) {
        productList = fileIo.productListReader(name,region);
        if(productList != null && !productList.isEmpty()){
            productList.entrySet().stream()
                    .forEach(entry -> System.out.println("[확인용 출력] getKey : " + entry.getKey() + "  | getValue : " + entry.getValue()));
        } else {
            System.out.println("등록된 재고가 없습니다.");
            return;
        }
        System.out.print("삭제할 헬멧 고유번호 : ");
        String inputSerialNum = sc.nextLine();
        try {
            if(productList.containsKey(inputSerialNum)) {
                productList.remove(inputSerialNum);
                fileIo.productListWriter(name, region, productList);
                System.out.println(inputSerialNum + " 번호의 "+ name +"이 삭제되었습니다.");
            } else {
                System.out.println("해당 번호의 "+name+"이 존재하지 않습니다.");
            }
        } catch(NumberFormatException e) {
            System.out.println("올바른 고유번호를 입력해주세요.");
        }
    }
}