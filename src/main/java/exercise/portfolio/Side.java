package exercise.portfolio;

/**
 * Side of the constituent
 */
public enum Side {
    BUY(1),
    SELL(-1);

    private final int sign;

    Side(int sign) {
        this.sign = sign;
    }

    public int getSign() {
        return this.sign;
    }
}
