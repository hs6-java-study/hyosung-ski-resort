import java.util.Map;
import java.util.Scanner;

public class AuthAdmin {
    private Map<String, Admin> adminList;
    public Scanner sc;
    private Admin admin;
    private MenuAdmin menuadmin;

    public void run(){
        System.out.println("1. 로그인 / 2. 회원가입 / 3. 뒤로");
        sc = new Scanner(System.in);
        int pointer = Integer.parseInt(sc.nextLine());

        switch (pointer){
            case 1:
                login();
                break;
            case 2:
                break;
            default:
                System.out.println("잘못된입력");
        }
    }

    public void singup(){

    }

    public void login(){
        menuadmin = new MenuAdmin();
        menuadmin.run();

    }
}
