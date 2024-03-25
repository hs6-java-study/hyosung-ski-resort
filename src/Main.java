import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Date now = new Date();
        Map<Date, Boolean> hashMap = new HashMap<Date, Boolean>();
        hashMap.put(now, true);

        Menu menu = new Menu();
        menu.run();
    }
}