package exercise.portfolio;

import java.util.HashMap;

/**
 * repository of portfolio order books.
 *
 * upon the updates of order books (individual or snapshot of a portfolio), the repository will be updated.
 * it is foreseen the portfolio order books will be published on each update or periodically (TODO).
 *
 * the repository is not thread-safe and expected to use by a single thread which listen to an update queue.
 */
public class PortfolioOrderBookRepository {
    private final InstrumentOrderBookRepository instrumentOrderBookRepository;
    private final HashMap<String, PortfolioOrderBook> portfolioOrderBooks = new HashMap<>();


    public PortfolioOrderBookRepository(InstrumentOrderBookRepository instrumentOrderBookRepository) {
        this.instrumentOrderBookRepository = instrumentOrderBookRepository;
    }

    public void onUpdate(Update update){
        // TODO
        // update instrument order books or portfolio books accordingly
        // computer and publish the refreshed/impacted portfolios' order books
        // (a mapping of instrumentId -> list of portfolios containing/affected by the instrument is expected)
    }

    /**
     * TODO order book updates/messages class,either as all Snapshots or all Deltas or some Snapshots or some deltas.
     */
    private class Update {
    }
}
