import java.util.Scanner;

public class ValidationUtils {

    public String getValidation(Scanner sc, AuthValidation validation) {

        String input;
        while (true) {
            System.out.print(AnsiColor.yellow("\t\t➤ "+validation.getInputMessage()));
            input = sc.nextLine();
            if ("x".equalsIgnoreCase(input) || input.trim().replace(" ", "").equals("")) {
                System.out.println(AnsiColor.red("\t\t➤ "+validation.getCancellationMessage()));
                return null;
            }
            if (!input.matches(validation.getRegex())) {
                System.out.println(AnsiColor.red("\t\t➤ "+validation.getFailureMessage()));
                continue;
            }
            break;
        }
        return input;
    }
}
