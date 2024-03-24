import TestAF.BuildHigh1Room;
import TestAF.BuildMuJuRoom;
import TestAF.BuildRoom;
import TestAF.RoomTest;

import java.util.Map;
import java.util.Scanner;

public class AuthAdmin {
    public Scanner sc;
    public FileIO fileIo;
    private Map<String, Admin> adminList;
    private MenuAdmin menuAdmin;
    private Admin admin;

    AuthAdmin() {
        sc = new Scanner(System.in);
        fileIo = new FileIO();
    }

    public void run() {
        int pointer;
        do {
            admin = null;
            System.out.println("1. 관리자 로그인 / 2. 관리자 회원가입 / 3. 뒤로");
            pointer = Integer.parseInt(sc.nextLine());
            switch (pointer) {
                case 1:
//                    ===========추상 팩토리 Test 실행 코드=============
//                    BuildRoom mjBuild = new BuildMuJuRoom();
//                    BuildRoom H1Build = new BuildHigh1Room();
//
//                    RoomTest room = mjBuild.orderRoom("standard");
//                    System.out.println(room.toString());
//                    RoomTest room2 = mjBuild.orderRoom("deluxe");
//                    System.out.println(room2.toString());
//                    RoomTest room3 = H1Build.orderRoom("standard");
//                    System.out.println(room3.toString());
//                    RoomTest room4 = H1Build.orderRoom("Family");
//                    System.out.println(room4.toString());
                    login();
                    break;
                case 2:
                    signup();
                    break;
                case 3:
                    System.out.println("뒤로이동");
                    break;
                default:
                    System.out.println("잘못된입력");
            }
        } while (pointer != 3);
    }

    public void signup() {
        adminList = fileIo.adminListReader(); // 기존 관리자목록 불러오기

        String userId;
        do {
            System.out.println("\n===== 효성리조트 관리자 회원가입 =====");
            System.out.println("회원가입을 취소하시려면 \"취소\"를 입력해주세요.\n");

            userId = getValidation("ID 입력 : ", "^[a-z]{1}[a-z0-9]{5,10}+$", "ID 정규식 미통과", "회원가입이 취소되었습니다.");
            if (userId == null) return;

            // 동일 키값이 있는 경우 들어가면 X
            if (adminList.containsKey(userId)) {
                System.out.println("이미 존재하는 관리자 계정입니다. 다시 입력해주세요.");
                userId = null;
            }
        } while (userId == null);

        String password = getValidation("비밀번호 입력 : ", "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=~!]).{8,14}$", "PW 정규식 미통과", "회원가입이 취소되었습니다.");
        if(password == null) return;

        String name = getValidation("이름 : ", "^[a-zA-Z가-힣]+$", "이름 정규식 미통과", "회원가입이 취소되었습니다.");
        if(name == null) return;

        admin = new Admin(name, userId, password, true);
        adminList.put(userId, admin);
        fileIo.adminListWriter(adminList);
        System.out.println("안녕하세요! " + userId + "님 효성 리조트 관리자 계정 가입이 완료되었습니다.\n");

        // 확인용 출력
        adminList.entrySet().stream()
                .forEach(entry -> System.out.println("관리자 ID : " + entry.getKey() + "  | admin 정보 : " + entry.getValue()));
    }

    public void login() {
        adminList = fileIo.adminListReader(); // 기존 관리자목록 불러오기
        String userId = null;
        String password = null;

        System.out.println("\n===== 관리자 로그인 하기 =====");
        System.out.println("뒤로가려면 \"취소\"를 입력해주세요.\n");

        while (true) {
            System.out.print("ID 입력 : ");
            userId = sc.nextLine();
            if(userId.equals("취소")) return;

            System.out.print("PW 입력 : ");
            password = sc.nextLine();
            if (password.equals("취소")) return;

            if (adminList.containsKey(userId) && adminList.get(userId).getPassword().equals(password)) {
                admin = adminList.get(userId);
                break;
            } else {
                System.out.println("아이디 또는 비밀번호를 잘못 입력했습니다.\n입력하신 내용을 다시 확인해주세요.");
            }
        }

        // 확인용 출력
        System.out.println("전체 관리자 정보 (확인용)");
        adminList.entrySet().stream()
                .forEach(entry -> System.out.println("관리자 ID : " + entry.getKey() + " | admin 정보 : " + entry.getValue()));

        // 현재 로그인한 관리자 정보 출력(확인용)
        System.out.println("현재 로그인한 관리자 정보 출력 : " + admin.toString());

        menuAdmin = new MenuAdmin();
        menuAdmin.run(admin);
    }

    /**
     * 유효성 검사
     */
    private String getValidation(String inputMessage, String regex, String failMessage, String exitMessage) {
        String input;
        while (true) {
            System.out.print(inputMessage);
            input = sc.nextLine();
            if ("취소".equals(input)) {
                System.out.println(exitMessage);
                return null;
            }
            if (!input.matches(regex)) {
                System.out.println(failMessage);
                continue;
            }
            break;
        }
        return input;
    }
}

