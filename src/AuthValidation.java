public enum AuthValidation {
    USER_ID("ID 입력 (소문자 알파벳, 숫자 조합 6~10자리) : ", "^[a-z][a-z0-9]{5,10}$", "ID 형식에 맞게 입력해주세요.", "회원가입이 취소되었습니다."),
    PASSWORD("비밀번호 입력 (대소문자 알파벳, 숫자, 특수문자 조합 8~14자리) : ", "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=~!]).{8,14}$", "비밀번호 형식에 맞게 입력해주세요.", "회원가입이 취소되었습니다."),
    NAME("이름 (한글 문자) : ", "^[가-힣]*$", "이름 형식에 맞게 입력해주세요.", "회원가입이 취소되었습니다."),
    PHONE_NUMBER("전화번호 (휴대전화 번호 형식에 맞는 번호) : ", "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", "전화번호 형식에 맞게 입력해주세요.", "회원가입이 취소되었습니다."),
    DATE("날짜 입력 (yyyy.MM.dd 형식) : ", "\\d{4}\\.\\d{2}\\.\\d{2}", "날짜 형식에 맞게 입력해주세요.", "날짜 입력이 취소되었습니다.");

    private final String inputMessage;
    private final String regex;
    private final String failureMessage;
    private final String cancellationMessage;

    AuthValidation(String inputMessage, String regex, String failureMessage, String cancellationMessage) {
        this.inputMessage = inputMessage;
        this.regex = regex;
        this.failureMessage = failureMessage;
        this.cancellationMessage = cancellationMessage;
    }

    public String getInputMessage() {
        return inputMessage;
    }

    public String getRegex() {
        return regex;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public String getCancellationMessage() {
        return cancellationMessage;
    }
}
