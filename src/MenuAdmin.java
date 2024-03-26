import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class MenuAdmin {
    private Admin admin;
    public Scanner sc;
    public FileIO fileIo;
    Map<String, Member> memberList;
    Map<Integer, Reservation> reservationList;
    ValidationUtils validationUtils;

    Map<String, Product> productList;

    MenuAdmin() {
        sc = new Scanner(System.in);
        fileIo = new FileIO();
    }

    public void run(Admin admin) {
        this.admin = admin;
        String pointer = null;
        do {
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            System.out.println("\t\t\t\t\t\t\t\t1. 회원 관리" );
            System.out.println("\t\t\t\t\t\t\t\t2. 예약 관리" );
            System.out.println("\t\t\t\t\t\t\t\t3. 장비 관리" );
            System.out.println("\t\t\t\t\t\t\t\t4. 매출 조회" );
            System.out.println("\t\t\t\t\t\t\t\t5. 로그 아웃" );
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
//            System.out.println( "1. 회원 관리, 2. 예약 내역 관리, 3. 장비 관리, 4. 매출 조회, 5. 로그아웃" );
            pointer = sc.nextLine();
            switch (pointer) {
                case "1": {
                    System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                    System.out.println("\t\t\t\t\t\t\t\t1. 전체 회원 조회" );
                    System.out.println("\t\t\t\t\t\t\t\t2. 특정 회원 조회" );
                    System.out.println("\t\t\t\t\t\t\t\t3. 회원 탈퇴 처리" );
                    System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                    System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
//                    System.out.println("1. 회원 전체 조회, 2. 회원 정보 검색, 3. 회원 탈퇴 처리");
                    String pointerMemberManage = sc.nextLine();
                    if (pointerMemberManage.equals("1")) {
                        getAllMemberListInfo();
                    } else if (pointerMemberManage.equals("2")) {
                        getOneMemberInfo();
                    } else if (pointerMemberManage.equals("3")) {
                        deleteOneMember();
                    } else {
                        System.out.println("잘못된 입력");
                    }
                    break;
                }

                case "2": {
                    System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                    System.out.println("\t\t\t\t\t\t\t\t1. 전체 예약 조회");
                    System.out.println("\t\t\t\t\t\t\t\t2. 특정 회원 예약 조회");
                    System.out.println("\t\t\t\t\t\t\t\t3. 지점별 날짜 예약 조회");
                    System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                    System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
//                    System.out.println("1. 예약 목록 전체 조회, 2. 특정 회원 예약내역 조회");
                    String pointerReservationManage = sc.nextLine();
                    if (pointerReservationManage.equals("1")) {
                        getAllReservationInfo();
                    } else if (pointerReservationManage.equals("2")) {
                        getReservationInfoByOneMember();
                    }  else if (pointerReservationManage.equals("3")) {
                        getReservationInfoByDate();
                    } else {
                        System.out.println("잘못된 입력");
                    }
                    break;
                }
                case "3": {
                    System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                    System.out.println("\t\t\t\t\t\t\t\t1. 전체 장비 조회" );
                    System.out.println("\t\t\t\t\t\t\t\t2. 장비 개수 추가" );
                    System.out.println("\t\t\t\t\t\t\t\t3. 장비 개수 삭제" );
                    System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                    System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
//                    System.out.println("1. 대여 장비 전체 재고 조회, 2. 장비 개수 추가, 3. 장비 개수 삭제");
                    String pointerProductManage = sc.nextLine();
                    if (pointerProductManage.equals("1")) {
                        getAllProductInfo();
                    } else if (pointerProductManage.equals("2")) {
                        addProduct();
                    } else if (pointerProductManage.equals("3")) {
                        deleteProduct();
                    } else {
                        System.out.println("잘못된 입력");
                    }
                    break;
                }
                case "4": {
                    System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                    System.out.println("\t\t\t\t\t\t\t\t1. 숙박 매출 조회" );
                    System.out.println("\t\t\t\t\t\t\t\t2. 장비 매출 조회" );
                    System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                    System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
//                    System.out.println("1. 숙박 매출 조회, 2. 대여 장비 매출 조회");
                    String pointerRevenueManage = sc.nextLine();
                    if (pointerRevenueManage.equals("1")) {
                        getAllReservationRevenue();
                    } else if (pointerRevenueManage.equals("2")) {
                        getAllProductRevenue();
                    } else {
                        System.out.println("잘못된 입력");
                    }
                    break;
                }

                case "5": {
                    System.out.println(admin.getUserId() + "님 로그아웃 되었습니다!");
                    break;
                }
                default:
                    System.out.println("잘못된 입력");
            }
        } while (!pointer.equals("5"));
    }

    private void getAllMemberListInfo() {
        memberList = fileIo.memberListReader();
        if (memberList != null) {
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            System.out.format("%-7s \t %-9s \t %-15s \t %-7s \t %-10s \t %s\n", "이름", "아이디" , "전화번호", "등급", "포인트", "총 예약 건수");
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            // 회원 정보 출력
            memberList.entrySet().stream()
                    .forEach(entry -> System.out.format("%-7s \t %-13s \t %-15s \t %-7s \t %-10d \t %s\n",
                            entry.getValue().getName(),
                            entry.getValue().getUserId(),
                            entry.getValue().getPhoneNumber(),
                            entry.getValue().getGrade(),
                            entry.getValue().getPoint(),
                            entry.getValue().getReservationNumberList().size()));
        } else {
            System.out.println("회원 정보가 없습니다.");
        }
    }

    private void getOneMemberInfo() {
        memberList = fileIo.memberListReader();
        validationUtils = new ValidationUtils();
        String searchInfo = validationUtils.getValidation(sc, AuthValidation.SEARCH_MEMBER_INFO);
        String [] splitInfo = searchInfo.split("/");
        String searchName = splitInfo[0].trim();
        String phoneNumLastFour = splitInfo[1].trim();

        boolean isEmptySearchedMemberByName = memberList.entrySet().stream().anyMatch(entry -> entry.getValue().getName().equals(searchName) && entry.getValue().getPhoneNumber().endsWith(phoneNumLastFour));

        if (isEmptySearchedMemberByName) {
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            System.out.format("%-8s  %-11s  %-5s  %-9s\n", "아이디", "전화번호", "등급", "포인트");
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            memberList.entrySet().stream()
                    .filter(entry -> entry.getValue().getName().equals(searchName))
                    .forEach(entry -> System.out.format("%-10s  %-13s  %-6s  %-10d\n",
                            entry.getKey(),
                            entry.getValue().getPhoneNumber(),
                            entry.getValue().getGrade(),
                            entry.getValue().getPoint()));
        } else {
            System.out.println("일치하는 회원 정보가 없습니다.");
        }
    }


    private void deleteOneMember() {
        validationUtils = new ValidationUtils();
        String searchInfo = validationUtils.getValidation(sc, AuthValidation.SEARCH_MEMBER_INFO);
        String [] splitInfo = searchInfo.split("/");
        String searchName = splitInfo[0].trim();
        String phoneNumLastFour = splitInfo[1].trim();
        memberList = fileIo.memberListReader();
        boolean removed = memberList.entrySet().removeIf(entry -> entry.getValue().getName().equals(searchName) && entry.getValue().getPhoneNumber().endsWith(phoneNumLastFour));
        if (removed) {
            System.out.println(searchName + " 회원의 정보가 성공적으로 삭제되었습니다.");
            fileIo.memberListWriter(memberList); // 변경된 회원 목록을 파일에 다시 쓰기
        } else {
            System.out.println(searchName + " 회원을 찾을 수 없습니다.");
        }
    }

    private void getAllReservationInfo() {
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t\t1. 무주점" );
        System.out.println("\t\t\t\t\t\t\t\t2. 강촌점" );
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
//        System.out.println("1. 무주점  2. 강촌점");
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
            System.out.println("이름 \t 아이디 \t 방 번호 \t 예약 금액 \t 체크인 \t 체크아웃 \t 장비 대여 수");
            reservationList.values().forEach(reservation -> {
                Set<Map.Entry<String, Boolean>> reservationDates = reservation.getRoom().getReservationDates().entrySet();
                System.out.println(reservation.getMember().getName() + "\t" +
                        reservation.getMember().getUserId() + "\t" +
                        reservation.getRoom().getRoomNumber() + "\t" +
                        reservation.getRoom().getPrice() + "\t" +
                        reservation.getRoom().getReservationDates().entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).min(String::compareTo).orElse("예약이 없습니다.") + "\t" +
                        reservation.getRoom().getReservationDates().entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).max(String::compareTo).orElse("예약이 없습니다.") + "\t" +
                        reservation.getProducts().values().stream().count());
            });
        } else {
            System.out.println("예약 내역이 없습니다.");
        }
    }



    private void getReservationInfoByOneMember() {
        validationUtils = new ValidationUtils();
        String searchInfo = validationUtils.getValidation(sc, AuthValidation.SEARCH_MEMBER_INFO);
        String [] splitInfo = searchInfo.split("/");
        String searchName = splitInfo[0].trim();
        String phoneNumLastFour = splitInfo[1].trim();

        List<Reservation> mujuReservations = loadAndFilterReservations("muju", searchName, phoneNumLastFour);
        List<Reservation> gangchonReservations = loadAndFilterReservations("gangchon", searchName, phoneNumLastFour);

        printFilteredReservations(mujuReservations, "무주점");
        printFilteredReservations(gangchonReservations, "강촌점");
    }

    private void getReservationInfoByDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        sdf.setLenient(false);

        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t\t1. 무주점" );
        System.out.println("\t\t\t\t\t\t\t\t2. 강촌점" );
        System.out.println("\t\t\t\t\t\t숙박 매출을 보고싶은 지점을 입력하세요.");
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
        String revenuePointer = sc.nextLine();
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        String region = null;
        if(revenuePointer.equals("1")) {
            region = "muju";
        } else if (revenuePointer.equals("2")) {
            region = "gangchon";
        } else {
            System.out.println("잘못된 입력");
            return;
        }

        try {
            Calendar startDate = Calendar.getInstance();
            Calendar endDate = Calendar.getInstance();
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            System.out.println("\t\t\t\t\t\t\t조회하려는 첫 날짜를 입력하세요.");
            System.out.println("\t\t\t\t\t\t\t\tyyyy.mm.dd 형식");
            System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
            startDate.setTime(sdf.parse(sc.nextLine()));
            System.out.println("\t\t\t\t\t\t\t조회하려는 끝 날짜를 입력하세요.");
            System.out.println("\t\t\t\t\t\t\t\tyyyy.mm.dd 형식");
            System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
            endDate.setTime(sdf.parse(sc.nextLine()));
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            if (!startDate.before(endDate)) {
                System.out.println("시작 날짜가 마지막 날짜보다 뒤에 있습니다. 다시 입력해 주세요.");
                return;
            }
            endDate.add(Calendar.DATE, 1); // 마지막 날짜를 포함 안시켜서 1일 더해줌
//            reservationList = fileIo.reservationListReader(region);
            Map<Integer, Reservation> reservationList = fileIo.reservationListReader(region);
            if (reservationList != null && !reservationList.isEmpty()) {
                System.out.println("이름\t아이디\t방 번호\t예약 금액\t체크인\t체크아웃\t장비 대여 수");
                reservationList.values().forEach(reservation -> {
                    // 예약된 각 날짜에 대해 검사
                    boolean isOverlap = reservation.getRoom().getReservationDates().entrySet().stream()
                            .filter(Map.Entry::getValue) // 예약 가능한 날짜만 필터링
                            .anyMatch(entry -> {
                                try {
                                    Calendar reservationDate = Calendar.getInstance();
                                    reservationDate.setTime(sdf.parse(entry.getKey()));
                                    return !reservationDate.before(startDate) && reservationDate.before(endDate);
                                    // 예약 날짜가 시작 날짜와 끝 날짜 사이인지 확인

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    return false;
                                }
                            });
                    if (isOverlap) { // 겹치는 날짜가 있으면, 예약 정보 출력
                        // 예약 정보 출력 로직
                        System.out.println(reservation.getMember().getName() + "\t" +
                                reservation.getMember().getUserId() + "\t" +
                                reservation.getRoom().getRoomNumber() + "\t" +
                                reservation.getRoom().getPrice() + "\t" +
                                reservation.getRoom().getReservationDates().entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).min(String::compareTo).orElse("예약이 없습니다.") + "\t" +
                                reservation.getRoom().getReservationDates().entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).max(String::compareTo).orElse("예약이 없습니다.") + "\t" +
                                reservation.getProducts().values().stream().count());
                    }
                });
            } else {
                System.out.println("해당 기간에 예약 내역이 없습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Reservation> loadAndFilterReservations(String location, String searchName, String phoneNumLastFour) {
        reservationList = fileIo.reservationListReader(location);
        return reservationList.values().stream()
                .filter(reservation -> reservation.getMember().getName().equals(searchName) &&
                        reservation.getMember().getPhoneNumber().endsWith(phoneNumLastFour))
                .collect(Collectors.toList());
    }

    private void printFilteredReservations(List<Reservation> filteredReservations, String locationName) {
        if (!filteredReservations.isEmpty()) {
            System.out.println("[ "+locationName + " ]");
            filteredReservations.forEach(reservation ->
                    System.out.println(reservation.getMember().getName() + "\t" +
                            reservation.getMember().getPhoneNumber() + "\t" +
                            reservation.getRoom().getRegion() + "\t" +
                            reservation.getRoom().getRoomNumber() + "\t" +
                            reservation.getRoom().getCapacity() + "\t" +
                            reservation.getRoom().getPrice() + "\t" +
                            reservation.getRoom().getReservationDates().entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).min(String::compareTo).orElse("예약이 없습니다.") + "\t" +
                            reservation.getRoom().getReservationDates().entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).max(String::compareTo).orElse("예약이 없습니다.") + "\t" +
                            reservation.getProducts().values().stream().count()));
        } else {
            System.out.println("[ "+ locationName + " ]");
            System.out.println("예약 내역이 없습니다.");
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
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t\t1. 무주점" );
        System.out.println("\t\t\t\t\t\t\t\t2. 강촌점" );
        System.out.println("\t\t\t\t\t\t원하는 장비 지점을 입력하세요.");
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
//        System.out.println("1. 무주 / 2. 강촌");
        String regionInput = sc.nextLine();
        String region = null;
        if( regionInput.equals("1")) {
            region = "muju";
        } else if ( regionInput.equals("2")) {
            region = "gangchon";
        } else {
            System.out.println("잘못된 입력");
            return;
        }
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t\t1. 헬멧" );
        System.out.println("\t\t\t\t\t\t\t\t2. 의류" );
        System.out.println("\t\t\t\t\t\t\t\t3. 장비" );
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
//        System.out.println("1. 헬멧 \t 2. 의류 \t 3. 장비");
        String productPointer = sc.nextLine();
        switch (productPointer) {
            case "1" :{
                combineAddProduct(region , "Helmet");
                break;}
            case "2" : {
                combineAddProduct(region , "Clothes");
                break;}
            case "3" :{
                combineAddProduct(region , "Equipment");
                break;}
            default: {
                System.out.println("잘못된 입력");
                break;
            }
        }
    }

    private void deleteProduct() {
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t\t1. 무주점" );
        System.out.println("\t\t\t\t\t\t\t\t2. 강촌점" );
        System.out.println("\t\t\t\t\t\t원하는 장비의 지점을 입력하세요.");
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
//        System.out.println("1. 무주 / 2. 강촌");
        String regionInput = sc.nextLine();
        String region = null;
        if( regionInput.equals("1")) {
            region = "muju";
        } else if ( regionInput.equals("2")) {
            region = "gangchon";
        } else {
            System.out.println("잘못된 입력");
            return;
        }
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t\t1. 헬멧" );
        System.out.println("\t\t\t\t\t\t\t\t2. 의류" );
        System.out.println("\t\t\t\t\t\t\t\t3. 장비" );
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
        String productPointer = sc.nextLine();
        switch (productPointer) {
            case "1" :{
                combineDeleteProduct(region, "Helmet");
                break;}
            case "2" : {
                combineDeleteProduct(region, "Clothes");
                break;}
            case "3" :{
                combineDeleteProduct(region, "Equipment");
                break;}
            default: {
                System.out.println("잘못된 입력");
                break;
            }
        }

    }

    private void getAllReservationRevenue() {
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t\t1. 무주점" );
        System.out.println("\t\t\t\t\t\t\t\t2. 강촌점" );
        System.out.println("\t\t\t\t\t\t숙박 매출을 보고싶은 지점을 입력하세요.");
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
//        System.out.println("1. 무주점 / 2. 강촌점");
        String revenuePointer = sc.nextLine();
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        String region = null;
        if(revenuePointer.equals("1")) {
            region = "muju";
        } else if (revenuePointer.equals("2")) {
            region = "gangchon";
        } else {
            System.out.println("잘못된 입력");
            return;
        }
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t\t1. 최근 3개월 매출 조회" );
        System.out.println("\t\t\t\t\t\t\t\t2. 특정 기간 매출 조회" );
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
//        System.out.println("1. 3개월 조회 \t 2. 특정 날짜 조회");
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
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t\t1. 무주점" );
        System.out.println("\t\t\t\t\t\t\t\t2. 강촌점" );
        System.out.print("\t\t\t\t\t\t숙박 매출을 보고싶은 지점을 입력 : ");
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
//        System.out.println("1. 무주점 \t 2. 강촌점");
        String revenuePointer = sc.nextLine();
        String region = null;
        if(revenuePointer.equals("1")) {
            region = "muju";
        } else if (revenuePointer.equals("2")) {
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
        sdf.setLenient(false);

        try {
            int grandTotalRevenue = 0;
            Calendar startDate = Calendar.getInstance();
            Calendar endDate = Calendar.getInstance();
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            System.out.println("\t\t\t\t\t\t\t조회하려는 첫 날짜를 입력하세요." );
            System.out.println("\t\t\t\t\t\t\t\tyyyy.mm.dd 형식" );
            System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
            startDate.setTime(sdf.parse(sc.nextLine()));
            System.out.println("\t\t\t\t\t\t\t조회하려는 끝 날짜를 입력하세요." );
            System.out.println("\t\t\t\t\t\t\t\tyyyy.mm.dd 형식" );
            System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
            endDate.setTime(sdf.parse(sc.nextLine()));
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
//            System.out.println("조회하려는 첫 날짜 입력 2024.04.01");
//            System.out.println("조회하려는 마지막 날짜 입력 2024.04.03");

            if (!startDate.before(endDate)) {
                System.out.println("시작 날짜가 마지막 날짜보다 뒤에 있습니다. 다시 입력해 주세요.");
                return; // 함수 종료
            }
            endDate.add(Calendar.DATE, 1); // 마지막 날짜를 포함 안시켜서 1일 더해줌

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
        } catch (ParseException e) {
            System.out.println("날짜 형식이 잘못되었습니다. 올바른 날짜 형식으로 입력해주세요.");
        }catch (Exception e) {
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

        if (productList.size() >= 75) {
            System.out.println("제품의 개수가 75개를 초과할 수 없습니다.");
            return;
        }

        String size = null;
        int howMany = 0;
        int price = 0;
        validationUtils = new ValidationUtils();

        if(name.equals("Equipment")) {
                String searchInfo = validationUtils.getValidation(sc, AuthValidation.EQUIPMENT_ADD);
                String [] productInput = searchInfo.split("/");
                howMany = Integer.parseInt(productInput[0].trim());
                price = Integer.parseInt(productInput[1].trim());
                size = "Free";
        } else if(name.equals("Helmet") || name.equals("Clothes")){
                String searchInfo = validationUtils.getValidation(sc, AuthValidation.HELMET_CLOTHES_ADD);
                String [] productInput = searchInfo.split("/");
                size = productInput[0].trim().toUpperCase();
                howMany = Integer.parseInt(productInput[1].trim());
                price = Integer.parseInt(productInput[2].trim());
        } else {
            System.out.println("잘못된 입력");
            return;
        }

        if (productList.size() + howMany > 75) {
            System.out.println("제품을 추가할 수 없습니다. 최대 제한을 초과합니다.");
            return;
        }

        for(int i = 0; i < howMany; i++) {
            productList.put(numberGen(4), new Product(size,price,new HashMap<String, Boolean>()));
        }
        fileIo.productListWriter(name, region, productList);
    }

    private void combineDeleteProduct(String region, String name) {
        boolean exitDeleteProduct = true;
        do {
            productList = fileIo.productListReader(name,region);
            if(productList != null && !productList.isEmpty()){
                productList.entrySet().stream()
                        .forEach(entry -> System.out.println("[확인용 출력] getKey : " + entry.getKey() + "  | getValue : " + entry.getValue()));
            } else {
                System.out.println("등록된 재고가 없습니다.");
                return;
            }
            System.out.print("삭제할 " + name + " 고유번호 : ");
            String inputSerialNum = sc.nextLine();
            try {
                if(productList.containsKey(inputSerialNum)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
                    String today = dateFormat.format(new Date());
                    for (Map.Entry<String, Boolean> d : productList.get(inputSerialNum).getRentalDates().entrySet()){
                        if(d.getKey().compareTo(today) > 0){
                            System.out.println("예약이 있는 상품은 삭제가 불가능");
                            return;
                        }
                    }
                    productList.remove(inputSerialNum);
                    fileIo.productListWriter(name, region, productList);
                    System.out.println(inputSerialNum + " 번호의 " + name + "이 삭제되었습니다.");
                    System.out.println("계속 하시려면 0, 나가려면 엔터");
                    if(sc.nextLine().equals("0")) {
                        exitDeleteProduct = true;
                    } else {
                        exitDeleteProduct = false;
                    }
                } else {
                    System.out.println("해당 번호의 "+ name + "이 존재하지 않습니다. 계속 하려면 0 , 나가려면 엔터");
                    if(sc.nextLine().equals("0")) {
                        exitDeleteProduct = true;
                    } else {
                        exitDeleteProduct = false;
                    }
                }
            } catch(NumberFormatException e) {
                System.out.println("올바른 고유번호를 입력해주세요.");
            }
        } while (exitDeleteProduct);
    }
}