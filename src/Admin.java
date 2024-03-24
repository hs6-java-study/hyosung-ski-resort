import lombok.Data;

import java.io.Serializable;

@Data
public class Admin extends User implements Serializable {

    Admin(String name, String userId, String password, boolean isAdmin){
        super(name, userId, password, isAdmin);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
