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
        int pointer;
        do {
            member = null;
            System.out.println("1. 회원 로그인 / 2. 회원 회원가입 / 3. 뒤로");
            pointer = Integer.parseInt(sc.nextLine());
            switch (pointer) {
                case 1:
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
        memberList = fileIo.memberListReader(); // 기존 회원목록 불러오기

        String userId;
        do {
            System.out.println("\n===== 효성리조트 회원가입 =====");
            System.out.println("회원가입을 취소하시려면 \"취소\"를 입력해주세요.\n");

            userId = getValidation("ID : ", "^[a-z]{1}[a-z0-9]{5,10}+$", "ID 정규식 미통과", "회원가입이 취소되었습니다.");
            if (userId == null) return;

            // 동일 키값이 있는 경우 들어가면 X
            if (memberList.containsKey(userId)) {
                System.out.println("이미 존재하는 계정입니다. 다시 입력해주세요.");
                userId = null;
            }
        } while (userId == null);

        String password = getValidation("비밀번호 : ", "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=~!]).{8,14}$", "PW 정규식 미통과", "회원가입이 취소되었습니다.");
        if (password == null) return;

        String name = getValidation("이름 : ", "^[a-zA-Z가-힣]+$", "이름 정규식 미통과", "회원가입이 취소되었습니다.");
        if (name == null) return;

        String phoneNumber = getValidation("전화번호 : ", "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", "전화번호 정규식 미통과", "회원가입이 취소되었습니다.");
        if (phoneNumber == null) return;

        member = new Member(name, phoneNumber, userId, password, false);
        memberList.put(userId, member);
        fileIo.memberListWriter(memberList);
        System.out.println("안녕하세요! " + name + "님 효성 리조트 회원 가입이 완료되었습니다.\n");

        // 확인용 출력
        memberList.entrySet().stream()
                .forEach(entry -> System.out.println("회원 ID : " + entry.getKey() + " | member 정보 : " + entry.getValue()));
    }

    public void login() {
        memberList = fileIo.memberListReader(); // 기존 회원목록 불러오기
        String userId = null;
        String password = null;

        System.out.println("\n===== 로그인 하기 =====");
        System.out.println("뒤로가려면 \"취소\"를 입력해주세요.\n");

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

        // 확인용 출력(전체정보 - 확인용)
        System.out.println("전체 회원 정보 (확인용)");
        memberList.entrySet().stream()
                .forEach(entry -> System.out.println("회원 ID : " + entry.getKey() + " | member 정보 : " + entry.getValue()));

        // 현재 로그인한 회원 정보 출력(확인용)
        System.out.println("현재 로그인한 회원 정보 출력 : " + member.toString());

        menuMember = new MenuMember();
        menuMember.run(member);
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