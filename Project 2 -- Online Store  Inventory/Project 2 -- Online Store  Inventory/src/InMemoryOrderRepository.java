import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryOrderRepository implements OrderRepository {
    private final List<Order> orders = new ArrayList<>();
    @Override
    public void add(Order order) {
        boolean exist = orders.stream()
                .anyMatch(p -> p.getOrderId()== order.getOrderId());
        if (exist){
            throw new IllegalArgumentException("Order already exists");
        }
        orders.add(order);
    }

    @Override
    public Optional<Order> findById(int id) {
        return orders.stream()
                .filter(o -> o.getOrderId() == id)
                .findFirst();
    }

    @Override
    public List<Order> getAll() {
        return new ArrayList<>(this.orders);
    }

    @Override
    public List<Order> findByCustomerId(int customerId) {
        return orders.stream()
                .filter(p -> p.getCustomerId() == customerId)
                .collect(Collectors.toList());
    }
}
