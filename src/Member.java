import lombok.Data;

import java.io.Serializable;

@Data
public class Member extends User implements Serializable {
    private String phoneNumber;
    private Grade grade;
    private int point;

    Member(String name,String phoneNumber, String userId, String password, boolean isAdmin){
        super(name, userId, password, isAdmin);
        this.phoneNumber = phoneNumber;
        this.grade = Grade.BASIC;
        this.point = 0;
    }

    @Override
    public String toString() {
        return super.toString() +
                "phoneNumber" + phoneNumber +
                " grade=" + grade +
                ", point=" + point +
                '}';
    }
}
