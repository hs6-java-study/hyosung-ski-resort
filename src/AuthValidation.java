public enum AuthValidation {
    USER_ID("ID 입력 (소문자 알파벳, 숫자 조합 6~10자리) : ", "^[a-z][a-z0-9]{5,10}$", "ID 형식에 맞게 입력해주세요.", "회원가입이 취소되었습니다."),
    PASSWORD("비밀번호 입력 (대소문자 알파벳, 숫자, 특수문자 조합 8~14자리) : ", "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=~!]).{8,14}$", "비밀번호 형식에 맞게 입력해주세요.", "회원가입이 취소되었습니다."),
    NAME("이름 (한글 문자) : ", "^[가-힣]*$", "이름 형식에 맞게 입력해주세요.", "회원가입이 취소되었습니다."),
    PHONE_NUMBER("전화번호 (01*-****-****) : ", "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", "전화번호 형식에 맞게 입력해주세요.", "회원가입이 취소되었습니다."),
    DATE("입력 형식 >> yyyy.MM.dd", "\\d{4}\\.\\d{2}\\.\\d{2}", AnsiColor.red("\t\t\t\t\t\t날짜 형식에 맞게 입력해주세요."), "날짜 입력이 취소되었습니다."),
    EQUIPMENT_COUNT("각 사이즈의 갯수를 입력해주세요. ex) 1/0/0 : ", "\\d/\\d/\\d", AnsiColor.red("장비 갯수 형식에 맞게 입력해주세요."), AnsiColor.red("장비 갯수 입력이 취소되었습니다.")),
    SEARCH_MEMBER_INFO("이름/전화번호 뒷 4자리 : ", "^[가-힣]{1,5}/\\d{4}$", "이름과 전화번호 뒷 4자리를 입력해주세요.","회원 정보 입력이 취소되었습니다."),
    HELMET_CLOTHES_ADD("추가할 사이즈/개수/가격 : ","[smlSML]{1}/\\d+/\\d+","다시 입력해주세요. \n 예)S/2/10000","개수 추가 입력이 취소되었습니다."),
    EQUIPMENT_ADD("추가할 장비의 개수/가격 : ","\\d+/\\d+","다시 입력해주세요. \n 예)2/10000","장비 개수 추가 입력이 취소되었습니다.");


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
