import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ManageMember {
    private Map<String, Member>memberList;
    public Scanner sc;
    private Member member;
    public FileIO fileIo = new FileIO();

    public void run(){
        System.out.println("1. 로그인 / 2. 회원가입 / 3. 뒤로");
        sc = new Scanner(System.in);
        int pointer = Integer.parseInt(sc.nextLine());

        switch (pointer){

            case 1:
                fileIo.memberListReader();
                break;
            case 2:
                member = new Member("asdf","asdf","Asdf",true);
//                System.out.print("이름 : ");
//                String name = sc.nextLine();
//                System.out.print("아이디 : ");
//                String userId = sc.nextLine();
//                System.out.println("축하합니다. 회원가입 완료");
                fileIo.memberListWriter(member);
                break;
            default:
                System.out.println("잘못된입력");
        }

    }

    public void singup(){

    }

    public void login(){

    }
}
