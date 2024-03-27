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

    private Map<Integer, Room> roomList;

    MenuAdmin() {
        sc = new Scanner(System.in);
        fileIo = new FileIO();
    }

    public void run(Admin admin) {
        this.admin = admin;
        String pointer = null;
        do {
            System.out.println(AnsiColor.purple("\n\n\t\t\t\t\t\t\t   < 관리자 메뉴 >"));
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            System.out.println("\t\t\t\t\t\t\t\t1. 회원 관리" );
            System.out.println("\t\t\t\t\t\t\t\t2. 예약 관리" );
            System.out.println("\t\t\t\t\t\t\t\t3. 장비 관리" );
            System.out.println("\t\t\t\t\t\t\t\t4. 매출 조회" );
            System.out.println("\t\t\t\t\t\t\t\t5. 방 생성" );
            System.out.println("\t\t\t\t\t\t\t\t6. 방 확인" );
            System.out.println("\t\t\t\t\t\t\t\t7. 로그 아웃" );
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
//            System.out.println( "1. 회원 관리, 2. 예약 내역 관리, 3. 장비 관리, 4. 매출 조회, 5. 로그아웃" );
            pointer = sc.nextLine();
            switch (pointer) {
                // 회원 관리
                case "1": {
                    System.out.println(AnsiColor.purple("\n\n\t\t\t\t\t\t\t   < 회원 관리 >"));
                    System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                    System.out.println("\t\t\t\t\t\t\t\t1. 전체 회원 조회" );
                    System.out.println("\t\t\t\t\t\t\t\t2. 특정 회원 조회" );
                    System.out.println("\t\t\t\t\t\t\t\t3. 회원 탈퇴 처리" );
                    System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                    System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
//                    System.out.println("1. 회원 전체 조회, 2. 회원 정보 검색, 3. 회원 탈퇴 처리");
                    String pointerMemberManage = sc.nextLine();
                    if (pointerMemberManage.equals("1")) {
                        // 전체 회원 조회
                        System.out.println(AnsiColor.purple("\n\n\t\t\t\t\t\t\t   < 전체 회원 조회 >"));
                        getAllMemberListInfo();
                    } else if (pointerMemberManage.equals("2")) {
                        // 특정 회원 조회
                        System.out.println(AnsiColor.purple("\n\n\t\t\t\t\t\t\t< 특정 회원 조회 >"));
                        getOneMemberInfo();
                    } else {
                        System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t  잘못된 입력"));
                    }
                    break;
                }
                // 예약 관리
                case "2": {
                    System.out.println(AnsiColor.purple("\n\n\t\t\t\t\t\t\t   < 예약 관리 >"));
                    System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                    System.out.println("\t\t\t\t\t\t\t\t1. 전체 예약 조회");
                    System.out.println("\t\t\t\t\t\t\t\t2. 특정 회원 예약 조회");
                    System.out.println("\t\t\t\t\t\t\t\t3. 지점별 날짜 예약 조회");
                    System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                    System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
                    String pointerReservationManage = sc.nextLine();
                    if (pointerReservationManage.equals("1")) {
                        // 전체 예약 조회
                        System.out.println(AnsiColor.purple("\n\n\t\t\t\t\t\t\t   < 전체 예약 조회 >"));
                        getAllReservationInfo();
                    } else if (pointerReservationManage.equals("2")) {
                        // 특정 회원 예약 조회
                        System.out.println(AnsiColor.purple("\n\n\t\t\t\t\t\t\t   < 특정 회원 예약 조회 >"));
                        getReservationInfoByOneMember();
                    }  else if (pointerReservationManage.equals("3")) {
                        // 지점별 날짜 예약 조회
                        System.out.println(AnsiColor.purple("\n\n\t\t\t\t\t\t  < 지점별 날짜 예약 조회 >"));
                        getReservationInfoByDate();
                    } else {
                        System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t  잘못된 입력"));
                    }
                    break;
                }
                // 장비 관리
                case "3": {
                    System.out.println(AnsiColor.purple("\n\n\t\t\t\t\t\t\t   < 장비 관리 >"));
                    System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                    System.out.println("\t\t\t\t\t\t\t\t1. 전체 장비 조회" );
                    System.out.println("\t\t\t\t\t\t\t\t2. 장비 개수 추가" );
                    System.out.println("\t\t\t\t\t\t\t\t3. 장비 개수 삭제" );
                    System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                    System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
                    String pointerProductManage = sc.nextLine();
                    if (pointerProductManage.equals("1")) {
                        // 전체 장비 조회
                        System.out.println(AnsiColor.purple("\n\n\t\t\t\t\t\t\t   < 전체 장비 조회 >"));
                        getAllProductInfo();
                    } else if (pointerProductManage.equals("2")) {
                        // 장비 개수 추가
                        System.out.println(AnsiColor.purple("\n\n\t\t\t\t\t\t\t < 장비 개수 추가 >"));
                        addProduct();
                    } else if (pointerProductManage.equals("3")) {
                        // 장비 개수 삭제
                        System.out.println(AnsiColor.purple("\n\n\t\t\t\t\t\t\t < 장비 개수 삭제 >"));
                        deleteProduct();
                    } else {
                        System.out.println(AnsiColor.red("\n\n\t\t\t\t\t\t\t\t  잘못된 입력"));
                    }
                    break;
                }

                // 매출 조회
                case "4": {
                    System.out.println(AnsiColor.purple("\n\n\t\t\t\t\t\t\t   < 매출 조회 >"));
                    System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                    System.out.println("\t\t\t\t\t\t\t\t1. 숙박 매출 조회" );
                    System.out.println("\t\t\t\t\t\t\t\t2. 장비 매출 조회" );
                    System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                    System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
                    String pointerRevenueManage = sc.nextLine();
                    if (pointerRevenueManage.equals("1")) {
                        // 숙박 매출 조회
                        System.out.println(AnsiColor.purple("\n\n\t\t\t\t\t\t\t   < 숙박 매출 조회 >"));
                        getAllReservationRevenue();
                    } else if (pointerRevenueManage.equals("2")) {
                        // 장비 매출 조회(3개월)
                        System.out.println(AnsiColor.purple("\n\n\t\t\t\t\t\t   < 장비 매출 조회(3개월) >"));
                        getThreeMonthProductRevenue();
                    } else {
                        System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t  잘못된 입력"));
                    }
                    break;
                }
                // 로그아웃
                case "5": {
                    AF_addRoom();
                    break;
                }

                case "6": {
                    tmp_checkRoom();
                    break;
                }

                case "7": {
                    System.out.println(AnsiColor.green(admin.getUserId() + "님 로그아웃 되었습니다!"));
                    break;
                }
                default:
                    System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t  잘못된 입력"));
            }
        } while (!pointer.equals("5"));
    }

    // 전체 회원 목록
    private void getAllMemberListInfo() {
        memberList = fileIo.memberListReader();
        if (memberList != null) {
            System.out.println("\t+———————————————————————————————————————————————————————————————————————————————————————+");
            System.out.format("\t%-7s \t %-9s \t %-15s \t %-7s \t %-10s \t %s\n", "이름", "아이디" , "전화번호", "등급", "포인트", "총 예약 건수");
            System.out.println("\t+———————————————————————————————————————————————————————————————————————————————————————+");
            // 회원 정보 출력
            memberList.entrySet().stream()
                    .forEach(entry -> System.out.format("\t%-7s \t %-13s \t %-15s \t %-7s \t %-,10d \t %s\n",
                            entry.getValue().getName(),
                            entry.getValue().getUserId(),
                            entry.getValue().getPhoneNumber(),
                            entry.getValue().getGrade(),
                            entry.getValue().getPoint(),
                            entry.getValue().getReservationNumberList().size()));
        } else {
            System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t회원 정보가 없습니다."));
        }
    }


    // 특정 회원 정보 조회
    private void getOneMemberInfo() {
        memberList = fileIo.memberListReader();
        validationUtils = new ValidationUtils();
        // 이름 / 전화번호 뒷4자리 검증
        String searchInfo = validationUtils.getValidation(sc, AuthValidation.SEARCH_MEMBER_INFO);
        String [] splitInfo = searchInfo.split("/");
        String searchName = splitInfo[0].trim();
        String phoneNumLastFour = splitInfo[1].trim();

        // 정보 들어오면 True
        boolean isEmptySearchedMemberByName = memberList.entrySet().stream().anyMatch(entry -> entry.getValue().getName().equals(searchName) && entry.getValue().getPhoneNumber().endsWith(phoneNumLastFour));

        if (isEmptySearchedMemberByName) {
            // 정보 출력 테이블
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            System.out.format("\t\t\t\t%-8s  %-11s  %-5s  %-9s\n", "아이디", "전화번호", "등급", "포인트");
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            memberList.entrySet().stream()
                    .filter(entry -> entry.getValue().getName().equals(searchName))
                    .forEach(entry -> System.out.format("\t\t\t\t%-10s  %-13s  %-6s  %-,10d\n",
                            entry.getKey(),
                            entry.getValue().getPhoneNumber(),
                            entry.getValue().getGrade(),
                            entry.getValue().getPoint()));
        } else {
            System.out.println(AnsiColor.red("\t\t\t\t\t\t  일치하는 회원 정보가 없습니다."));
        }
    }

    // 전체 예약 조회(3개월)
    private void getAllReservationInfo() {
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t\t1. 무주점" );
        System.out.println("\t\t\t\t\t\t\t\t2. 강촌점" );
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
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
                System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t  잘못된 입력")); return;
        }
        if (reservationList != null && !reservationList.isEmpty()) {
            // 정보 출력 테이블
            System.out.println("\t+———————————————————————————————————————————————————————————————————————————————————————————————————+");
            System.out.format("\t%-7s \t %-10s \t %-4s \t %-9s \t %-10s \t %-10s \t %-7s%n",
                    "이름", "아이디", "방 번호", "1일 예약 금액", "체크인", "체크아웃", "장비 대여 수");
            System.out.println("\t+———————————————————————————————————————————————————————————————————————————————————————————————————+");
            reservationList.values().forEach(reservation -> {
//                Set<Map.Entry<String, Boolean>> reservationDates = reservation.getRoom().getReservationDates().entrySet();
            System.out.format("\t%-7s \t %-10s \t %-8s \t %-,13d \t %-10s \t %-10s \t %-7d%n",
                        reservation.getMember().getName(),
                        reservation.getMember().getUserId(),
                        reservation.getRoom().getRoomNumber(),
                        reservation.getRoom().getPrice(),
                        reservation.getRoom().getReservationDates().entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).min(String::compareTo).orElse("예약이 없습니다."),
                        reservation.getRoom().getReservationDates().entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).max(String::compareTo).orElse("예약이 없습니다."),
                        reservation.getProducts().values().stream().count());
            });
        } else {
            System.out.println(AnsiColor.red("\t\t\t\t\t\t\t예약 내역이 없습니다."));
        }
    }


    // 특정 회원 예약 조회
    private void getReservationInfoByOneMember() {
        validationUtils = new ValidationUtils();
        // 이름 / 번호 뒷 4자리 검증
        String searchInfo = validationUtils.getValidation(sc, AuthValidation.SEARCH_MEMBER_INFO);
        String [] splitInfo = searchInfo.split("/");
        String searchName = splitInfo[0].trim();
        String phoneNumLastFour = splitInfo[1].trim();

        // 지점, 이름, 뒷4자리 조회 리스트 담기
        List<Reservation> mujuReservations = loadAndFilterReservations("muju", searchName, phoneNumLastFour);
        List<Reservation> gangchonReservations = loadAndFilterReservations("gangchon", searchName, phoneNumLastFour);

        // 담은 리스트, 지역명 출력
        printFilteredReservations(mujuReservations, "무주점");
        printFilteredReservations(gangchonReservations, "강촌점");
    }

    // 특정 날짜 입력받아 예약 조회
    private void getReservationInfoByDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        // 엄격한 날짜 형식
        sdf.setLenient(false);

        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t\t1. 무주점" );
        System.out.println("\t\t\t\t\t\t\t\t2. 강촌점" );
        System.out.println("\t\t\t\t\t\t 조회하려는 지점을 입력하세요.");
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
        String revenuePointer = sc.nextLine();
        String region = null;
        if(revenuePointer.equals("1")) {
            region = "muju";
        } else if (revenuePointer.equals("2")) {
            region = "gangchon";
        } else {
            System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t  잘못된 입력"));
            return;
        }

        try {
            Calendar startDate = Calendar.getInstance();
            Calendar endDate = Calendar.getInstance();
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            System.out.println("\t\t\t\t\t\t조회하려는 첫 날짜를 입력하세요.");
            System.out.println("\t\t\t\t\t\t\t yyyy.mm.dd 형식");
            System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
            startDate.setTime(sdf.parse(sc.nextLine()));
            System.out.println("\t\t\t\t\t\t조회하려는 끝 날짜를 입력하세요.");
            System.out.println("\t\t\t\t\t\t\t yyyy.mm.dd 형식");
            System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
            endDate.setTime(sdf.parse(sc.nextLine()));
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            if (!startDate.before(endDate)) {
                System.out.println(AnsiColor.red("\t\t\t\t\t시작 날짜가 마지막 날짜보다 뒤에 있습니다."));
                return;
            }
            // 마지막 날짜를 포함 안시켜서 1일 더해줌
            endDate.add(Calendar.DATE, 1);
            Map<Integer, Reservation> reservationList = fileIo.reservationListReader(region);
            if (reservationList != null && !reservationList.isEmpty()) {
                System.out.println("\t+———————————————————————————————————————————————————————————————————————————————————————————————————+");
                System.out.format("\t%-7s\t%-8s\t%-8s\t%-12s\t%-10s\t%-10s\t%-15s%n", "이름", "아이디", "방 번호", "1일 예약 금액", "체크인", "체크아웃", "장비 대여 수");
                System.out.println("\t+———————————————————————————————————————————————————————————————————————————————————————————————————+");
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
                    if (isOverlap) {
                        // 겹치는 날짜가 있으면, 예약 정보 출력
                        // 정보 출력
                        System.out.format("\t%-7s\t%-10s\t%-8s\t%-,12d\t%-15s\t%-15s\t%-15d%n",
                                reservation.getMember().getName(), // 이름
                                reservation.getMember().getUserId(), // 아이디
                                reservation.getRoom().getRoomNumber(), // 방 번호
                                reservation.getRoom().getPrice(), // 예약 금액
                                reservation.getRoom().getReservationDates().entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).min(String::compareTo).orElse("예약 없음"), // 체크인
                                reservation.getRoom().getReservationDates().entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).max(String::compareTo).orElse("예약 없음"), // 체크아웃
                                reservation.getProducts().values().stream().count()); // 장비 대여 수
                    }
                });
            } else {
                System.out.println(AnsiColor.red("\t\t\t\t\t\t해당 기간에 예약 내역이 없습니다."));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 지점, 이름, 뒷4자리 조회
    private List<Reservation> loadAndFilterReservations(String location, String searchName, String phoneNumLastFour) {
        reservationList = fileIo.reservationListReader(location);
        return reservationList.values().stream()
                .filter(reservation -> reservation.getMember().getName().equals(searchName) &&
                        reservation.getMember().getPhoneNumber().endsWith(phoneNumLastFour))
                .collect(Collectors.toList());
    }

    // 필터한 정보 출력
    private void printFilteredReservations(List<Reservation> filteredReservations, String locationName) {
        if (!filteredReservations.isEmpty()) {
            System.out.println("\t\t\t\t\t\t\t\t [ "+locationName + " ]");
            System.out.println("\t+———————————————————————————————————————————————————————————————————————————————————————————————————————————————+");
            System.out.format("\t%-7s\t%-10s\t%-8s\t%-9s\t%-10s\t%-7s\t%-10s\t%-12s\n",
                    "이름", "전화번호", "방 번호", "수용 인원", "1일 예약 가격", "체크인", "체크아웃", "장비 대여 수");
            System.out.println("\t+———————————————————————————————————————————————————————————————————————————————————————————————————————————————+");
            filteredReservations.forEach(reservation ->
                    System.out.format("\t%-7s\t%-15s\t%-10d\t%-9d\t%-,13d\t%-10s\t%-13s\t%-5d%n\n",
                            reservation.getMember().getName(),
                            reservation.getMember().getPhoneNumber(),
                            reservation.getRoom().getRoomNumber(),
                            reservation.getRoom().getCapacity(),
                            reservation.getRoom().getPrice(),
                            reservation.getRoom().getReservationDates().entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).min(String::compareTo).orElse("예약이 없습니다."),
                            reservation.getRoom().getReservationDates().entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).max(String::compareTo).orElse("예약이 없습니다."),
                            reservation.getProducts().values().stream().count()));
        } else {
            System.out.println("\t\t\t\t\t\t\t\t [ "+ locationName + " ]");
            System.out.println(AnsiColor.red("\t\t\t\t\t\t\t  예약 내역이 없습니다."));
        }
    }

    // 전체 장비 조회
    public void getAllProductInfo() {
        productList = fileIo.AllProductListReader();
        if(productList.isEmpty()) {
            System.out.println(AnsiColor.red("\t\t\t\t\t\t  장비 목록이 존재하지 않습니다."));
        }
        // 확인용 출력
        System.out.println("\t+————————————————————————————————————————————————————————————————+");
        System.out.format("\t%-2s\t%-3s\t%-3s\t%-8s\t%-7s\t%-7s\n",
                "지점", "분류" , "번호" , "사이즈", "가격", "대여 날짜");
        System.out.println("\t+————————————————————————————————————————————————————————————————+");
        productList.entrySet().stream().forEach(entry->
            System.out.format("\t%-6s\t%-9s\t%-9s\t%-8s\t\n",
                    entry.getKey(),
                    entry.getValue().getSize(),
                    entry.getValue().getPrice(),
                    entry.getValue().getRentalDates().keySet().stream().map(Object::toString) // Map의 키(날짜)를 문자열로 변환
                            .collect(Collectors.joining(", "))
            ));
    }

    // 장비 추가
    private void addProduct() {
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t\t1. 무주점" );
        System.out.println("\t\t\t\t\t\t\t\t2. 강촌점" );
        System.out.println("\t\t\t\t\t\t  원하는 지점을 입력하세요.");
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
        String regionInput = sc.nextLine();
        String region = null;
        if( regionInput.equals("1")) {
            region = "muju";
        } else if ( regionInput.equals("2")) {
            region = "gangchon";
        } else {
            System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t  잘못된 입력"));
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
                combineAddProduct(region , "Helmet");
                break;}
            case "2" : {
                combineAddProduct(region , "Clothes");
                break;}
            case "3" :{
                combineAddProduct(region , "Equipment");
                break;}
            default: {
                System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t  잘못된 입력"));
                break;
            }
        }
    }

    // 장비 삭제
    private void deleteProduct() {
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t\t1. 무주점" );
        System.out.println("\t\t\t\t\t\t\t\t2. 강촌점" );
        System.out.println("\t\t\t\t\t\t원하는 장비의 지점을 입력하세요.");
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
        String regionInput = sc.nextLine();
        String region = null;
        if( regionInput.equals("1")) {
            region = "muju";
        } else if ( regionInput.equals("2")) {
            region = "gangchon";
        } else {
            System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t  잘못된 입력"));
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
                System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t  잘못된 입력"));
                break;
            }
        }

    }

    // 전체 숙박 매출 조회
    private void getAllReservationRevenue() {
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t\t1. 무주점" );
        System.out.println("\t\t\t\t\t\t\t\t2. 강촌점" );
        System.out.println("\t\t\t\t\t\t 조회하려는 지점을 입력하세요.");
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
            System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t  잘못된 입력"));
            return;
        }
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t\t1. 최근 3개월 매출 조회" );
        System.out.println("\t\t\t\t\t\t\t\t2. 특정 기간 매출 조회" );
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
        String pointer = sc.nextLine();
        switch (pointer) {
            case "1": {
                int grandTotalRevenue = 0;
                Calendar currentDate = Calendar.getInstance();
                System.out.println("\t\t\t\t\t\t+———————————————————————————+");
                System.out.format("\t\t\t\t\t\t%-11s\t%-3s\n",
                        "월", "매출" );
                System.out.println("\t\t\t\t\t\t+———————————————————————————+");
                // 3개월만, 이번달 기준 +- 1개월씩 3번
                for(int i = 0; i < 3; i++) {
                    int totalRevenue = 0;
                    // 다음달 부터 -1개월씩 3번
                    String yearMonth = String.format("%04d.%02d", currentDate.get(Calendar.YEAR),currentDate.get(Calendar.MONTH)+2);
                    Map<Integer, Reservation> reservationList = fileIo.reservationListReader(region);


                    // 2024.04로 시작하는 내역 불러온 후, 금액을 revenue에 저장
                    for(Reservation reservation : reservationList.values()) {
                        int revenue = reservation.getRoom().getReservationDates().entrySet().stream()
                                .filter(entry -> entry.getKey().startsWith(yearMonth))
                                .mapToInt(entry -> entry.getValue() ? reservation.getRoom().getPrice() : 0)
                                .sum();
                        // 1개월 총합 저장
                        totalRevenue += revenue;
                    }
                    // 3개월 총합 저장
                    grandTotalRevenue += totalRevenue;
//                    System.out.println(yearMonth + " : " + totalRevenue);
                    System.out.format("\t\t\t\t\t\t%-12s\t%,d\n",
                            yearMonth, totalRevenue );
                    // -1개월 해주기
                    currentDate.add(Calendar.MONTH, -1);
                }
                if(grandTotalRevenue != 0) {
                    System.out.println("\t\t\t\t\t\t+———————————————————————————+");
                    System.out.format("\t\t\t\t\t\t%-10s\t%,d\n",
                            "숙박 매출 총합", grandTotalRevenue );
                } else {
                    System.out.println(AnsiColor.red("\t\t\t\t\t\t\t예약 내역이 없습니다."));
                }
                break;
            }
            case "2": {
                getSpecificReservationRevenue(region);
                break;
            }
            default:
                System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t  잘못된 입력"));
                break;
        }
    }

    // 장비 3개월간 매출 조회
    private void getThreeMonthProductRevenue() {
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t\t1. 무주점" );
        System.out.println("\t\t\t\t\t\t\t\t2. 강촌점" );
        System.out.println("\t\t\t\t\t\t 조회하려는 지점을 입력하세요.");
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
        String revenuePointer = sc.nextLine();
        String region = null;
        if(revenuePointer.equals("1")) {
            region = "muju";
        } else if (revenuePointer.equals("2")) {
            region = "gangchon";
        } else {
            System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t  잘못된 입력"));
            return;
        }
        Map<String, Integer> monthlyRevenueMap = new HashMap<>();
        Calendar currentDate = Calendar.getInstance();
        Map<Integer, Reservation> reservationList = fileIo.reservationListReader(region);

        System.out.println("\t\t\t\t\t\t+———————————————————————————+");
        System.out.format("\t\t\t\t\t\t%-11s\t%-3s\n",
                "월", "매출" );
        System.out.println("\t\t\t\t\t\t+———————————————————————————+");

        // 개월수를 더 늘리고 싶다면 i < 3을 원하는 개월 수 만큼 증가
        // currentData.get(Calendar.Month + 원하는 개월 수 만큼 증가)
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
            System.out.format("\t\t\t\t\t\t%-12s\t%,d\n",
                    yearMonth, totalRevenue );

            currentDate.add(Calendar.MONTH, -1);
        }
        int grandTotalRevenue = monthlyRevenueMap.values().stream().mapToInt(Integer::intValue).sum();
        if(grandTotalRevenue != 0) {
            System.out.println("\t\t\t\t\t\t+———————————————————————————+");
            System.out.format("\t\t\t\t\t\t%-10s\t%,d\n",
                    "장비 매출 총합", grandTotalRevenue );
        } else {
            System.out.println(AnsiColor.red("\t\t\t\t\t\t\t예약 내역이 없습니다."));
        }

    }

    // 특정 날짜 예약 매출 조회
    private void getSpecificReservationRevenue(String region) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        // 엄격한 날짜 형식
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

            if (!startDate.before(endDate)) {
                System.out.println(AnsiColor.red("\t\t\t\t\t시작 날짜가 마지막 날짜보다 뒤에 있습니다."));
                return; // 메서드 종료
            }
            endDate.add(Calendar.DATE, 1); // 마지막 날짜를 포함 안시켜서 1일 더하기

            int totalRevenue = 0;
            Map<Integer, Reservation> reservationList = fileIo.reservationListReader(region);

            // 방마다의 예약 날짜를 불러온 후
            // 시작 날짜보다 전이 아니라면 && 마지막 날짜보다 전이라면 금액 담기
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
                // 예약마다 금액 저장
                totalRevenue += revenue;
            }
            // 전체 총 예약 금액 저장
            grandTotalRevenue += totalRevenue;
            if(grandTotalRevenue != 0) {
                System.out.format("\t%-10s\t%,d\n",
                        "\t\t\t\t\t\t숙박 매출 총합", grandTotalRevenue );
            } else {
                System.out.println(AnsiColor.red("\t\t\t\t\t\t\t예약 내역이 없습니다."));
            }
        } catch (ParseException e) {
            System.out.println(AnsiColor.red("\t\t\t\t\t날짜 형식을 잘못 입력 하셨습니다."));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 고유값 랜덤 숫자 생성
    private static String numberGen(int len) {

        Random random = new Random();
        String numStr = "";

        for(int i=0;i<len;i++) {
            String randomStr = Integer.toString(random.nextInt(10));
            if(!numStr.contains(randomStr)) {
                numStr += randomStr;
            } else {
                i-=1;
            }
        }
        return numStr;
    }

    // 물품 추가 통합 메서드
    private void combineAddProduct(String region ,String name) {
        productList = fileIo.productListReader(name,region);
        if(productList != null && !productList.isEmpty()){
            System.out.println("\t+—————————————————————————————————————————————————————————+");
            System.out.format("\t%-7s\t%-9s\t%-10s\t%-7s\n",
                    "고유번호", "사이즈", "가격", "대여 날짜");
            System.out.println("\t+—————————————————————————————————————————————————————————+");
            productList.entrySet().stream().forEach(entry->
                    System.out.format("\t%-9s\t%-9s\t%-7s\t%-8s\t\n",
                            entry.getKey(),
                            entry.getValue().getSize(),
                            entry.getValue().getPrice(),
                            entry.getValue().getRentalDates().keySet().stream().map(Object::toString) // Map의 키(날짜)를 문자열로 변환
                                    .collect(Collectors.joining(", "))
                    ));
        } else {
            fileIo.productListWriter(name,region,new HashMap<String, Boolean>());
            productList = new HashMap<String, Product>();
        }

        // 물품 추가 최대 75개 까지
        if (productList.size() >= 75) {
            System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t제품의 개수가 75개를 초과할 수 없습니다."));
            return;
        }

        String size = null;
        String feat = null;
        int howMany = 0;
        int price = 0;

        validationUtils = new ValidationUtils();

        // 장비 일 때
        if(name.equals("Equipment")) {
                // 장비 입력 검증
                // 개수 / 가격
                String searchInfo = validationUtils.getValidation(sc, AuthValidation.EQUIPMENT_ADD);
                String [] productInput = searchInfo.split("/");
                howMany = Integer.parseInt(productInput[0].trim());
                price = Integer.parseInt(productInput[1].trim());
                size = "Free";
                feat = "장비";

        // 헬멧 || 의류 일 때
        } else if(name.equals("Helmet") || name.equals("Clothes")){
                // 헬멧 || 의류 입력 검증
                // 사이즈 / 개수 / 가격
                String searchInfo = validationUtils.getValidation(sc, AuthValidation.HELMET_CLOTHES_ADD);
                String [] productInput = searchInfo.split("/");
                size = productInput[0].trim().toUpperCase();
                howMany = Integer.parseInt(productInput[1].trim());
                price = Integer.parseInt(productInput[2].trim());
                feat = name.equals("Helmet") ? "헬멧" : "의류";
        } else {
            System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t  잘못된 입력"));
            return;
        }

        if (productList.size() + howMany > 75) {
            System.out.println(AnsiColor.red("\t\t\t\t\t제품을 추가할 수 없습니다. 최대 제한을 초과합니다."));
            return;
        }

        for(int i = 0; i < howMany; i++) {
            productList.put(numberGen(4), new Product(size,price,new HashMap<String, Boolean>()));
        }
        fileIo.productListWriter(name, region, productList);
        System.out.println(AnsiColor.green("\t\t\t\t\t  "+feat + " " + size + "/" + price + "원/" + howMany + "개 추가되었습니다."));
    }

    // 물품 삭제 통합 메서드
    private void combineDeleteProduct(String region, String name) {
        boolean exitDeleteProduct = true;
        do {
            productList = fileIo.productListReader(name,region);
            if(productList != null && !productList.isEmpty()){
                System.out.println("\t+—————————————————————————————————————————————————————————+");
                System.out.format("\t%-7s\t%-9s\t%-10s\t%-7s\n",
                        "고유번호", "사이즈", "가격", "대여 날짜");
                System.out.println("\t+—————————————————————————————————————————————————————————+");
                productList.entrySet().stream().forEach(entry->
                        System.out.format("\t%-9s\t%-9s\t%-7s\t%-8s\t\n",
                                entry.getKey(),
                                entry.getValue().getSize(),
                                entry.getValue().getPrice(),
                                entry.getValue().getRentalDates().keySet().stream().map(Object::toString) // Map의 키(날짜)를 문자열로 변환
                                        .collect(Collectors.joining(", "))
                        ));
            } else {
                System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t등록된 재고가 없습니다."));
                return;
            }
            // name을 한글화
            String feat = (name.equals("Helmet")?"헬멧":(name.equals("Clothes")?"의류":"장비"));
            System.out.println("\t\t\t\t\t  삭제할 " + feat + "의 고유번호를 입력하세요.");
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
            String inputSerialNum = sc.nextLine();
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            try {
                if(productList.containsKey(inputSerialNum)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
                    String today = dateFormat.format(new Date());
                    for (Map.Entry<String, Boolean> d : productList.get(inputSerialNum).getRentalDates().entrySet()){
                        if(d.getKey().compareTo(today) > 0){
                            System.out.println(AnsiColor.red("\t\t\t\t\t\t예약된 상품은 삭제하실 수 없습니다."));
                            return; // 메서드 종료
                        }
                    }
                    productList.remove(inputSerialNum);
                    fileIo.productListWriter(name, region, productList);
                    System.out.println(AnsiColor.green("\t\t\t\t\t\t"+inputSerialNum + " / " + name + " 삭제되었습니다."));
                    System.out.println("\t\t\t\t\t     계속 하시려면 0, 나가려면 엔터");
                    if(sc.nextLine().equals("0")) {
                        exitDeleteProduct = true; // 계속
                    } else {
                        exitDeleteProduct = false; // while문 탈출
                    }
                } else {
                    System.out.println(AnsiColor.red("\t\t\t\t\t\t"+ feat + "의 고유번호를 입력하세요."));
                    System.out.println("\t\t\t\t\t     계속 하려면 0 , 나가려면 엔터");
                    if(sc.nextLine().equals("0")) {
                        exitDeleteProduct = true; // 계속
                    } else {
                        exitDeleteProduct = false; // while문 탈출
                    }
                }
            } catch(NumberFormatException e) {
                System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t올바른 고유번호를 입력해주세요."));
            }
        } while (exitDeleteProduct);
    }


    public void AF_addRoom(){
        while (true){
            System.out.println("\n\t+———————————————————————————————————————————————————————————————————+");
            System.out.println("\t\t\t\t\t\t\t\t1. 무주" );
            System.out.println("\t\t\t\t\t\t\t\t2. 강촌" );
            System.out.println(AnsiColor.yellow("\t\t\t\t\t\t\t원하는 방 지점의 번호를 입력하세요. ('x'를 입력하면 상위 메뉴로 돌아갑니다)"));
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
            String where = sc.nextLine();

            if ("x".equalsIgnoreCase(where)) {
                return; // "x" 입력 시 메소드 종료 (상위 메뉴로 돌아감)
            }

            String roomType = "";
            boolean isValidRoomType = false;
            while (!isValidRoomType) {
                System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                System.out.println("\t\t\t\t\t\t\t\tstandard");
                System.out.println("\t\t\t\t\t\t\t\tdeluxe" );
                System.out.println("\t\t\t\t\t\t\t\tFamily" );
                System.out.println(AnsiColor.yellow("\t\t\t\t\t생성할 방의 타입을 입력하세요. ('x'를 입력하면 상위 메뉴로 돌아갑니다)"));
                System.out.println("\t+———————————————————————————————————————————————————————————————————+");
                System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
                roomType = sc.nextLine();

                if ("x".equalsIgnoreCase(roomType)) {
                    return; // "x" 입력 시 메소드 종료
                }

                if (roomType.equalsIgnoreCase("standard") || roomType.equalsIgnoreCase("deluxe") || roomType.equalsIgnoreCase("Family")) {
                    isValidRoomType = true;
                } else {
                    System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t잘못된 방 타입입니다. 다시 입력해 주세요."));
                }
            }

            Room room = null;
            switch (where){
                case "1":
                    AF_BuildRoom mjBuild = new AF_BuildMuJuRoom();
                    room = mjBuild.orderRoom(roomType);
                    break;
                case "2":
                    AF_BuildRoom gcBuild = new AF_BuildGangChonRoom();
                    room = gcBuild.orderRoom(roomType);
                    break;
                default:
                    System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t잘못된 입력입니다."));
                    continue; // 잘못된 입력 시 처음부터 다시 시작
            }

            if (room == null) {
                System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t잘못된 방 타입입니다."));
                continue; // 방 생성 실패 시 처음부터 다시 시작
            }

            where = (where.equals("1") ? "muju" : "gangchon");
            roomList = fileIo.roomListReader(where);
            roomList.put(room.getRoomNumber(), room);
            fileIo.roomListWriter(where, roomList);
            break; // 방 추가 후 루프 종료
        }
    }



    public void tmp_checkRoom(){
        while (true) {
            System.out.println("\n\t+———————————————————————————————————————————————————————————————————+");
            System.out.println(AnsiColor.yellow("\t\t조회할 방 지점의 번호를 입력하세요. ('x'를 입력하면 상위 메뉴로 돌아갑니다)"));
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
            String region = sc.nextLine().trim(); // 입력 값의 앞뒤 공백 제거

            if ("x".equalsIgnoreCase(region)) {
                return; // "x" 입력 시 메서드 종료 (상위 메뉴로 돌아감)
            }

            if (!region.equalsIgnoreCase("muju") && !region.equalsIgnoreCase("gangchon")) {
                System.out.println(AnsiColor.red("\t\t\t\t\t잘못된 입력입니다. 'muju' 또는 'gangchon'을 입력해주세요."));
                continue; // 잘못된 입력 시 다시 입력 받음
            }

            roomList = fileIo.roomListReader(region);
            if (roomList == null || roomList.isEmpty()) {
                System.out.println(AnsiColor.red("\t\t\t\t\t해당 지점에 등록된 방이 없습니다."));
                return; // 방이 없는 경우 메서드 종료
            }


            // 테이블 헤더 출력
            System.out.format("\t%-8s %-12s %-10s %-12s %-20s %-15s %-20s\n",
                    "지점", "방 호수", "인원 수", "가격", "방 종류", "Tv", "Bed");
            System.out.println("\t—————————————————————————————————————————————————————————————————————————————————————————————————————");

            // 테이블 내용 출력 부분에 적용
            for (Map.Entry mem : roomList.entrySet()) {
                Room room = (Room) mem.getValue();
                System.out.format("\t%-8s %-12d %-10d %-12d %-20s %-15s %-20s\n",
                        room.getRegion(),
                        room.getRoomNumber(),
                        room.getCapacity(),
                        room.getPrice(),
                        room.getRoomType(),
                        room.getTv() == null ? "없음" : room.getTv(),
                        room.getBed());
            }
            System.out.println("\t—————————————————————————————————————————————————————————————————————————————————————————————————————");
            return; // 정보 출력 후 메서드 종료
        }
    }

}