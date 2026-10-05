import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    public void add(Product product);
    Optional<Product> findById(int id);
    List<Product> getAll();
}
