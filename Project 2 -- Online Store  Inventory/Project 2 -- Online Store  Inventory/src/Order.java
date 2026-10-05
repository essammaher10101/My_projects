import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class Order {

    private final int orderId;
    private final int customerId;
    private final LocalDateTime orderDate;
    private final List<OrderItem> orderItems;

    public Order(int orderId, int customerId, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderItems = new ArrayList<>();
    }
//"what is the total price of this entire order?"
    public BigDecimal getTotal(){
        return orderItems.stream()
                .map(OrderItem ::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public void addItem(OrderItem item) {
        orderItems.add(item);
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public List<OrderItem> getOrderItems() {
        return new ArrayList<>(orderItems);
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", orderDate=" + orderDate +
                ", orderItems=" + orderItems +
                '}';
    }
}
