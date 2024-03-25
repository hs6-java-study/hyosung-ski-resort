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
    Map<String, Equipment> equitmentList;

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

    public void getAllMemberListInfo() {
        memberList = fileIo.memberListReader();
        if (memberList != null) {
            System.out.println("ID \t 전화번호 \t 등급 \t 포인트 \t");
            memberList.entrySet().stream()
                    .forEach(entry -> System.out.println(entry.getValue().getName() + "\t" + entry.getValue().getPhoneNumber() + "\t" + entry.getValue().getGrade() + "\t" + entry.getValue().getPoint()));
        } else {
            System.out.println("회원 정보가 없습니다.");
        }
    }

    public void getOneMemberInfo() {
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


    public void deleteOneMember() {
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

    public void getAllReservationInfo() {
        // 예약하기 제대로 안되서 미확인
        System.out.println("1. 무주점  2. 강촌점");
        int regionPointer = Integer.parseInt(sc.nextLine());
        switch (regionPointer) {
            case 1: {
                reservationList = fileIo.reservationListReader("muju");
                break;
            }
            case 2: {
                reservationList = fileIo.reservationListReader("gangchon");
                break;
            }
            default:
                System.out.println("잘못된 입력");
                return;
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

    public void getReservationInfoByOneMember() {
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
        productList = fileIo.ProductListReader();
        // 확인용 출력
        productList.entrySet().stream()
                .forEach(entry -> System.out.println("[확인용 출력] " + entry.getKey() + " | product 정보 : " + entry.getValue()));
    }

    public void addProduct() {

        System.out.println("=====무주점=====");
        System.out.println("1. 헬멧 \t 2. 의류 \t 3. 장비");
        System.out.println("=====강촌점=====");
        System.out.println("4. 헬멧 \t 5. 의류 \t 6. 장비");
        int productPointer = Integer.parseInt(sc.nextLine());
        switch (productPointer) {
            case 1: {
                Map<String, Helmet> helmetList = fileIo.helmetListReader("muju");
                if (helmetList != null && !helmetList.isEmpty()) {
                } else {
                    fileIo.helmetListWriter("muju", null);
                    helmetList = new HashMap<String, Helmet>();
                }
                System.out.println("추가할 헬멧 사이즈 / 개수 / 가격");
                String[] helmetInput = sc.nextLine().split("/");
                String size = helmetInput[0];
                int howMany = Integer.parseInt(helmetInput[1]);
                int price = Integer.parseInt(helmetInput[2]);
                for (int i = 0; i < howMany; i++) {
                    helmetList.put(numberGen(4), new Helmet(Integer.parseInt(numberGen(4)), size, price, null));
                }
                fileIo.helmetListWriter("muju", helmetList);
                break;
            }
//            case 2 : {Map<String, Clothes> clothesList = fileIo.clothesListReader("muju");
//                if(clothesList != null && !clothesList.isEmpty()){
//                } else {
//                    fileIo.clothesListWriter("muju",null);
//                    clothesList = new HashMap<String,Clothes>();
//                }
//                System.out.println("추가할 의류 사이즈 / 개수 / 가격");
//                String[] clothesInput = sc.nextLine().split("/");
//                String size = clothesInput[0];
//                int howMany = Integer.parseInt(clothesInput[1]);
//                int price = Integer.parseInt(clothesInput[2]);
//                for(int i = 0; i < howMany; i++) {
//                    clothesList.put(numberGen(4), new Clothes(Integer.parseInt(numberGen(4)),size,price,null));
//                }
//                fileIo.clothesListWriter("muju",clothesList);
//                break;}
            case 3: {
                break;
            }
            case 4: {
                break;
            }
            case 5: {
                break;
            }
            case 6: {
                break;
            }
            default: {
                System.out.println("잘못된 입력");
                break;
            }
        }


    }

    public void deleteProduct() {

    }

    public void getAllReservationRevenue() {

    }

    public void getAllProductRevenue() {

    }

    public static String numberGen(int len) {

        Random rand = new Random();
        String numStr = "";

        for (int i = 0; i < len; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            if (!numStr.contains(ran)) {
                numStr += ran;
            } else {
                i -= 1;
            }
        }
        return numStr;
    }
}