import java.util.Scanner;

public class ValidationUtils {

    public String getValidation(Scanner sc, AuthValidation validation) {

        String input;
        while (true) {
            System.out.print(validation.getInputMessage());
            input = sc.nextLine();
            if ("취소".equals(input)) {
                System.out.println(validation.getCancellationMessage());
                return null;
            }
            if (!input.matches(validation.getRegex())) {
                System.out.println(validation.getFailureMessage());
                continue;
            }
            break;
        }
        return input;
    }
}
