public enum AnsiColor {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    BLACK_BACKGROUND("\u001B[40m"),
    RED_BACKGROUND("\u001B[41m"),
    GREEN_BACKGROUND("\u001B[42m"),
    YELLOW_BACKGROUND("\u001B[43m"),
    BLUE_BACKGROUND("\u001B[44m"),
    PURPLE_BACKGROUND("\u001B[45m"),
    CYAN_BACKGROUND("\u001B[46m"),
    WHITE_BACKGROUND("\u001B[47m");

    private final String code;

    AnsiColor(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    // 색상을 적용하는 메서드
    public static String black(String content) {
        return AnsiColor.BLACK.getCode() + content + AnsiColor.RESET.getCode();
    }

    public static String red(String content) {
        return AnsiColor.RED.getCode() + content + AnsiColor.RESET.getCode();
    }

    public static String green(String content) {
        return AnsiColor.GREEN.getCode() + content + AnsiColor.RESET.getCode();
    }

    public static String yellow(String content) {
        return AnsiColor.YELLOW.getCode() + content + AnsiColor.RESET.getCode();
    }

    public static String blue(String content) {
        return AnsiColor.BLUE.getCode() + content + AnsiColor.RESET.getCode();
    }

    public static String purple(String content) {
        return AnsiColor.PURPLE.getCode() + content + AnsiColor.RESET.getCode();
    }

    public static String cyan(String content) {
        return AnsiColor.CYAN.getCode() + content + AnsiColor.RESET.getCode();
    }

    public static String white(String content) {
        return AnsiColor.WHITE.getCode() + content + AnsiColor.RESET.getCode();
    }

    // 배경 색상을 적용하는 메서드
    public static String blackBackground(String content) {
        return AnsiColor.BLACK_BACKGROUND.getCode() + content + AnsiColor.RESET.getCode();
    }

    public static String redBackground(String content) {
        return AnsiColor.RED_BACKGROUND.getCode() + content + AnsiColor.RESET.getCode();
    }

    public static String greenBackground(String content) {
        return AnsiColor.GREEN_BACKGROUND.getCode() + content + AnsiColor.RESET.getCode();
    }

    public static String yellowBackground(String content) {
        return AnsiColor.YELLOW_BACKGROUND.getCode() + content + AnsiColor.RESET.getCode();
    }

    public static String blueBackground(String content) {
        return AnsiColor.BLUE_BACKGROUND.getCode() + content + AnsiColor.RESET.getCode();
    }

    public static String purpleBackground(String content) {
        return AnsiColor.PURPLE_BACKGROUND.getCode() + content + AnsiColor.RESET.getCode();
    }

    public static String cyanBackground(String content) {
        return AnsiColor.CYAN_BACKGROUND.getCode() + content + AnsiColor.RESET.getCode();
    }

    public static String whiteBackground(String content) {
        return AnsiColor.WHITE_BACKGROUND.getCode() + content + AnsiColor.RESET.getCode();
    }
}