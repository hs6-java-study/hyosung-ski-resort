import lombok.Data;

import java.io.Serializable;

@Data
public class Member extends User implements Serializable {
    private Grade grade;
    private int point;

    Member(String name, String userId, String password, boolean isAdmin){
        super(name, userId, password, isAdmin);
        this.grade = Grade.BASIC;
        this.point = 0;
    }

    @Override
    public String toString() {
        return super.toString() + " grade=" + grade +
                ", point=" + point +
                '}';
    }
}
