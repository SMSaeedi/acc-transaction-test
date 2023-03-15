package at.co.account.enums;

public enum TransactionType {
    CREDIT,
    DEBIT,
    UNKNOWN;

    public static String getTitle(TransactionType val) {
        if (val == null)
            return null;

        switch (val) {
            case CREDIT:
                return "credit";
            case DEBIT:
                return "debit";
            case UNKNOWN:
                return "unknown";
        }
        return null;
    }
}