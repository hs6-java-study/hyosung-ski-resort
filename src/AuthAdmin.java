

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
        String pointer = null;
        do {
            admin = null;
            System.out.println(AnsiColor.line("\n\n\t\t\t\t\t\t\t\t< 관리자 접속 >"));
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            System.out.println("\t\t\t\t\t\t\t\t1. 관리자 로그인   " );
            System.out.println("\t\t\t\t\t\t\t\t2. 관리자 회원가입  " );
            System.out.println("\t\t\t\t\t\t\t\t3. 뒤로 이동" );
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
            pointer = sc.nextLine();
            switch (pointer) {
                case "1":
                    login();
                    break;
                case "2":
                    signup();
                    break;
                case "3":
                    break;
                default:
                    System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t잘못된 입력입니다."));
            }
        } while (!pointer.equals("3"));
    }

    public void signup() {
        adminList = fileIo.adminListReader(); // 기존 관리자목록 불러오기
        ValidationUtils validationUtils = new ValidationUtils(); // 유효성 검사 클래스 분리

        String userId;
        do {
            System.out.println("\n\n\t+———————————————————————————————————————————————————————————————————+");
            System.out.println("\t\t\t\t\t\t\t\t효성 리조트 관리자 회원 가입   " );
            System.out.println(AnsiColor.yellow("\t\t\t\t\t\t\t-뒤로가려면 \"X\"를 입력해주세요-"));
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");

            userId = validationUtils.getValidation(sc, AuthValidation.USER_ID);
            if (userId == null) return;

            // 동일 키값이 있는 경우 들어가면 X
            if (adminList.containsKey(userId)) {
                System.out.println(AnsiColor.red("\t\t이미 존재하는 관리자 계정입니다. 다시 입력해주세요."));
                userId = null;
            }
        } while (userId == null);

        String password = validationUtils.getValidation(sc, AuthValidation.PASSWORD);
        if (password == null) return;

        String name = validationUtils.getValidation(sc, AuthValidation.NAME);
        if (name == null) return;

        admin = new Admin(name, userId, password, true);
        adminList.put(userId, admin);
        fileIo.adminListWriter(adminList);
        System.out.println(AnsiColor.green("\n\t\t\t\t안녕하세요! " + name + "님 효성 리조트 관리자 계정 가입이 완료되었습니다.\n"));

        // 확인용 출력
        adminList.entrySet().stream()
                .forEach(entry -> System.out.println("[확인용 출력] 관리자 ID : " + entry.getKey() + "  | admin 정보 : " + entry.getValue()));
    }

    public void login() {
        adminList = fileIo.adminListReader(); // 기존 관리자목록 불러오기
        String userId = null;
        String password = null;

        System.out.println("\n\n\t+———————————————————————————————————————————————————————————————————+");
        System.out.println("\t\t\t\t\t\t\t\t효성 리조트 관리자 로그인   " );
        System.out.println(AnsiColor.yellow("\t\t\t\t\t\t\t-뒤로가려면 \"X\"를 입력해주세요-"));
        System.out.println("\t+———————————————————————————————————————————————————————————————————+");
        System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");

        while (true) {
            System.out.print("\t\t\t\t\t\t\t\t➤ ID 입력 : ");
            userId = sc.nextLine();
            if ("x".equalsIgnoreCase(userId)) return;

            System.out.print("\t\t\t\t\t\t\t\t➤ PW 입력 : ");
            password = sc.nextLine();
            if ("x".equalsIgnoreCase(password)) return;

            if (adminList.containsKey(userId) && adminList.get(userId).getPassword().equals(password)) {
                admin = adminList.get(userId);
                break;
            } else {
                System.out.println(AnsiColor.red("\t\t\t\t\t\t\t아이디 또는 비밀번호를 잘못 입력했습니다.\n\t\t\t\t\t\t\t입력하신 내용을 다시 확인해주세요."));
            }
        }

        // 현재 로그인한 관리자 정보 출력(확인용)
        System.out.println("현재 로그인한 관리자 정보 출력 : " + admin.toString());

        menuAdmin = new MenuAdmin();
        menuAdmin.run(admin);
    }
}

