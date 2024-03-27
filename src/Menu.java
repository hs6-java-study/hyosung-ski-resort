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
        System.out.println("\t██╗  ██╗███████╗    ██████╗ ███████╗███████╗ ██████╗ ██████╗ ████████╗\n" +
                "\t██║  ██║██╔════╝    ██╔══██╗██╔════╝██╔════╝██╔═══██╗██╔══██╗╚══██╔══╝\n" +
                "\t███████║███████╗    ██████╔╝█████╗  ███████╗██║   ██║██████╔╝   ██║   \n" +
                "\t██╔══██║╚════██║    ██╔══██╗██╔══╝  ╚════██║██║   ██║██╔══██╗   ██║   \n" +
                "\t██║  ██║███████║    ██║  ██║███████╗███████║╚██████╔╝██║  ██║   ██║   \n" +
                "\t╚═╝  ╚═╝╚══════╝    ╚═╝  ╚═╝╚══════╝╚══════╝ ╚═════╝ ╚═╝  ╚═╝   ╚═╝   ");
        System.out.println(AnsiColor.line("\t\t\t\t\t\t< 효성 리조트에 오신것을 환영합니다 >"));
        do{
            System.out.println("\n\n\t+———————————————————————————————————————————————————————————————————+");
            System.out.println("\t\t\t\t\t\t\t\t1. 회원 접속   " );
            System.out.println("\t\t\t\t\t\t\t\t2. 관리자 접속  " );
            System.out.println("\t\t\t\t\t\t\t\t3. 서비스 종료" );
            System.out.println("\t+———————————————————————————————————————————————————————————————————+");
            System.out.print("\t\t\t\t\t\t\t\t➤ 입력 : ");
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
                    System.out.println(AnsiColor.line("\t\t\t\t\t\t\t\t< 서비스 종료 >"));
                    break;
                default:
                    System.out.println(AnsiColor.red("\t\t\t\t\t\t\t\t잘못된 입력입니다."));
            }
        }while(pointer != 3);
    }
}
