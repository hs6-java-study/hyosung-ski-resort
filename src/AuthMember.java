import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AuthMember {
    public Scanner sc;
    public FileIO fileIo;
    private Map<String, Member> memberList;
    private MenuMember menuMember;
    private Member member;

    AuthMember() {
        sc = new Scanner(System.in);
        fileIo = new FileIO();
    }

    public void run() {
        String pointer = null;
        do {
            member = null;
            System.out.println("\n");
            System.out.println(AnsiColor.line("\t\t\t\t\t\t\t\t< 회원 접속 >"));
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            System.out.println("\t\t\t\t\t\t\t\t1. 회원 로그인   " );
            System.out.println("\t\t\t\t\t\t\t\t2. 회원 회원가입  " );
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
                    System.out.println("뒤로이동");
                    break;
                default:
                    System.out.println("잘못된입력");
            }
        } while (!pointer.equals("3"));

    }

    public void signup() {
        memberList = fileIo.memberListReader(); // 기존 회원목록 불러오기
        ValidationUtils validationUtils = new ValidationUtils(); // 유효성 검사 클래스 분리

        String userId;
        do {
            System.out.println("\n===== 효성리조트 회원가입 =====");
            System.out.println("회원가입을 취소하시려면 \"취소\"를 입력해주세요.\n");

            userId = validationUtils.getValidation(sc, AuthValidation.USER_ID);
            if (userId == null) return;

            // 동일 키값이 있는 경우 들어가면 X
            if (memberList.containsKey(userId)) {
                System.out.println("이미 존재하는 계정입니다. 다시 입력해주세요.");
                userId = null;
            }
        } while (userId == null);

        String password = validationUtils.getValidation(sc, AuthValidation.PASSWORD);
        if (password == null) return;

        String name = validationUtils.getValidation(sc, AuthValidation.NAME);
        if (name == null) return;

        String phoneNumber = validationUtils.getValidation(sc, AuthValidation.PHONE_NUMBER);
        if (phoneNumber == null) return;

        member = new Member(name, phoneNumber, userId, password, false);
        memberList.put(userId, member);
        fileIo.memberListWriter(memberList);
        System.out.println("안녕하세요! " + name + "님 효성 리조트 회원 가입이 완료되었습니다.\n");

        // 확인용 출력
        memberList.entrySet().stream()
                .forEach(entry -> System.out.println("[확인용 출력] 회원 ID : " + entry.getKey() + " | member 정보 : " + entry.getValue()));
    }

    public void login() {
        memberList = fileIo.memberListReader(); // 기존 회원목록 불러오기
        String userId = null;
        String password = null;

        System.out.println();
        System.out.println("\t\t\t\t\t\t\t\t< 회원 로그인 >");
        System.out.println(AnsiColor.yellow("\t\t\t\t\t\t-뒤로가려면 \"X\"를 입력해주세요-\n"));

        while (true) {
            System.out.print("ID 입력 : ");
            userId = sc.nextLine();
            if (userId.equals("취소")) return;

            System.out.print("PW 입력 : ");
            password = sc.nextLine();
            if (password.equals("취소")) return;

            if (memberList.containsKey(userId) && memberList.get(userId).getPassword().equals(password)) {
                member = memberList.get(userId);
                break;
            } else {
                System.out.println("아이디 또는 비밀번호를 잘못 입력했습니다.\n입력하신 내용을 다시 확인해주세요.");
            }
        }

        // 현재 로그인한 회원 정보 출력(확인용)
        System.out.println("현재 로그인한 회원 정보 출력 : " + member.toString());

        menuMember = new MenuMember();
        menuMember.run(member);
    }

}