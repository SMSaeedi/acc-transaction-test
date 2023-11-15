package at.co.account.enums;

public enum DebitStatus {
    SUCCESS,
    FAIL,
    UNKNOWN;

    public static String getTitle(CreditStatus val) {
        if (val == null)
            return null;
        switch (val) {
            case SUCCESS:
                return SUCCESS.name();
            case FAIL:
                return FAIL.name();
        }
        return UNKNOWN.name();
    }
}