import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    void add(Order order);
    Optional<Order> findById(int id);
    List<Order> getAll();
    List<Order> findByCustomerId(int customerId);
}