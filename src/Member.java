import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
public class Member extends User implements Serializable {
    private static final long serialVersionUID = 6741273514427688155L; // Room 클래스의 serialVersionUID 사용
    private String phoneNumber;
    private Grade grade;
    private int point;
    private List<Integer> reservationNumberList;

    Member(String name,String phoneNumber, String userId, String password, boolean isAdmin){
        super(name, userId, password, isAdmin);
        this.phoneNumber = phoneNumber;
        this.grade = Grade.BASIC;
        this.point = 0;
        this.reservationNumberList = new LinkedList<Integer>();
    }

    @Override
    public String toString() {
        return "Member{" +
                super.toString() +
                "phoneNumber='" + phoneNumber + '\'' +
                ", grade=" + grade +
                ", point=" + point +
                ", reservationList=" + reservationNumberList +
                '}';
    }

    // point 누적
    public void updatePoints(int additionalPoints) {
        this.point += additionalPoints; // 포인트 업데이트
        updateGrade(); // 등급도 업데이트 처리~!
    }

    // point에 기반한 grade 산정 helper 함수
    private void updateGrade() {
        if (this.point >= 10000000) {
            this.grade = Grade.MVG;
        } else if (this.point >= 5000000) {
            this.grade = Grade.VVIP;
        } else if (this.point >= 1000000) {
            this.grade = Grade.VIP;
        } else {
            this.grade = Grade.BASIC;
        }
    }

    // 할인율 적용
    public double getDiscountRate() {
        switch (this.grade) {
            case MVG:
                return 0.30;
            case VVIP:
                return 0.20;
            case VIP:
                return 0.10;
            default:
                return 0.00;
        }
    }

}
