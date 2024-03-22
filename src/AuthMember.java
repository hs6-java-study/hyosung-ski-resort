import java.util.Map;
import java.util.Scanner;

public class AuthMember {
    private Map<String, Member> memberList;
    public Scanner sc;
    private Member member;
    private MenuMember managereservation;
    public FileIO fileIo = new FileIO();


    public void run() {
        System.out.println("1. 로그인 / 2. 회원가입 / 3. 뒤로");
        sc = new Scanner(System.in);
        int pointer = Integer.parseInt(sc.nextLine());

        switch (pointer) {
            case 1:
                login();
                break;
            case 2:
                fileIo.memberListWriter(member);
                break;
            default:
                System.out.println("잘못된입력");
        }

    }

    public void singup() {

    }

    public void login() {
        fileIo.memberListReader();
        managereservation = new MenuMember();
        managereservation.run(member);
    }
}
