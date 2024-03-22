import java.io.*;

public class FileIO {
    final String basePath = "C:\\SkiResort\\";
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    ObjectOutputStream out = null;
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    ObjectInputStream in = null;
    String path;

    private void makeFolder(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            try {
                folder.mkdir();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }else {
            System.out.println("이미 폴더가 존재합니다.");
        }
    }

    public void memberListWriter(Member member){
        try {

            makeFolder(basePath);
            fos = new FileOutputStream(basePath + "memberData.txt",true);
            bos = new BufferedOutputStream(fos);

            out = new ObjectOutputStream(bos);
            Member member2 = new Member("dd","dd","dd",true);
            Member member3 = new Member("aa","aa","aa",true);

            out.writeObject(member2);
            out.writeObject(member3);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                bos.close();
                fos.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void memberListReader(){
        try {
            fis = new FileInputStream(basePath + "memberData.txt");
            bis = new BufferedInputStream(fis);
            in = new ObjectInputStream(bis);

            Object users = null;
            while((users = in.readObject()) != null) {
                System.out.println(((Member)users).toString());
            }

        } catch (FileNotFoundException e) {
            System.out.println("파일이 존재하지 않아요");
        } catch (EOFException e) {
            System.out.println("파일의 끝" + e.getMessage());
        } catch (IOException e) {
            System.out.println("파일이 읽을 수 없어요");
        } catch (ClassNotFoundException e) {
            System.out.println("해당 객체가 존재하지 않아요");
        } catch (Exception e) {
            System.out.println("나머지 예외");
        }finally {
            try {
                in.close();
                bis.close();
                fis.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
