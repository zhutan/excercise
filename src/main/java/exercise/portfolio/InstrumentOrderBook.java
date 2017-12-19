package exercise.portfolio;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

/**
 * Order book of an instrument with bids and offers.
 * Bids and offers are sorted by price already, descending for bids and ascending for offers respectively.
 * Hence, the inputs of bids and offers to the class are expected to be sorted
 */

public class InstrumentOrderBook {
    private final String instrumentId;

    /**
     * @return bids which is sorted by price in descending order
     */
    private final ImmutableList<Order> bids;

    /**
     * @return offers which is sorted by price in ascending order
     */
    private final ImmutableList<Order> offers;

    /**
     * construct an order book for an instrument with bids and offers.
     *
     * @param instrumentId instrument Id, can't be blank.
     * @param bids         bids sorted by price in descending order
     * @param offers       offers sorted by price in ascending order
     */
    public InstrumentOrderBook(String instrumentId, ImmutableList<Order> bids, ImmutableList<Order> offers) {
        Preconditions.checkArgument(instrumentId != null && !instrumentId.isEmpty(),
                "instrumentId must not be blank");
        this.instrumentId = instrumentId;
        this.bids = bids;
        this.offers = offers;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public ImmutableList<Order> getBids() {
        return bids;
    }

    public ImmutableList<Order> getOffers() {
        return offers;
    }
}
