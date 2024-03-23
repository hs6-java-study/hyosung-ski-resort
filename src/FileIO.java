import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileIO {
    final String basePath = "ResortData\\";
    FileOutputStream fos ;
    BufferedOutputStream bos ;
    ObjectOutputStream out ;
    FileInputStream fis ;
    BufferedInputStream bis ;
    ObjectInputStream in ;
    Map<String, Boolean> regions ;
    String path;

    FileIO(){
        fos = null;
        bos = null;
        out = null;
        fis = null;
        bis = null;
        in = null;
        this.regions = new HashMap<String, Boolean>() {{
            put("muju", true);
            put("high1", true);
        }};
    }

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

    public boolean inRegion(String region){
        if (!regions.containsKey(region)) {
            System.out.println("지점이 존재하지 않습니다.");
            return false;
        }
        return true;
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
        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (Exception e) {
            e.printStackTrace();
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

    public void roomListWriter(String region, Map<String, Room> roomList){
        try {
            path = basePath + region + "\\";
            makeFolder(path);
            fos = new FileOutputStream(path + "roomData.txt");
            bos = new BufferedOutputStream(fos);
            out = new ObjectOutputStream(bos);
            out.writeObject(roomList);
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

    public Map roomListReader(String region){
        path = basePath + region + "\\";
        Map<Integer, Room> roomList = null;
        try {
            fis = new FileInputStream(path + "roomData.txt");
            bis = new BufferedInputStream(fis);
            in = new ObjectInputStream(bis);
            roomList = (HashMap) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("파일이 존재하지 않아서 생성");
            return new HashMap<String, Room>();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
                bis.close();
                fis.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return roomList;
    }
}
