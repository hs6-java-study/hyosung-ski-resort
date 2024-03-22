import java.util.Scanner;

public class Menu {
    public Scanner sc;
    private ManageMember memberMember;
    private ManageAdmin manageAdmin;

    void run(){
        System.out.println("효성 리조트에 오신것을 환영합니다.");
        System.out.println("1. 회원 / 2. 관리자 / 3.종료");
        sc = new Scanner(System.in);

        int pointer = Integer.parseInt(sc.nextLine());

        switch (pointer){
            case 1:
                memberMember = new ManageMember();
                memberMember.run();
                break;
            case 2:
                manageAdmin = new ManageAdmin();
                manageAdmin.run();
                break;
            default:
                System.out.println("잘못된입력");
        }



    }
}
