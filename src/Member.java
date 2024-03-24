import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Member extends User implements Serializable {
    private String phoneNumber;
    private Grade grade;
    private int point;
    private List<Integer> reservationList;

    Member(String name,String phoneNumber, String userId, String password, boolean isAdmin){
        super(name, userId, password, isAdmin);
        this.phoneNumber = phoneNumber;
        this.grade = Grade.BASIC;
        this.point = 0;
        this.reservationList = new ArrayList<Integer>();
    }

    @Override
    public String toString() {
        return "Member{" +
                super.toString() +
                "phoneNumber='" + phoneNumber + '\'' +
                ", grade=" + grade +
                ", point=" + point +
                ", reservationList=" + reservationList +
                '}';
    }
}
