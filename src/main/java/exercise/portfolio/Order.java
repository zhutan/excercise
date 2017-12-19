package exercise.portfolio;

import com.google.common.base.Preconditions;

/**
 * a order entry in a order book, consists of price and quality
 */
public class Order {
    private final double price;
    private final int quantity;

    public Order(double price, int quantity) {
        Preconditions.checkState(quantity >= 0, "Quantity %s should not less than 0", quantity);
        this.price = price;
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("price=").append(price);
        sb.append(", quantity=").append(quantity);
        sb.append('}');
        return sb.toString();
    }
}
