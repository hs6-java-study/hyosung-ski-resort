import java.io.*;
import java.util.HashMap;
import java.util.Map;

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
        }
    }

    public void memberListWriter(Map<String, Member> memberList){
        try {
            makeFolder(basePath);
            fos = new FileOutputStream(basePath + "memberData.txt");
            bos = new BufferedOutputStream(fos);
            out = new ObjectOutputStream(bos);
            out.writeObject(memberList);
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

    public Map memberListReader(){
        Map<String, Member> memberList = null;
        try {
            fis = new FileInputStream(basePath + "memberData.txt");
            bis = new BufferedInputStream(fis);
            in = new ObjectInputStream(bis);
            memberList = (HashMap) in.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("파일이 존재하지 않아서 생성");
            return new HashMap<String,Member>();
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
        return memberList;
    }

    public void adminListWriter(Map<String, Admin> adminList){
        try {
            makeFolder(basePath);
            fos = new FileOutputStream(basePath + "adminData.txt");
            bos = new BufferedOutputStream(fos);
            out = new ObjectOutputStream(bos);
            out.writeObject(adminList);
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

    public Map adminListReader(){
        Map<String, Admin> adminList = null;
        try {
            fis = new FileInputStream(basePath + "adminData.txt");
            bis = new BufferedInputStream(fis);
            in = new ObjectInputStream(bis);
            adminList = (HashMap) in.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("파일이 존재하지 않아서 생성");
            return new HashMap<String,Admin>();
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
        return adminList;
    }
}
