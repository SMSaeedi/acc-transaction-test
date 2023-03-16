package at.co.account.enums;

public enum DebitStatus {
    SUCCESS,
    FAIL;

    public static String getTitle(DebitStatus val) {
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