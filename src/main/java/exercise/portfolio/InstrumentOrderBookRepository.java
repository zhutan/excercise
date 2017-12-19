package exercise.portfolio;

import java.util.HashMap;
import java.util.Map;

/**
 * the repository of individuel instrument order books.
 */
public class InstrumentOrderBookRepository {
    private final Map<String, InstrumentOrderBook> books = new HashMap<>();

    /**
     * Returns the OrderBook to which the specified instrumentId is mapped,
     * or {@code null} if this map contains no mapping for the key.
     */
    public InstrumentOrderBook getOrderBook(String instrumentId) {
        return books.get(instrumentId);
    }

    /**
     * Insert/Update an instrumentOrderBook in the store, the instruemntOrderBook will be keyed on its instrumentId.
     * If the store previously contained a order book for the instrumentId, the old value is replaced by the specified value.
     *
     * @param instrumentOrderBook the order book to be inserted/updated.
     */
    public void updateOrderBook(InstrumentOrderBook instrumentOrderBook) {
        books.put(instrumentOrderBook.getInstrumentId(), instrumentOrderBook);

    }
}
