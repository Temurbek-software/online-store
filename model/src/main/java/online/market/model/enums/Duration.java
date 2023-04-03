package online.market.model.enums;

public enum Duration {
    MONTHLY(30),
    QUARTERLY(90),
    YEARLY(365);

    private final int days;

    Duration(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }
}
