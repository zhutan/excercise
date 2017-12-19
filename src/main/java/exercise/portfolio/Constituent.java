package exercise.portfolio;

import com.google.common.base.Preconditions;

/**
 * Constituent information
 */
public class Constituent {
    private final String instrumentId;
    private final double weight;
    private final Side side;

    /**
     * a Constituent of a
     *
     * @param instrumentId
     * @param weight
     * @param side
     */
    public Constituent(String instrumentId, double weight, Side side) {
        Preconditions.checkArgument(instrumentId != null && !instrumentId.isEmpty(), "instrumentId must not be blank");
        Preconditions.checkArgument(weight > 0, "Weight should be greater than 0");
        this.instrumentId = instrumentId;
        this.weight = weight;
        this.side = side;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public double getWeight() {
        return weight;
    }

    public Side getSide() {
        return side;
    }
}
