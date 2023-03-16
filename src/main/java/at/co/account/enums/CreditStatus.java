package at.co.account.enums;

public enum CreditStatus {
    SUCCESS,
    FAIL;

    public static String getTitle(CreditStatus val) {
        if (val == null)
            return null;
        switch (val) {
            case SUCCESS:
                return "موفق";
            case FAIL:
                return "ناموفق";
        }
        return null;
    }
}