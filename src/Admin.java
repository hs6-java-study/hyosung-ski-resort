import lombok.Data;

@Data
public class Admin extends User{

    Admin(String name, String userId, String password, boolean isAdmin){
        super(name, userId, password, isAdmin);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
