import java.util.Scanner;

public class Menu {
    public Scanner sc;
    private AuthMember memberMember;
    private AuthAdmin manageAdmin;
    private int pointer;

    Menu(){
        sc = new Scanner(System.in);
    }
    void run(){
        System.out.println("효성 리조트에 오신것을 환영합니다.");
        do{
            System.out.println("1. 회원 / 2. 관리자 / 3.종료");
            pointer = Integer.parseInt(sc.nextLine());
            switch (pointer){
                case 1:
                    memberMember = new AuthMember();
                    memberMember.run();
                    break;
                case 2:
                    manageAdmin = new AuthAdmin();
                    manageAdmin.run();
                    break;
                case 3:
                    System.out.println("서비스 종료");
                    break;
                default:
                    System.out.println("잘못된입력");
            }
        }while(pointer != 3);
    }
}
