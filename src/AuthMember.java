import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AuthMember {
    public Scanner sc ;
    public FileIO fileIo;
    private Map<String, Member> memberList;
    private MenuMember menuMember;
    private Member member;
    private int pointer;

    AuthMember(){
        sc = new Scanner(System.in);
        fileIo = new FileIO();
    }

    public void run() {
        do{
            System.out.println("1. 회원 로그인 / 2. 회원 회원가입 / 3. 뒤로");
            pointer = Integer.parseInt(sc.nextLine());
            switch (pointer) {
                case 1:
                    login();
                    break;
                case 2:
                    singup();
                    break;
                case 3:
                    System.out.println("뒤로이동");
                    break;
                default:
                    System.out.println("잘못된입력");
            }
        }while(pointer != 3);

    }

    public void singup() {
        System.out.print("ID입력 : ");
        String ID = sc.nextLine();
        member = new Member("asdf","45","gusehd502","456",true);
        memberList = fileIo.memberListReader();
        memberList.put(ID, member);
        fileIo.memberListWriter(memberList);
    }

    public void login() {
        memberList = fileIo.memberListReader();
        for (Map.Entry mem : memberList.entrySet()){
            System.out.println("회원ID : " + mem.getKey() + " / " + "회원클래스 : " + mem.getValue());
        }
        menuMember = new MenuMember();
        menuMember.run(member);
    }
}
