import java.io.*;
import java.util.*;

public class FileIO {
    final String basePath = "ResortData\\";
//    final String basePath = "/Users/som/SkiResort/";
    private Map<String, Boolean> regions;
    private String path;

    FileIO() {
        this.regions = new HashMap<String, Boolean>() {{
            put("muju", true);
            put("gangchon", true);
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

    public boolean inRegion(String region) {
        if (!regions.containsKey(region)) {
            System.out.println("지점이 존재하지 않습니다.");
            return false;
        }
        return true;
    }

    public void memberListWriter(Map<String, Member> memberList) {
        makeFolder(basePath);
        try (
                FileOutputStream fos = new FileOutputStream(basePath + "memberData.txt");
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream out = new ObjectOutputStream(bos);
        ) {
            out.writeObject(memberList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Member> memberListReader() {
        Map<String, Member> memberList = null;
        try (
                FileInputStream fis = new FileInputStream(basePath + "memberData.txt");
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream in = new ObjectInputStream(bis);
        ) {
            memberList = (HashMap) in.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("파일이 존재하지 않습니다.");
            return new HashMap<String, Member>();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return memberList;
    }

    public void adminListWriter(Map<String, Admin> adminList) {
        makeFolder(basePath);
        try (
                FileOutputStream fos = new FileOutputStream(basePath + "adminData.txt");
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream out = new ObjectOutputStream(bos);
        ) {
            out.writeObject(adminList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Admin> adminListReader() {
        Map<String, Admin> adminList = null;
        try (
                FileInputStream fis = new FileInputStream(basePath + "adminData.txt");
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream in = new ObjectInputStream(bis);
        ) {
            adminList = (HashMap) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("파일이 존재하지 않습니다.");
            return new HashMap<String, Admin>();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adminList;
    }

    public void roomListWriter(String region, Map<Integer, Room> roomList) {
        String path = basePath + region + "\\";
        makeFolder(path);
        // try-with-resources 구문 사용
        try (
                FileOutputStream fos = new FileOutputStream(path + "roomData.txt");
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(roomList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Room> roomListReader(String region) {
        path = basePath + region + "\\";
        Map<Integer, Room> roomList = null;
        try (
                FileInputStream fis = new FileInputStream(path + "roomData.txt");
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream in = new ObjectInputStream(bis);
        ) {
            roomList = (HashMap) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("파일이 존재하지 않습니다.");
            return new HashMap<Integer, Room>();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roomList;
    }

    public void reservationListWriter(String region, Map<Integer, Reservation> reservationList) {
        path = basePath + region + "\\";
        makeFolder(path);
        try (
                FileOutputStream fos = new FileOutputStream(path + "reservationData.txt");
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream out = new ObjectOutputStream(bos);
        ) {
            out.writeObject(reservationList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Reservation> reservationListReader(String region) {
        path = basePath + region + "\\";
        Map<Integer, Reservation> reservationList = null;
        try (
                FileInputStream fis = new FileInputStream(path + "reservationData.txt");
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream in = new ObjectInputStream(bis);
        ) {
            reservationList = (HashMap) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("파일이 존재하지 않습니다.");
            return new HashMap<Integer, Reservation>();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservationList;
    }


    public void productListWriter(String productName, String region, Map product) {
        path = basePath + region + "\\";
        makeFolder(path);
        try (
                FileOutputStream fos = new FileOutputStream(path + productName + ".txt");
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream out = new ObjectOutputStream(bos);
        ) {
            out.writeObject(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> Map productListReader(String productName, String region) {
        Map<Integer, T> productList = null;
        path = basePath + region + "\\";
        try (
                FileInputStream fis = new FileInputStream(path + productName + ".txt");
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream in = new ObjectInputStream(bis);
        ) {
            productList = (HashMap) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("파일이 존재하지 않습니다.");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }


    /**
     * 장비 목록 전체 조회(지점까지)
     */
    public Map<String, Product> AllProductListReader() {
        path = basePath;
        Map<String, Product> productList = new HashMap<String, Product>();

        File file = new File(path);
        String[] files = null;

        try {
            if (file.exists() && file.isDirectory()) {
                files = file.list();
                for (String eachFile : files) {
                    getAllProduct(eachFile, productList, "muju");
                    getAllProduct(eachFile, productList, "gangchon");
                }
            }
            return productList;
        } catch (FileNotFoundException e) {
            System.out.println("파일이 존재하지 않습니다.");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 장비 전체 조회 helper 함수
     */
    private void getAllProduct(String eachFile, Map<String, Product> productList, String region) throws IOException, ClassNotFoundException {
        String[] productFileNames = {"Helmet.txt", "Clothes.txt", "Equipment.txt"};
        if (eachFile.contains(region)) {
            for (String productFileName : productFileNames) {
                File temp = new File(basePath + region + "/" + productFileName);
                if (temp.exists()) {
                    try (FileInputStream fis = new FileInputStream(temp);
                         BufferedInputStream bis = new BufferedInputStream(fis);
                         ObjectInputStream in = new ObjectInputStream(bis)) {

                        String productType = productFileName.contains("Helmet") ? "헬멧" : productFileName.contains("Clothes") ? "의류" : "장비";
                        Map<String, Product> tempProductList = (HashMap) in.readObject();
                        for (Map.Entry<String, Product> entry : tempProductList.entrySet()) {
                            // 지점명을 포함하는 새 키 생성
                            String newKey = "[" + region + " 지점 | " + productType + "]  물품 고유번호 : " + entry.getKey();
                            // 지점명이 포함된 키와 제품을 productList에 추가
                            productList.put(newKey, entry.getValue());
                        }
                    }
                }
            }
        }
    }
}
