import java.util.Map;
import java.util.Scanner;

public class AuthAdmin {
    public Scanner sc;
    public FileIO fileIo ;
    private Map<String, Admin> adminList;
    private MenuAdmin menuAdmin;
    private Admin admin;
    private int pointer;

    AuthAdmin(){
        sc = new Scanner(System.in);
        fileIo = new FileIO();
    }

    public void run(){
        do{
            System.out.println("1. 관리자 로그인 / 2. 관리자 회원가입 / 3. 뒤로");
            pointer = Integer.parseInt(sc.nextLine());
            switch (pointer){
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

    public void singup(){
        System.out.print("ID입력 : ");
        String ID = sc.nextLine();
        admin = new Admin("asdf","45","gusehd502",true);
        adminList = fileIo.adminListReader();
        adminList.put(ID, admin);
        fileIo.adminListWriter(adminList);
    }

    public void login(){
        adminList = fileIo.adminListReader();
        for (Map.Entry ad : adminList.entrySet()){
            System.out.println("관리자ID : " + ad.getKey() + " / " + "관리자클래스 : " + ad.getValue());
        }
        menuAdmin = new MenuAdmin();
        menuAdmin.run();
    }
}
