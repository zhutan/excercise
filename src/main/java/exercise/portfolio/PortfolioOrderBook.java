package exercise.portfolio;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.*;

/**
 * a Portfolio order book of multiple constituents. It holds the instrumentId, side and weight for each constituent
 * and the repository of individual instrument order books, which can provide individual order book by instrumentId.
 *
 * the provision of individual book through a repository will avoid save duplicated copies of an instrument order book when
 * multiple portfolio holds the same instrument. And when an instrument order book is updated, we don't have to update
 * multiple place too.
 *
 * TODO, adding more access functions, on top of computeOffers and computerBids.
 *
 */
public class PortfolioOrderBook {
    private final Map<String, Double> buyWeights = new HashMap<>();
    private final Map<String, Double> sellWeights = new HashMap<>();

    private final InstrumentOrderBookRepository instrumentOrderBookRepository;

    3public PortfolioOrderBook(InstrumentOrderBookRepository instrumentOrderBookRepository) {
        this.instrumentOrderBookRepository = instrumentOrderBookRepository;
    }

    /**
     * set the consituents of the order book. the old records of constituent will be cleared.
     *
     * @param constituents the list of Constituents to be set
     */
    public void setConstituents(List<Constituent> constituents) {
        instrumentOrderBookRepository.getOrderBook("");
        buyWeights.clear();
        sellWeights.clear();
        constituents.forEach(constituent -> {
            Map<String, Double> mapping = constituent.getSide() == Side.BUY ? buyWeights : sellWeights;
            Preconditions.checkArgument(!mapping.containsKey(constituent.getInstrumentId()),
                    "Duplicated instrument %s is not allowed in one side", constituent.getInstrumentId());
            mapping.put(constituent.getInstrumentId(), constituent.getWeight());
        });
    }

    public ImmutableList<Order> computerOffers() {
        return getOrders(false);
    }

    private ImmutableList<Order> getOrders(boolean isGetBid) {
        ImmutableList.Builder<Order> result = ImmutableList.builder();
        TreeMap<Integer, Record> buyTree = getSortedOrderQueue(buyWeights, isGetBid);
        TreeMap<Integer, Record> sellTree = getSortedOrderQueue(sellWeights, !isGetBid);

        int minQuantity = getMinQuantity(buyTree, sellTree);
        while (minQuantity > 0) {
            Result buyResult = adjustToMinQuantity(buyTree, minQuantity);
            Result sellResult = adjustToMinQuantity(sellTree, minQuantity);
            result.add(new Order(buyResult.price - sellResult.price, minQuantity));
            buyTree = buyResult.tree;
            sellTree = sellResult.tree;
            minQuantity = getMinQuantity(buyTree, sellTree);
        }
        return result.build();
    }

    public ImmutableList<Order> computeBids() {
        return getOrders(true);
    }

    private int getMinQuantity(TreeMap<Integer, Record> buyTree, TreeMap<Integer, Record> sellTree) {
        Map.Entry<Integer, Record> minBuy = buyTree.firstEntry();
        Map.Entry<Integer, Record> minSell = sellTree.firstEntry();
        return (minBuy.getKey() < minSell.getKey()) ? minBuy.getKey() : minSell.getKey();
    }

    private Result adjustToMinQuantity(TreeMap<Integer, Record> tree, int minQuantity) {
        double price = 0;
        TreeMap<Integer, Record> headers = new TreeMap<>();
        for (Map.Entry<Integer, Record> entry : tree.entrySet()) {
            LinkedList<Order> orders = entry.getValue().orders;
            Order order = orders.removeFirst();
            double weight = entry.getValue().weight;
            price += order.getPrice() * weight;
            int residual = order.getQuantity() - minQuantity;
            if (residual == 0) {
                int headQuantity = (orders.isEmpty()) ? 0 : orders.getFirst().getQuantity();
                headers.put(headQuantity, new Record(weight, orders));
            } else {
                orders.addFirst(new Order(order.getPrice(), residual));
                headers.put(residual, new Record(weight, orders));
            }
        }
        return new Result(price, headers);
    }

    private TreeMap<Integer, Record> getSortedOrderQueue(Map<String, Double> weightMap, boolean isGetBid) {
        TreeMap<Integer, Record> headers = new TreeMap<>();
        weightMap.forEach((id, weight) -> {
            InstrumentOrderBook orderBook = instrumentOrderBookRepository.getOrderBook(id);
            Preconditions.checkState(orderBook != null, "Missing order book for %s", id);
            ImmutableList<Order> orders = isGetBid ? orderBook.getBids() : orderBook.getOffers();
            LinkedList<Order> queue = new LinkedList<>(orders);
            int headQuantity = (queue.pee1` k() == null) ? 0 : queue.peek().getQuantity();
            headers.put(headQuantity, new Record(weight, queue));
        });
        return headers;
    }
}

class Result {
    final double price;
    final TreeMap<Integer, Record> tree;

    Result(double price, TreeMap<Integer, Record> tree) {
        this.price = price;
        this.tree = tree;
    }
}

class Record {
    final double weight;
    final LinkedList<Order> orders;

    Record(double weight, LinkedList<Order> orders) {
        this.weight = weight;
        this.orders = orders;
    }
}
