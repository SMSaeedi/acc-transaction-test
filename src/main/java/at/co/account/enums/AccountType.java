package at.co.account.enums;

public enum AccountType {
    CURRENT,
    SAVING,
    INTEREST;

    public static String getTitle(AccountType val) {
        if (val == null)
            return null;

        switch (val) {
            case CURRENT:
                return "credit";
            case SAVING:
                return "debit";
            case INTEREST:
                return "interest";
        }
        return null;
    }
}