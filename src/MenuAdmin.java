import java.util.Scanner;

public class MenuAdmin {

    public Scanner sc;
    public FileIO fileIo;
    private Admin admin;

    MenuAdmin() {
        sc = new Scanner(System.in);
        fileIo = new FileIO();
    }

    public void run(Admin admin) {
        // 관리자 메뉴
        int pointer;
        do {
            System.out.println("===== 관리자 메뉴 =====");
            System.out.println("1. 회원 전체 조회 / 2. 회원 정보 검색 / 3. 회원 탈퇴 처리 / 4. 예약 목록 전체 조회 / 5. 특정 회원 예약내역 조회 / 6. 장비 전체 조회 / 7. 장비 추가 / 8. 장비 삭제 / 9. 장비 대여 매출 조회 / 10. 숙박 매출 조회 / 11. 로그아웃");
            pointer = Integer.parseInt(sc.nextLine());
            switch (pointer) {
                case 1:
                    getAllMemberListInfo();
                    break;
                case 2:
                    getOneMemberInfo();
                    break;
                case 3:
                    deleteOneMember();
                    break;
                case 4:
                    getAllReservationInfo();
                    break;
                case 5:
                    getReservationInfoByOneMember();
                    break;
                case 6:
                    getAllProductInfo();
                    break;
                case 7:
                    addProduct();
                    break;
                case 8:
                    deleteProduct();
                    break;
                case 9:
                    getAllReservationRevenue();
                    break;
                case 10:
                    getAllProductRevenue();
                    break;
                case 11:
                    // TODO: 로그아웃 처리
                    System.out.println(admin.getUserId() + " 관리자님 로그아웃 되었습니다!");
                    break;
                default:
                    System.out.println("잘못된입력");
            }
        } while (pointer != 11);

    }

    /**
     * 회원 전체 조회
     */
    public void getAllMemberListInfo() {

    }

    /**
     * 회원 정보 검색
     */
    public void getOneMemberInfo() {

    }

    /**
     * 회원 탈퇴 처리
     */
    public void deleteOneMember() {

    }

    /**
     * 예약 목록 전체 조회
     */
    public void getAllReservationInfo() {

    }

    /**
     * 특정 회원 예약내역 조회
     */
    public void getReservationInfoByOneMember() {

    }

    /**
     * 장비 전체 조회
     */
    public void getAllProductInfo() {

    }

    /**
     * 장비 추가
     */
    public void addProduct() {

    }

    /**
     * 장비 삭제
     */
    public void deleteProduct() {

    }

    /**
     * 장비 대여 매출 조회
     */
    public void getAllReservationRevenue() {

    }

    /**
     * 숙박 매출 조회
     */
    public void getAllProductRevenue() {

    }
}